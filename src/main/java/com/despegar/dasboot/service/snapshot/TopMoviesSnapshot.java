package com.despegar.dasboot.service.snapshot;

import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class TopMoviesSnapshot extends BaseSnapshot {
  private static final String ID = "top-movies";
  private static final Logger logger = LoggerFactory.getLogger(TopMoviesSnapshot.class);
  private TMDBConnector connector;
  private TopMovieTransformer transformer;
  private AtomicReference<Set<Long>> movies;

  @Autowired
  public TopMoviesSnapshot(TMDBConnector connector, TopMovieTransformer transformer) {
    this.movies = new AtomicReference<>(new HashSet<>());
    this.connector = connector;
    this.transformer = transformer;
  }

  public boolean isTopRated(Long movieId) {
    return movies.get().contains(movieId);
  }

  @Scheduled(fixedDelay = 60000)
  public void refresh() {
    initTrace();
    logger.info("Refreshing top rated movies");
    var topMovies = connector.getTopRated();
    movies.set(transformer.convert(topMovies));
  }

  @Override
  protected String getId() {
    return ID;
  }
}

