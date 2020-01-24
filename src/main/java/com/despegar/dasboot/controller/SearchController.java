package com.despegar.dasboot.controller;

import com.despegar.dasboot.model.movie.MovieInfo;
import com.despegar.dasboot.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

  @RequestMapping(value = "/search", method = RequestMethod.GET)
  public List<MovieInfo> search(
      @RequestParam(value = "q") String query,
      @RequestParam(value = "page", required = false) Optional<Integer> page) {
    return this.service.search(query, page.orElse(1));
  }
}
