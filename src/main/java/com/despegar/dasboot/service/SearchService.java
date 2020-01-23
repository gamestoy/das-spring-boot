package com.despegar.dasboot.service;

import com.despegar.dasboot.model.search.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SearchService {

  public List<Movie> search(String query, Integer page) {
    return Collections.singletonList(
        new Movie(
            "1",
            "The Godfather",
            "The godfather",
            Collections.singletonList("Drama"),
            new ArrayList<>()));
  }
}
