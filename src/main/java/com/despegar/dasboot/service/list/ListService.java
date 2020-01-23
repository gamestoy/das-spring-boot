package com.despegar.dasboot.service.list;

import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import com.despegar.dasboot.connector.tmdb.dto.MovieData;
import com.despegar.dasboot.model.list.ListInfo;
import com.despegar.dasboot.model.list.UserList;
import com.despegar.dasboot.model.list.UserListItem;
import com.despegar.dasboot.model.movie.MovieInfo;
import com.despegar.dasboot.repository.Lists;
import com.despegar.dasboot.repository.dto.MovieItem;
import com.despegar.dasboot.repository.dto.MovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListService {
  private Lists lists;
  private TMDBConnector connector;

  @Autowired
  public ListService(Lists lists, TMDBConnector connector) {
    this.lists = lists;
    this.connector = connector;
  }

  public Optional<UserList> getList(String id) {
    return lists.getList(id).map(this::convert);
  }

  public Optional<UserList> addToList(String listId, List<String> movieIds) {
    return lists.addToList(listId, movieIds).map(this::convert);
  }

  public Optional<UserList> deleteFromList(String listId, List<String> movieIds) {
    return lists.removeFromList(listId, movieIds).map(this::convert);
  }

  public Optional<UserList> delete(String listId) {
    return lists.removeList(listId).map(this::convert);
  }

  public UserList create(ListInfo listInfo) {
    var list = lists.createList(listInfo);
    return convert(list);
  }

  private UserList convert(MovieList list) {
    var items =
        list.getItems().stream()
            .map(
                i -> {
                  var movie = connector.getMovie(i.getId());
                  return convert(i, movie);
                })
            .collect(Collectors.toList());
    return new UserList(list.getId(), list.getUser(), list.getName(), items, list.getCreated());
  }

  private UserListItem convert(MovieItem item, MovieData movie) {
    var info = new MovieInfo(movie.getId(), movie.getTitle(), movie.getReleaseDate());
    return new UserListItem(info, item.getAdded());
  }
}
