package com.despegar.springtutorial.model.list;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ListInfo {
  private String user;
  private String name;

  @JsonCreator
  public ListInfo(String user, String name) {
    this.user = user;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getUser() {
    return user;
  }
}
