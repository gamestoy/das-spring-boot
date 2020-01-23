package com.despegar.dasboot.connector.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Credits {
  private Long id;
  private List<Cast> cast;
  private List<Crew> crew;

  public Credits(
      @JsonProperty("id") Long id,
      @JsonProperty("cast") List<Cast> cast,
      @JsonProperty("crew") List<Crew> crew) {
    this.id = id;
    this.cast = cast;
    this.crew = crew;
  }

  public Long getId() {
    return id;
  }

  public List<Cast> getCast() {
    return cast;
  }

  public List<Crew> getCrew() {
    return crew;
  }
}
