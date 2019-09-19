package com.despegar.springtutorial.model.search;

import java.util.List;

public class Movie {
  private String id;
  private String name;
  private String description;
  private List<String> genres;
  private List<MovieActor> actors;

  public Movie(
      String id, String name, String description, List<String> genres, List<MovieActor> actors) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.genres = genres;
    this.actors = actors;
  }

  public List<MovieActor> getActors() {
    return actors;
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
