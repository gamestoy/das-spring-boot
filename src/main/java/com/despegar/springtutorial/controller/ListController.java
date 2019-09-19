package com.despegar.springtutorial.controller;

import com.despegar.springtutorial.model.list.ListInfo;
import com.despegar.springtutorial.model.list.UserList;
import com.despegar.springtutorial.service.list.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class ListController {
  private ListService service;

  @Autowired
  public ListController(ListService service) {
    this.service = service;
  }

  @RequestMapping(value = "/lists/{id}", method = RequestMethod.GET)
  public UserList getList(@PathVariable(value = "id") String id) {
    return service.getList(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
  }

  @RequestMapping(value = "/lists/{listId}/movie/{id}", method = RequestMethod.PUT)
  public void addToList(
      @PathVariable(value = "listId") String listId, @PathVariable(value = "id") String movieId) {
    service.addToList(listId, movieId);
  }

  @RequestMapping(value = "/lists/{listId}/movie/{id}", method = RequestMethod.DELETE)
  public void deleteFromList(
      @PathVariable(value = "listId") String listId, @PathVariable(value = "id") String movieId) {
    service.deleteFromList(listId, movieId);
  }

  @RequestMapping(value = "/lists/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable(value = "id") String listId) {
    service.delete(listId);
  }

  @RequestMapping(value = "/lists", method = RequestMethod.POST)
  public UserList create(@RequestBody ListInfo listInfo) {
    return service.create(listInfo).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
  }
}
