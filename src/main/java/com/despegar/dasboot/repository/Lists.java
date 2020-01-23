package com.despegar.dasboot.repository;

import com.despegar.dasboot.model.list.ListInfo;
import com.despegar.dasboot.repository.dto.MovieItem;
import com.despegar.dasboot.repository.dto.MovieList;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
// This is not thread safe
public class Lists {
  private Map<String, MovieList> lists;

  public Lists() {
    this.lists = new ConcurrentHashMap<>();
  }

  public List<String> getListsIds(String user) {
    return lists.values().stream()
        .filter(v -> v.getUser().equalsIgnoreCase(user))
        .map(MovieList::getId)
        .collect(Collectors.toList());
  }

  public Optional<MovieList> getList(String id) {
    return Optional.ofNullable(lists.get(id));
  }

  public Optional<MovieList> addToList(String listId, List<String> movies) {
    return Optional.ofNullable(lists.get(listId))
        .map(
            l -> {
              movies.forEach(m -> l.addItem(new MovieItem(m, LocalDate.now())));
              return l;
            });
  }

  public MovieList createList(ListInfo info) {
    var id = UUID.randomUUID().toString();
    var movieList =
        new MovieList(id, info.getUser(), info.getName(), new HashSet<>(), LocalDate.now());
    lists.put(id, movieList);
    return movieList;
  }

  public Optional<MovieList> removeFromList(String listId, List<String> movies) {
    return Optional.ofNullable(lists.get(listId))
        .map(
            l -> {
              movies.forEach(l::removeItem);
              return l;
            });
  }

  public Optional<MovieList> removeList(String listId) {
    return Optional.ofNullable(lists.remove(listId));
  }
}
