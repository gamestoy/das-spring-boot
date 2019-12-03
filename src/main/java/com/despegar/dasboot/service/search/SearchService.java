package com.despegar.dasboot.service.search;

import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import com.despegar.dasboot.connector.tmdb.dto.SearchMovie;
import com.despegar.dasboot.model.movie.MovieInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SearchService {
  private TMDBConnector connector;

  @Autowired
  public SearchService(TMDBConnector connector) {
    this.connector = connector;
  }

  public List<MovieInfo> search(String query, Integer page) {
    var results = connector.search(query, page);
    return results.getResults().stream().map(this::convert).collect(Collectors.toList());
  }

  private MovieInfo convert(SearchMovie movie) {
    var releaseYear =
        Optional.ofNullable(movie.getReleaseDate())
            .filter(d -> !d.isBlank())
            .map(LocalDate::parse)
            .map(d -> String.valueOf(d.getYear()))
            .orElse("");
    return new MovieInfo(movie.getId(), movie.getTitle(), releaseYear);
  }
}
