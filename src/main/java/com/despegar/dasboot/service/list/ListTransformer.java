package com.despegar.dasboot.service.list;

import com.despegar.dasboot.connector.tmdb.dto.MovieData;
import com.despegar.dasboot.model.list.ListInfo;
import com.despegar.dasboot.model.list.UserList;
import com.despegar.dasboot.model.list.UserListItem;
import com.despegar.dasboot.model.movie.MovieInfo;
import com.despegar.dasboot.repository.entities.MovieItem;
import com.despegar.dasboot.repository.entities.MovieList;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Component
public class ListTransformer {

  UserList convert(MovieList list, List<UserListItem> movieItems) {
    return new UserList(
      list.getId(),
      list.getUser(),
      list.getName(),
      movieItems,
      list.getCreated()
    );
  }

  MovieList convert(ListInfo list) {
    return new MovieList(list.getName(), list.getUser(), new HashSet<>(), LocalDate.now());
  }

  MovieItem convert(String movieId) {
    return new MovieItem(movieId, LocalDate.now());
  }

  UserListItem convert(MovieItem item, MovieData movie) {
    var info = new MovieInfo(movie.getId(), movie.getTitle(), movie.getReleaseDate());
    return new UserListItem(info, item.getAdded());
  }
}
