package com.despegar.springtutorial.service.movie;

import com.despegar.springtutorial.connector.tmdb.TMDBConnector;
import com.despegar.springtutorial.model.movie.Movie;
import com.despegar.springtutorial.service.exception.ServiceException;
import com.despegar.springtutorial.service.review.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class MovieService {
  private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

  private TMDBConnector connector;
  private MovieTransformer transformer;
  private ReviewService reviewService;
  private AsyncTaskExecutor taskExecutor;

  @Autowired
  public MovieService(
      TMDBConnector connector,
      MovieTransformer transformer,
      ReviewService reviewService,
      AsyncTaskExecutor commonThreadPoolTaskExecutor) {
    this.connector = connector;
    this.reviewService = reviewService;
    this.taskExecutor = commonThreadPoolTaskExecutor;
    this.transformer = transformer;
  }

  public Movie get(String id) {
    Instant start = Instant.now();
    logger.debug("Retrieving movie");

    var movieData = connector.getMovie(id);
    var creditsF = CompletableFuture.supplyAsync(() -> connector.getCast(id), taskExecutor);
    var similarMoviesF =
        CompletableFuture.supplyAsync(() -> connector.getSimilar(id), taskExecutor);
    var reviewsF =
        CompletableFuture.supplyAsync(() -> reviewService.getMovieReviews(id), taskExecutor);

    try {
      Movie movie =
          creditsF
              .thenCompose(
                  c ->
                      similarMoviesF.thenCombine(
                          reviewsF, (s, r) -> transformer.convert(movieData, c, r, s)))
              .get();
      Instant finish = Instant.now();
      logger.info(
          "MovieService.get duration: " + Duration.between(start, finish).toMillis() + "ms");
      return movie;
    } catch (InterruptedException | ExecutionException e) {
      throw new ServiceException("Error retrieving movie: ", e);
    }
  }
}
