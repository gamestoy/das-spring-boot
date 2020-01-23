package com.despegar.dasboot.service.snapshot;

import com.despegar.dasboot.connector.tmdb.dto.TopRated;
import com.despegar.dasboot.model.movie.MovieInfo;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TopMovieTransformer {

  public Set<Long> convert(TopRated topRated) {
    return topRated.getResults().stream().map(MovieInfo::getId).collect(Collectors.toSet());
  }
}
