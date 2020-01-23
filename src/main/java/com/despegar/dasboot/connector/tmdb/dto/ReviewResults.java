package com.despegar.dasboot.connector.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReviewResults {
  private List<Review> results;

  @JsonCreator
  public ReviewResults(@JsonProperty("results") List<Review> results) {
    this.results = results;
  }

  public List<Review> getResults() {
    return results;
  }
}
