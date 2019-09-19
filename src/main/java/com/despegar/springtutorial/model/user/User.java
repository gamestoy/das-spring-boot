package com.despegar.springtutorial.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class User {
  private String id;
  private List<String> lists;

  @JsonCreator
  public User(String id, List<String> lists) {
    this.id = id;
    this.lists = lists;
  }

  public String getId() {
    return id;
  }

  public List<String> getLists() {
    return lists;
  }
}
