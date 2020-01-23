package com.despegar.dasboot.model.movie;

import java.util.List;

public class Movie {
  private String id;
  private String name;
  private String description;
  private List<String> genres;

  public Movie(String id, String name, String description, List<String> genres) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.genres = genres;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public List<String> getGenres() {
    return genres;
  }

  public String getId() {
    return id;
  }
}
