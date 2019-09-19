package com.despegar.springtutorial.service.snapshot;

import com.despegar.springtutorial.connector.tmdb.dto.TopRated;
import com.despegar.springtutorial.model.movie.MovieInfo;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TopMovieTransformer {

  public Set<Long> convert(TopRated topRated) {
    return topRated.getResults().stream().map(MovieInfo::getId).collect(Collectors.toSet());
  }
}
