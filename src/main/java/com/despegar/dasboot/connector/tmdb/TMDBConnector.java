package com.despegar.dasboot.connector.tmdb;

import com.despegar.dasboot.aop.Performance;
import com.despegar.dasboot.config.TMDBConfig;
import com.despegar.dasboot.connector.tmdb.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TMDBConnector {
  private static final String MOVIE_URL = "/3/movie/{id}?api_key={token}";
  private static final String TOP_RATED_URL = "/3/movie/top_rated?api_key={token}";
  private static final String CREDITS_URL = "/3/movie/{id}/credits?api_key={token}";
  private static final String SIMILAR_URL = "/3/movie/{id}/similar?api_key={token}";
  private static final String REVIEWS_URL = "/3/movie/{id}/reviews?api_key={token}";
  private static final String SEARCH_URL =
      "/3/search/movie?api_key={token}&query={query}&page={page}";
  private RestTemplate client;
  private String token;

  @Autowired
  public TMDBConnector(RestTemplate tmdbClient, TMDBConfig config) {
    this.client = tmdbClient;
    this.token = config.getToken();
  }

  @Performance
  public MovieData getMovie(String id) {
    return client.getForObject(MOVIE_URL, MovieData.class, id, token);
  }

  @Performance
  public Credits getCast(String id) {
    return client.getForObject(CREDITS_URL, Credits.class, id, token);
  }

  @Performance
  public SimilarMovies getSimilarMovies(String id) {
    return client.getForObject(SIMILAR_URL, SimilarMovies.class, id, token);
  }

  @Performance
  public ReviewResults getReviews(String id) {
    return client.getForObject(REVIEWS_URL, ReviewResults.class, id, token);
  }

  @Performance
  public SearchResults search(String query, Integer page) {
    return client.getForObject(SEARCH_URL, SearchResults.class, token, query, page);
  }

  @Performance
  public TopRated getTopRated() {
    return client.getForObject(TOP_RATED_URL, TopRated.class, token);
  }
}
