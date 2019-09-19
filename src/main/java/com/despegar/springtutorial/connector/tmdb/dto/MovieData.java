package com.despegar.springtutorial.connector.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class MovieData {
  private Long id;
  private String title;
  private String originalTitle;
  private String originalLanguage;
  private String overview;
  private String releaseDate;
  private Integer runtime;
  private Long revenue;
  private BigDecimal voteAverage;
  private List<Genre> genres;

  @JsonCreator
  public MovieData(
      @JsonProperty("id") Long id,
      @JsonProperty("title") String title,
      @JsonProperty("original_title") String originalTitle,
      @JsonProperty("original_language") String originalLanguage,
      @JsonProperty("overview") String overview,
      @JsonProperty("release_date") String releaseDate,
      @JsonProperty("runtime") Integer runtime,
      @JsonProperty("revenue") Long revenue,
      @JsonProperty("vote_average") BigDecimal voteAverage,
      @JsonProperty("genres") List<Genre> genres) {
    this.id = id;
    this.title = title;
    this.originalTitle = originalTitle;
    this.originalLanguage = originalLanguage;
    this.overview = overview;
    this.releaseDate = releaseDate;
    this.runtime = runtime;
    this.revenue = revenue;
    this.voteAverage = voteAverage;
    this.genres = genres;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public String getOriginalLanguage() {
    return originalLanguage;
  }

  public String getOverview() {
    return overview;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public Integer getRuntime() {
    return runtime;
  }

  public Long getRevenue() {
    return revenue;
  }

  public BigDecimal getVoteAverage() {
    return voteAverage;
  }

  public List<Genre> getGenres() {
    return genres;
  }
}
