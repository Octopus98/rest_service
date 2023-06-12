package com.example.model;

import com.example.util.MD5;

import java.util.Objects;

public class ETag {
  private final String value;
  private final String md5Value;

  public ETag(String value) {
    this.value = value;
    md5Value = MD5.encode(this.value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ETag eTag = (ETag) o;
    return Objects.equals(value, eTag.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "etag:" + md5Value;
  }
}
