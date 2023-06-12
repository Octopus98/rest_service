package com.example.elastic;

public class ChildObject {
  private String name;
  private String id;

  public void set_parent(String _parent) {
    this._parent = _parent;
  }

  private String _parent;

  public ChildObject(String name, String id) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
