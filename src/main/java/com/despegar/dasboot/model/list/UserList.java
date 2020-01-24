package com.despegar.dasboot.model.list;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalDate;
import java.util.List;

public class UserList {
  private String id;
  private String user;
  private String name;
  private List<UserListItem> items;
  private LocalDate created;

  @JsonCreator
  public UserList(
      String id, String user, String name, List<UserListItem> items, LocalDate created) {
    this.id = id;
    this.user = user;
    this.name = name;
    this.items = items;
    this.created = created;
  }

  public String getId() {
    return id;
  }

  public String getUser() {
    return user;
  }

  public String getName() {
    return name;
  }

  public List<UserListItem> getItems() {
    return items;
  }

  public LocalDate getCreated() {
    return created;
  }
}
