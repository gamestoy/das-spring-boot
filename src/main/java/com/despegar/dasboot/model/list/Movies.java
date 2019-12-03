package com.despegar.dasboot.model.list;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class Movies {
  private List<String> ids;

  @JsonCreator
  public Movies(List<String> ids) {
    this.ids = ids;
  }

  public List<String> getIds() {
    return ids;
  }
}
