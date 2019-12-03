package com.despegar.dasboot.controller;

import com.despegar.dasboot.model.movie.Movie;
import com.despegar.dasboot.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class MovieController {
  private MovieService service;

  @Autowired
  public MovieController(MovieService service) {
    this.service = service;
  }

  @RequestMapping(value = "/movies/{id}", method = RequestMethod.GET)
  public Movie getMovie(@PathVariable final String id) {
    return service
        .get(id)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Movie not found"));
  }
}
