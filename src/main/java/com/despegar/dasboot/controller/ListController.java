package com.despegar.dasboot.controller;

import com.despegar.dasboot.model.list.ListInfo;
import com.despegar.dasboot.repository.dto.MovieList;
import com.despegar.dasboot.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class ListController {
  private ListService service;

  @Autowired
  public ListController(ListService service) {
    this.service = service;
  }

  @GetMapping(value = "/lists/{id}")
  public MovieList getList(@PathVariable(value = "id") String id) {
    return service.getList(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
  }

  @PostMapping(value = "/lists/{listId}/movies")
  public MovieList addToList(
    @RequestBody List<String> movies, @PathVariable(value = "listId") String listId) {
    return service
        .addToList(listId, movies)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
  }

  @DeleteMapping(value = "/lists/{listId}/movies")
  public MovieList deleteFromList(
    @RequestBody List<String> movies, @PathVariable(value = "listId") String listId) {
    return service
        .deleteFromList(listId, movies)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
  }

  @DeleteMapping(value = "/lists/{id}")
  public MovieList delete(@PathVariable(value = "id") String listId) {
    return service.delete(listId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
  }

  @PostMapping(value = "/lists")
  public MovieList create(@RequestBody ListInfo listInfo) {
    return service.create(listInfo);
  }
}
