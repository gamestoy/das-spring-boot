package com.despegar.springtutorial.controller;

import com.despegar.springtutorial.model.movie.Movie;
import com.despegar.springtutorial.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {
  private MovieService service;

  @Autowired
  public MovieController(MovieService service) {
    this.service = service;
  }

  @RequestMapping(value = "/movie/{id}", method = RequestMethod.GET)
  public Movie getMovie(@PathVariable final String id) {
    return service.get(id);
  }
}
