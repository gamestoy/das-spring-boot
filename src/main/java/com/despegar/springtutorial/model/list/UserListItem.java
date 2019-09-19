package com.despegar.springtutorial.model.list;

import com.despegar.springtutorial.model.movie.MovieInfo;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalDate;

public class UserListItem {
  private LocalDate added;
  private MovieInfo movie;

  @JsonCreator
  public UserListItem(MovieInfo movie, LocalDate added) {
    this.movie = movie;
    this.added = added;
  }

  public LocalDate getAdded() {
    return added;
  }

  public MovieInfo getMovie() {
    return movie;
  }
}
