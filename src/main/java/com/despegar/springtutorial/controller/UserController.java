package com.despegar.springtutorial.controller;

import com.despegar.springtutorial.model.user.User;
import com.despegar.springtutorial.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  private UserService service;

  @Autowired
  public UserController(UserService service) {
    this.service = service;
  }

  @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
  public User get(@PathVariable(value = "id") String id) {
    return service.get(id);
  }
}
