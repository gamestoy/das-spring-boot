package com.despegar.dasboot.repository.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Set;

public class MovieList {
  private String id;
  private String user;
  private String name;
  @JsonProperty private Set<MovieItem> items;
  private LocalDate created;

  @JsonCreator
  public MovieList(String id, String user, String name, Set<MovieItem> items, LocalDate created) {
    this.id = id;
    this.user = user;
    this.name = name;
    this.items = items;
    this.created = created;
  }

  public String getName() {
    return name;
  }

  public void removeItem(String movieId) {
    items.stream()
        .filter(i -> i.getId().equalsIgnoreCase(movieId))
        .findFirst()
        .ifPresent(m -> items.remove(m));
  }

  public void addItem(MovieItem item) {
    items.add(item);
  }

  public LocalDate getCreated() {
    return created;
  }

  public String getId() {
    return id;
  }

  public String getUser() {
    return user;
  }

  public Set<MovieItem> getItems() {
    return items;
  }
}
