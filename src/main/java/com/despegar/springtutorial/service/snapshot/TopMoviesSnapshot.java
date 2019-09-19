package com.despegar.springtutorial.service.snapshot;

import com.despegar.springtutorial.connector.tmdb.TMDBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class TopMoviesSnapshot {
  private static final Logger logger = LoggerFactory.getLogger(TopMoviesSnapshot.class);
  private TMDBConnector connector;
  private TopMovieTransformer transformer;
  private AtomicReference<Set<Long>> movies;

  @Autowired
  public TopMoviesSnapshot(TMDBConnector connector, TopMovieTransformer transformer) {
    this.movies = new AtomicReference<>(new HashSet<>());
    this.connector = connector;
    this.transformer = transformer;
    var topMovies = connector.getTopRated();
    movies.set(transformer.convert(topMovies));
  }

  public boolean isTopRated(Long movieId) {
    return movies.get().contains(movieId);
  }

  @Scheduled(fixedDelay = 600000, initialDelay = 600000)
  public void refresh() {
    logger.info("Refreshing top rated movies");
    var topMovies = connector.getTopRated();
    movies.set(transformer.convert(topMovies));
  }
}
