package com.despegar.springtutorial.connector.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Cast {
  private Long id;
  private String character;
  private String name;
  private String profilePath;

  @JsonCreator
  public Cast(
      @JsonProperty("id") Long id,
      @JsonProperty("character") String character,
      @JsonProperty("name") String name,
      @JsonProperty("profile_path") String profilePath) {
    this.id = id;
    this.character = character;
    this.name = name;
    this.profilePath = profilePath;
  }

  public String getCharacter() {
    return character;
  }

  public String getName() {
    return name;
  }

  public Long getId() {
    return id;
  }

  public String getProfilePath() {
    return profilePath;
  }
}
