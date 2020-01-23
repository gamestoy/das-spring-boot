package com.despegar.dasboot.controller;

import com.despegar.dasboot.model.search.Movie;
import com.despegar.dasboot.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class SearchController {
  private SearchService service;

  @Autowired
  public SearchController(SearchService service) {
    this.service = service;
  }

  @GetMapping(value = "/search")
  public List<Movie> search(@RequestParam(value = "q") String query, @RequestParam Optional<Integer> page) {
    return this.service.search(query, page.orElse(1));
  }
}
