package com.despegar.dasboot.service.review;

import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import com.despegar.dasboot.connector.tmdb.dto.Review;
import com.despegar.dasboot.model.review.MovieReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
  private TMDBConnector connector;

  @Autowired
  public ReviewService(TMDBConnector connector) {
    this.connector = connector;
  }

  public List<MovieReview> getMovieReviews(String movieId) {
    try {
      var reviews = connector.getReviews(movieId);
      return reviews.getResults().stream().map(this::convert).collect(Collectors.toList());
    } catch (HttpClientErrorException e) {
      return Collections.emptyList();
    }
  }

  private MovieReview convert(Review review) {
    return new MovieReview(review.getId(), review.getAuthor(), review.getContent());
  }
}
