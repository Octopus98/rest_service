package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class Top {
  private String _org;
  private String objectId;
  private String objectType;
  private String planType;
  private String creationDate;

  public Top() {}

  public Top(
      String _org, String objectId, String objectType, String planType, String creationDate) {
    this._org = _org;
    this.objectId = objectId;
    this.objectType = objectType;
    this.planType = planType;
    this.creationDate = creationDate;
    joinField = new HashMap<>();
  }

  public Map<String, String> getJoinField() {
    return joinField;
  }

  public void setJoinField(Map<String, String> joinField) {
    this.joinField = joinField;
  }

  @JsonProperty(value = "join_field")
  private Map<String, String> joinField;
}
