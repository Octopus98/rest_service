package com.example.model;

import com.example.util.Strings;

import java.util.Objects;

public class Header {
  String algorithm; // alg
  String type; // typ
  String keyID; // optional

  Header(String algorithm, String type) {
    this(algorithm, type, "");
  }

  Header(String algorithm, String type, String keyID) {
    this.algorithm = algorithm;
    this.type = type;
    this.keyID = keyID;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");

    sb.append("\t")
        .append(Strings.quote("arg"))
        .append(": ")
        .append(Strings.quote(algorithm))
        .append("\n");

    if (!keyID.isEmpty()) {
      sb.append("\t")
          .append(Strings.quote("kid"))
          .append(": ")
          .append(Strings.quote(keyID))
          .append("\n");
    }

    sb.append("\t")
        .append(Strings.quote("typ"))
        .append(": ")
        .append(Strings.quote(type))
        .append("\n");

    sb.append("}");

    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Header header = (Header) o;
    return Objects.equals(algorithm, header.algorithm)
        && Objects.equals(type, header.type)
        && Objects.equals(keyID, header.keyID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(algorithm, type, keyID);
  }

  public static void main(String[] args) {
    System.out.println(new Header("RS256", "JWT", "5df1f945ff906aeaefa93c27698db406d606b0e8"));
  }
}
