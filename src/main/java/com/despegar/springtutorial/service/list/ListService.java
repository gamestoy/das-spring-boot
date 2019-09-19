package com.despegar.springtutorial.service.list;

import com.despegar.springtutorial.connector.tmdb.TMDBConnector;
import com.despegar.springtutorial.connector.tmdb.dto.MovieData;
import com.despegar.springtutorial.model.list.ListInfo;
import com.despegar.springtutorial.model.list.UserList;
import com.despegar.springtutorial.model.list.UserListItem;
import com.despegar.springtutorial.model.movie.MovieInfo;
import com.despegar.springtutorial.repository.ListRepository;
import com.despegar.springtutorial.repository.entities.MovieItem;
import com.despegar.springtutorial.repository.entities.MovieList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ListService {
  private static final Logger logger = LoggerFactory.getLogger(ListService.class);
  private ListRepository repository;
  private TMDBConnector connector;
  private AsyncTaskExecutor taskExecutor;

  @Autowired
  public ListService(
      ListRepository repository,
      TMDBConnector connector,
      AsyncTaskExecutor commonThreadPoolTaskExecutor) {
    this.repository = repository;
    this.connector = connector;
    this.taskExecutor = commonThreadPoolTaskExecutor;
  }

  public Optional<UserList> getList(String id) {
    logger.debug("Retrieving list " + id);
    return repository.findById(id).flatMap(this::convert);
  }

  public List<String> getUserLists(String user) {
    logger.debug("Retrieving lists from user " + user);
    return repository.findAllByUser(user).stream()
        .map(MovieList::getId)
        .collect(Collectors.toList());
  }

  public void addToList(String listId, String movieId) {
    logger.debug("Adding" + movieId + "to list " + listId);
    repository.addMovieToList(listId, movieId);
  }

  public void deleteFromList(String listId, String movieId) {
    logger.debug("Deleting" + movieId + "from list " + listId);
    repository.removeMovieToList(listId, movieId);
  }

  public void delete(String listId) {
    logger.debug("Deleting list");
    repository.deleteById(listId);
  }

  public Optional<UserList> create(ListInfo listInfo) {
    logger.debug("Creating list");
    var list =
        repository.save(
            new MovieList(
                listInfo.getUser(), listInfo.getName(), new HashSet<>(), LocalDate.now()));
    return convert(list);
  }

  private Optional<UserList> convert(MovieList list) {
    var itemsF =
        list.getItems().stream()
            .map(
                i ->
                    CompletableFuture.supplyAsync(() -> connector.getMovie(i.getId()), taskExecutor)
                        .thenApply(m -> this.convert(m, i)))
            .collect(Collectors.toList());
    var movieItems =
        itemsF.stream()
            .map(CompletableFuture::join)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    return Optional.of(
        new UserList(list.getId(), list.getUser(), list.getName(), movieItems, list.getCreated()));
  }

  private UserListItem convert(MovieData movie, MovieItem item) {
    var info = new MovieInfo(movie.getId(), movie.getTitle(), movie.getReleaseDate());
    return new UserListItem(info, item.getAdded());
  }
}
