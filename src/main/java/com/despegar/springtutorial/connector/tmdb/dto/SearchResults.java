package com.despegar.springtutorial.connector.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchResults {
  private List<SearchMovie> results;

  @JsonCreator
  public SearchResults(@JsonProperty("results") List<SearchMovie> results) {
    this.results = results;
  }

  public List<SearchMovie> getResults() {
    return results;
  }
}
