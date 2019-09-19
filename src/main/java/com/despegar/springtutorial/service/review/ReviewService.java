package com.despegar.springtutorial.service.review;

import com.despegar.springtutorial.connector.tmdb.TMDBConnector;
import com.despegar.springtutorial.connector.tmdb.dto.Review;
import com.despegar.springtutorial.model.review.MovieReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
  private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);
  private TMDBConnector connector;

  @Autowired
  public ReviewService(TMDBConnector connector) {
    this.connector = connector;
  }

  public List<MovieReview> getMovieReviews(String movieId) {
    logger.debug("Retrieving movie reviews");

    var reviews = connector.getReviews(movieId);
    return reviews.getResults().stream().map(this::convert).collect(Collectors.toList());
  }

  private MovieReview convert(Review review) {
    return new MovieReview(review.getId(), review.getAuthor(), review.getContent());
  }
}
