package com.example.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Jwt {
  private static final String PRI_KEY = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
  private static final Key SECRET_KEY_SPEC =
      new SecretKeySpec(Base64.getDecoder().decode(PRI_KEY), SignatureAlgorithm.HS256.getJcaName());

  public enum ParseStatus {
    Ok("OK"),
    TimeOut("Expired token"),
    Invalid("Invalid token");

    private final String message;

    ParseStatus(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }
  }

  public static String createToken() {

    Instant now = Instant.now();

    return Jwts.builder()
        .claim("name", "Jane Doe")
        .claim("email", "jane@example.com")
        .setSubject("jane")
        .setId(java.util.UUID.randomUUID().toString())
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(now.plus(300L, ChronoUnit.SECONDS)))
        .signWith(SECRET_KEY_SPEC)
        .compact();
  }

  public static ParseStatus validateJWT(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(SECRET_KEY_SPEC).build().parseClaimsJws(token);

      return ParseStatus.Ok;
    } catch (ExpiredJwtException ignored) {
      return ParseStatus.TimeOut;
    } catch (Exception ignored) {
      return ParseStatus.Invalid;
    }
  }

  public static void main(String[] args) throws InterruptedException {
    String jwtToken = createToken();
    TimeUnit.SECONDS.sleep(3);
  }
}
