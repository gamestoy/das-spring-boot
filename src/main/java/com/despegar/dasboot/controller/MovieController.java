package com.despegar.dasboot.controller;

import com.despegar.dasboot.model.movie.Movie;
import com.despegar.dasboot.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovieController {
  private MovieService service;

  @Autowired
  public MovieController(MovieService service) {
    this.service = service;
  }

  @GetMapping(value = "/movies/{id}")
  public Movie getMovie(@PathVariable final String id) {
    return service.get(id);
  }
}
