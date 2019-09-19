package com.despegar.springtutorial.repository;

public interface MovieOperations {
  void addMovieToList(String listId, String movieId);
  void removeMovieToList(String listId, String movieId);
}
