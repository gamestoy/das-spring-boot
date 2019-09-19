package com.despegar.springtutorial.connector.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Similar {
  private List<SearchMovie> results;

  @JsonCreator
  public Similar(@JsonProperty("results") List<SearchMovie> results) {
    this.results = results;
  }

  public List<SearchMovie> getResults() {
    return results;
  }
}
