package com.despegar.dasboot.service.list;

import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import com.despegar.dasboot.model.list.ListInfo;
import com.despegar.dasboot.model.list.UserList;
import com.despegar.dasboot.repository.ListRepository;
import com.despegar.dasboot.repository.entities.MovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ListService {
  private ListRepository listRepository;
  private TMDBConnector connector;
  private AsyncTaskExecutor taskExecutor;
  private ListTransformer transformer;

  @Autowired
  public ListService(
    ListRepository listRepository,
    TMDBConnector connector,
    AsyncTaskExecutor commonThreadPoolTaskExecutor,
    ListTransformer transformer
  ) {
    this.listRepository = listRepository;
    this.connector = connector;
    this.taskExecutor = commonThreadPoolTaskExecutor;
    this.transformer = transformer;
  }

  public Optional<UserList> getList(String id) {
    return listRepository.findById(id).map(this::getUserList);
  }

  public Optional<UserList> addToList(String listId, List<String> movieIds) {
    var movieItems = movieIds.stream().map(transformer::convert).collect(Collectors.toList());
    listRepository.addMovieToList(listId, movieItems);
    return getList(listId);
  }

  public Optional<UserList> deleteFromList(String listId, List<String> movieIds) {
    listRepository.removeMovieFromList(listId, movieIds);
    return getList(listId);
  }

  public void delete(String listId) {
    listRepository.deleteById(listId);
  }

  public UserList create(ListInfo listInfo) {
    var list = transformer.convert(listInfo);
    var insertedList = listRepository.insert(list);
    return getUserList(insertedList);
  }

  private UserList getUserList(MovieList list) {
    var itemsF =
      list.getItems().stream()
        .map(
          i ->
            CompletableFuture.supplyAsync(() -> connector.getMovie(i.getId()), taskExecutor)
              .thenApply(m -> transformer.convert(i, m)))
        .collect(Collectors.toList());
    var movieItems =
      itemsF.stream()
        .map(CompletableFuture::join)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    return transformer.convert(list, movieItems);

  }


}
