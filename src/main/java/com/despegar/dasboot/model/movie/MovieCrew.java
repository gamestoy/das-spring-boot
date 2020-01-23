package com.despegar.dasboot.model.movie;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MovieCrew implements Serializable {
  private Long id;
  private String job;
  private String name;
  private String profilePath;

  @JsonCreator
  public MovieCrew(
      @JsonProperty("id") Long id,
      @JsonProperty("job") String job,
      @JsonProperty("name") String name,
      @JsonProperty("profile_path") String profilePath) {
    this.id = id;
    this.job = job;
    this.name = name;
    this.profilePath = profilePath;
  }

  public String getJob() {
    return job;
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
