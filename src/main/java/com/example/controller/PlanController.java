package com.example.controller;

import com.example.model.Plan;
import com.example.queue.RabbitMQReceiver;
import com.example.queue.RabbitMQSender;
import com.example.service.RedisJsonStub;
import com.example.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

@RestController
public class PlanController {
  private final RedisJsonStub db = RedisJsonStub.getInstance();
  private final Set<String> jwtTokenSet = new TreeSet<>();

  private final RabbitMQReceiver rabbitMQReceiver;
  private final RabbitMQSender rabbitMQSender;

  public PlanController() {
    this.rabbitMQReceiver = new RabbitMQReceiver();
    rabbitMQSender = new RabbitMQSender();
  }

  private Jwt.ParseStatus interceptor(String jwtToken) {
    if (!jwtTokenSet.contains(jwtToken)) {
      return Jwt.ParseStatus.Invalid;
    }

    Jwt.ParseStatus status = Jwt.validateJWT(jwtToken);
    if (status == Jwt.ParseStatus.TimeOut) {
      jwtTokenSet.remove(jwtToken);
    }

    return status;
  }

  private HttpEntity<String> authorize(String authorization) {
    if (authorization.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty authorization header.");
    }

    String[] tokens = authorization.split(" ");
    if (tokens.length != 2 && !"Bearer".equalsIgnoreCase(tokens[0])) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Jwt.ParseStatus.Invalid.getMessage());
    }

    Jwt.ParseStatus status = interceptor(tokens[1]);
    if (status != Jwt.ParseStatus.Ok) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(status.getMessage());
    }

    return null;
  }

  @GetMapping(path = "/token")
  public HttpEntity<String> getToken() {
    String jwtToken = Jwt.createToken();
    jwtTokenSet.add(jwtToken);

    return ResponseEntity.status(HttpStatus.OK)
        .body("{ " + Strings.quote("token") + ": " + Strings.quote(jwtToken) + " }");
  }

  @GetMapping("/ListHeaders")
  public ResponseEntity<String> listAllHeaders(@RequestHeader Map<String, String> headers) {
    headers.forEach(
        (key, value) -> {
          System.out.printf("Header '%s' = %s%n", key, value);
        });

    return new ResponseEntity<>(String.format("Listed %d headers", headers.size()), HttpStatus.OK);
  }

  @PostMapping(path = "/plan")
  public HttpEntity<String> addPlan(
      @RequestHeader Map<String, String> headers, @RequestBody String jsonString) {
    // Derive authorization header from request.
    String authorization =
        headers.getOrDefault(HttpHeaders.AUTHORIZATION.toLowerCase(Locale.ROOT), "");

    // Security check.
    HttpEntity<String> result = authorize(authorization);
    if (result != null) {
      return result;
    }

    // Verify json string format.
    ProcessingReport report = JsonSchemaValidator.validate(jsonString);
    if (!report.isSuccess()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(report.toString());
    }

    ObjectMapper mapper = new ObjectMapper();
    try {
      Plan plan = mapper.readValue(jsonString, Plan.class);
      String objectId = plan.getObjectId();
      String etag = MD5.encode(jsonString);
      db.store(objectId, jsonString, etag);

      System.out.println("ETAg before = " + etag);

      // Send request to rabbitmq.
      HttpRequestMessage requestMessage =
          new HttpRequestMessage("POST", "/plan/" + objectId, headers, jsonString);
      rabbitMQSender.send(requestMessage);

      System.out.println("After = " + etag);
      return ResponseEntity.status(HttpStatus.CREATED)
          .header("ETag", etag)
          .body("{ " + Strings.quote("objectId") + ": " + Strings.quote(plan.getObjectId()) + " }");
    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
    }

    return null;
  }

  @GetMapping(path = "/plan/{id}")
  public HttpEntity<String> getPlan(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String authorization,
      @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, defaultValue = "") String ifNoneMatch,
      @PathVariable String id) {
    HttpEntity<String> result = authorize(authorization);
    if (result != null) {
      return result;
    }

    List<String> stringList = db.query(id);

    if (stringList == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id + " NOT exist.");
    }

    assert stringList.size() == 2;

    String jsonString = stringList.get(0);
    String md5Value = stringList.get(1);

    if (md5Value.equals(ifNoneMatch)) {
      return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(jsonString);
    }

    return ResponseEntity.status(HttpStatus.OK).body(jsonString);
  }

  @PatchMapping(path = "/plan/{id}")
  public HttpEntity<String> patchPlan(
      @RequestHeader Map<String, String> headers,
      @PathVariable String id,
      @RequestBody String jsonString) {
    // Derive authorization header from request.
    String authorization =
        headers.getOrDefault(HttpHeaders.AUTHORIZATION.toLowerCase(Locale.ROOT), "");

    HttpEntity<String> result = authorize(authorization);
    if (result != null) {
      return result;
    }

    ProcessingReport report = JsonSchemaValidator.validate(jsonString);
    if (!report.isSuccess()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(report.toString());
    }

    List<String> stringList = db.query(id);
    if (stringList == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id + " NOT exist.");
    }

    assert stringList.size() == 2;

    String md5Value = stringList.get(1);

    String ifNoneMatch =
        headers.getOrDefault(HttpHeaders.IF_MATCH.toLowerCase(Locale.ROOT), "");
    System.out.println("ifNoneMatch = " + ifNoneMatch);
    System.out.println("md5 = " + md5Value);
    if (!md5Value.equals(ifNoneMatch)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ETag NOT match.");
    }

    // Check patch content
    if (md5Value.equals(MD5.encode(jsonString))) {
      return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Content doesn't change.");
    }

    // Send request to rabbitmq.
    HttpRequestMessage requestMessage =
        new HttpRequestMessage("PATCH", "None", headers, jsonString);
    try {
      rabbitMQSender.send(requestMessage);
    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
    }

    db.remove(id);

    md5Value = MD5.encode(jsonString);
    db.store(id, jsonString, md5Value);

    return ResponseEntity.status(HttpStatus.OK)
        .header("ETag", md5Value)
        .body("Patch Successfully.");
  }

  @DeleteMapping(path = "/plan/{id}")
  public HttpEntity<String> deletePlan(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String authorization,
      @RequestHeader(value = HttpHeaders.IF_MATCH, defaultValue = "") String ifNoneMatch,
      @PathVariable String id) {
    HttpEntity<String> result = authorize(authorization);
    if (result != null) {
      return result;
    }

    List<String> stringList = db.query(id);

    if (stringList == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id + " NOT exist.");
    }

    assert stringList.size() == 2;

    String md5Value = stringList.get(1);

    if (!md5Value.equals(ifNoneMatch)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ETag NOT match.");
    }

    db.remove(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Succeed to remove.");
  }
}
