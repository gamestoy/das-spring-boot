package com.despegar.dasboot.connector.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SimilarMovies {
  private List<SearchMovie> results;

  @JsonCreator
  public SimilarMovies(@JsonProperty("results") List<SearchMovie> results) {
    this.results = results;
  }

  public List<SearchMovie> getResults() {
    return results;
  }
}
