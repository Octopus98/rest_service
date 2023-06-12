package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class LinkedPlanServiceHelper {
  private String _org;
  private String objectId;
  private String objectType;

  public LinkedPlanServiceHelper() {
    joinField = new HashMap<>();
  }

  public LinkedPlanServiceHelper(String _org, String objectId, String objectType) {
    this._org = _org;
    this.objectId = objectId;
    this.objectType = objectType;
    joinField = new HashMap<>();
  }

  public String get_org() {
    return _org;
  }

  public void set_org(String _org) {
    this._org = _org;
  }

  public String getObjectId() {
    return objectId;
  }

  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  public String getObjectType() {
    return objectType;
  }

  public void setObjectType(String objectType) {
    this.objectType = objectType;
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
