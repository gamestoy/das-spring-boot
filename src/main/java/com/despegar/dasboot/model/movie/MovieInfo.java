package com.despegar.dasboot.model.movie;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieInfo {
  private Long id;
  private String title;

  @JsonProperty("release_year")
  private String releaseYear;

  @JsonCreator
  public MovieInfo(
      @JsonProperty("id") Long id,
      @JsonProperty("title") String title,
      @JsonProperty("release_year") String releaseYear) {
    this.id = id;
    this.title = title;
    this.releaseYear = releaseYear;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getReleaseYear() {
    return releaseYear;
  }
}
