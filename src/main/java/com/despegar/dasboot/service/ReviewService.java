package com.despegar.dasboot.service;

import com.despegar.dasboot.model.review.MovieReview;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

  public ReviewService() {}

  public List<MovieReview> getMovieReviews(String movieId) {
    return Collections.singletonList(
        new MovieReview(UUID.randomUUID().toString(), "user", "Malísima"));
  }
}
