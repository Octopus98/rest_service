package com.example.util;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
  public static String encode(String data) {
    try {
      MessageDigest md = MessageDigest.getInstance("md5");
      byte[] bytes = md.digest(data.getBytes(StandardCharsets.UTF_8));

      return Hex.encodeHexString(bytes);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return null;
  }
}
