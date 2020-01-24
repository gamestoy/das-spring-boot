package com.despegar.dasboot.connector.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchMovie {
  private Long id;
  private String title;
  private String releaseDate;

  @JsonCreator
  public SearchMovie(
      @JsonProperty("id") Long id,
      @JsonProperty("title") String title,
      @JsonProperty("release_date") String releaseDate) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getReleaseDate() {
    return releaseDate;
  }
}
