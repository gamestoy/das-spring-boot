package com.despegar.springtutorial.service.user;

import com.despegar.springtutorial.model.user.User;
import com.despegar.springtutorial.service.list.ListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);
  private ListService listService;

  @Autowired
  public UserService(ListService listService) {
    this.listService = listService;
  }

  public User get(String user) {
    logger.debug("Retrieving user");
    var movieLists = listService.getUserLists(user);

    return new User(user, movieLists);
  }
}
