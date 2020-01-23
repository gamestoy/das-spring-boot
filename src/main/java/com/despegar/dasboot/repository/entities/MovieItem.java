package com.despegar.dasboot.repository.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalDate;
import java.util.Objects;

public class MovieItem {
  private String id;
  private LocalDate added;

  @JsonCreator
  public MovieItem(String id, LocalDate added) {
    this.id = id;
    this.added = added;
  }

  public String getId() {
    return id;
  }

  public LocalDate getAdded() {
    return added;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MovieItem movieItem = (MovieItem) o;
    return Objects.equals(id, movieItem.id) && Objects.equals(added, movieItem.added);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, added);
  }
}

