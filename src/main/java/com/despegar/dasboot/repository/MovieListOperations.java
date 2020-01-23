package com.despegar.dasboot.repository;

import com.despegar.dasboot.repository.entities.MovieItem;

import java.util.List;

public interface MovieListOperations {
  long addMovieToList(String listId, List<MovieItem> movieIds);
  long removeMovieFromList(String listId, List<String> movieIds);
}
