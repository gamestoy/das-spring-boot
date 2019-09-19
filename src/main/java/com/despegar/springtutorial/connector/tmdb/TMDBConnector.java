package com.despegar.springtutorial.connector.tmdb;

import com.despegar.springtutorial.config.TMDBConfig;
import com.despegar.springtutorial.connector.tmdb.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TMDBConnector {
  private RestTemplate client;
  private String token;
  private String host;

  @Autowired
  public TMDBConnector(RestTemplate tmdbClient, TMDBConfig config) {
    this.client = tmdbClient;
    this.host = config.getHost();
    this.token = config.getToken();
  }

  public MovieData getMovie(String id) {
    var url = host + "/movie/" + id;
    return client.getForObject(addApiKey(url), MovieData.class);
  }

  public Credits getCast(String movieId) {
    var url = host + "/movie/" + movieId + "/credits";
    return client.getForObject(addApiKey(url), Credits.class);
  }

  public Similar getSimilar(String movieId) {
    var url = host + "/movie/" + movieId + "/similar";
    return client.getForObject(addApiKey(url), Similar.class);
  }

  public ReviewResults getReviews(String movieId) {
    var url = host + "/movie/" + movieId + "/reviews";
    return client.getForObject(addApiKey(url), ReviewResults.class);
  }

  public TopRated getTopRated() {
    var url = host + "/movie/top_rated";
    return client.getForObject(addApiKey(url), TopRated.class);
  }

  public SearchResults search(String query, Integer page) {
    var url = host + "/search/movie?query=" + query + "&page=" + page;
    return client.getForObject(addApiKey(url), SearchResults.class);
  }

  private String addApiKey(String url) {
    return url + (url.contains("?") ? "&" : "?") + "api_key=" + this.token;
  }
}
