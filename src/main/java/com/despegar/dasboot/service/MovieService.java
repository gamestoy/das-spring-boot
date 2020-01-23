package com.despegar.dasboot.service;

import com.despegar.dasboot.model.movie.Movie;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MovieService {

  public Movie get(String id) {
    return new Movie(id, "The Godfather", "", Collections.singletonList("Drama"));
  }
}
