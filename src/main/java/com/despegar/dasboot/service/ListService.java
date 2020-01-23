package com.despegar.dasboot.service;

import com.despegar.dasboot.model.list.ListInfo;
import com.despegar.dasboot.repository.Lists;
import com.despegar.dasboot.repository.dto.MovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListService {
  private Lists lists;

  @Autowired
  public ListService(Lists lists) {
    this.lists = lists;
  }

  public Optional<MovieList> getList(String id) {
    return lists.getList(id);
  }

  public Optional<MovieList> addToList(String listId, List<String> movies) {
    return lists.addToList(listId, movies);
  }

  public Optional<MovieList> deleteFromList(String listId, List<String> movies) {
    return lists.removeFromList(listId, movies);
  }

  public Optional<MovieList> delete(String listId) {
    return lists.removeList(listId);
  }

  public MovieList create(ListInfo listInfo) {
    return lists.createList(listInfo);
  }
}
