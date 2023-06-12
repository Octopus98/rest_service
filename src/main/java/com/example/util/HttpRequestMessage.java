package com.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestMessage {
  private String method;
  private String url;
  private Map<String, String> headers;
  private Object body;

  public HttpRequestMessage() {}

  public HttpRequestMessage(String method, String url, Map<String, String> headers, Object body) {
    this.method = method;
    this.url = url;
    this.headers = headers;
    this.body = body;
  }

  public String toJson() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(this);
  }

  public static HttpRequestMessage fromJson(String json) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(json, HttpRequestMessage.class);
  }

  public String getMethod() {
    return method;
  }

  public String getUrl() {
    return url;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public Object getBody() {
    return body;
  }

  public static void main(String[] args) throws JsonProcessingException {
    Map<String, String> map = new HashMap<>();
    map.put("host", "remote");
    map.put("version", "1.0");

    HttpRequestMessage message = new HttpRequestMessage("GET", "/plan", map, "hello");
    String json = message.toJson();

    HttpRequestMessage m2 = HttpRequestMessage.fromJson(json);
    System.out.println(m2.getBody());
    System.out.println(m2.getUrl());
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public void setBody(Object body) {
    this.body = body;
  }
}
