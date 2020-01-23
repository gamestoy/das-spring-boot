package com.despegar.dasboot.service.movie;

import com.despegar.dasboot.aop.Performance;
import com.despegar.dasboot.connector.exception.APIException;
import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import com.despegar.dasboot.connector.tmdb.dto.Credits;
import com.despegar.dasboot.connector.tmdb.dto.SimilarMovies;
import com.despegar.dasboot.model.movie.Movie;
import com.despegar.dasboot.service.exception.ServiceException;
import com.despegar.dasboot.service.review.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class MovieService {
  private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
  private TMDBConnector connector;
  private ReviewService reviewService;
  private MovieTransformer transformer;
  private AsyncTaskExecutor taskExecutor;

  @Autowired
  public MovieService(
      TMDBConnector connector, ReviewService reviewService, MovieTransformer transformer, AsyncTaskExecutor commonThreadPoolTaskExecutor) {
    this.connector = connector;
    this.reviewService = reviewService;
    this.transformer = transformer;
    this.taskExecutor = commonThreadPoolTaskExecutor;
  }

  @Performance
  public Optional<Movie> get(String id) {
    logger.debug("Retrieving movie");

    var movieData = connector.getMovie(id);
    var creditsF = CompletableFuture.supplyAsync(() -> getCast(id), taskExecutor);
    var similarMoviesF =
      CompletableFuture.supplyAsync(() -> getSimilarMovies(id), taskExecutor);
    var reviewsF =
      CompletableFuture.supplyAsync(() -> reviewService.getMovieReviews(id), taskExecutor);
    try {
      var movie =
        creditsF
          .thenCompose(
            c ->
              similarMoviesF.thenCombine(
                reviewsF, (s, r) -> transformer.convert(movieData, c, r, s)))
          .get();
      return Optional.of(movie);
    } catch (InterruptedException | ExecutionException e) {
      throw new ServiceException("Error retrieving movie: ", e);
    }
  }

  private Optional<Credits> getCast(String id) {
    try {
      return Optional.ofNullable(connector.getCast(id));
    } catch (APIException e) {
      return Optional.empty();
    }
  }

  private Optional<SimilarMovies> getSimilarMovies(String id) {
    try {
      return Optional.ofNullable(connector.getSimilarMovies(id));
    } catch (APIException e) {
      return Optional.empty();
    }
  }
}
