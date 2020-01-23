package com.despegar.dasboot.service.movie;

import com.despegar.dasboot.connector.exception.APIException;
import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import com.despegar.dasboot.connector.tmdb.dto.Credits;
import com.despegar.dasboot.connector.tmdb.dto.SimilarMovies;
import com.despegar.dasboot.model.movie.Movie;
import com.despegar.dasboot.service.review.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class MovieService {
  private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
  private TMDBConnector connector;
  private ReviewService reviewService;
  private MovieTransformer transformer;

  @Autowired
  public MovieService(
      TMDBConnector connector, ReviewService reviewService, MovieTransformer transformer) {
    this.connector = connector;
    this.reviewService = reviewService;
    this.transformer = transformer;
  }

  public Optional<Movie> get(String id) {
    logger.debug("Retrieving movie");
    try {
      var movie = connector.getMovie(id);
      var credits = getCast(id);
      var similarMovies = getSimilarMovies(id);
      var reviews = reviewService.getMovieReviews(id);
      var movieInfo = transformer.convert(movie, credits, reviews, similarMovies);
      return Optional.of(movieInfo);
    } catch (HttpClientErrorException e) {
      return Optional.empty();
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
