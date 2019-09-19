package com.despegar.springtutorial.service.movie;

import com.despegar.springtutorial.connector.tmdb.dto.*;
import com.despegar.springtutorial.model.movie.Movie;
import com.despegar.springtutorial.model.movie.MovieCast;
import com.despegar.springtutorial.model.movie.MovieCrew;
import com.despegar.springtutorial.model.movie.MovieInfo;
import com.despegar.springtutorial.model.review.MovieReview;
import com.despegar.springtutorial.service.snapshot.TopMoviesSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MovieTransformer {
  private static final Logger logger = LoggerFactory.getLogger(MovieTransformer.class);
  private static final Set<String> jobs =
      new HashSet<>(Arrays.asList("director", "screenplay", "novel"));
  private TopMoviesSnapshot topRatedSnapshot;

  @Autowired
  public MovieTransformer(TopMoviesSnapshot topRatedSnapshot) {
    this.topRatedSnapshot = topRatedSnapshot;
  }

  public Movie convert(
      MovieData movie, Credits credits, List<MovieReview> reviews, Similar similar) {
    logger.debug("Converting movie");
    var genres = movie.getGenres().stream().map(Genre::getName).collect(Collectors.toList());
    var cast = credits.getCast().stream().limit(10).map(this::convert).collect(Collectors.toList());
    var crew =
        credits.getCrew().stream()
            .filter(c -> jobs.contains(c.getJob().toLowerCase()))
            .map(this::convert)
            .collect(Collectors.toList());
    var similarMovies =
        similar.getResults().stream().limit(5).map(this::convert).collect(Collectors.toList());
    var topRated = topRatedSnapshot.isTopRated(movie.getId());
    return new Movie(
        String.valueOf(movie.getId()),
        movie.getTitle(),
        movie.getOverview(),
        genres,
        cast,
        crew,
        reviews,
        similarMovies,
        topRated);
  }

  private MovieCast convert(Cast cast) {
    return new MovieCast(cast.getId(), cast.getCharacter(), cast.getName(), cast.getProfilePath());
  }

  private MovieCrew convert(Crew crew) {
    return new MovieCrew(crew.getId(), crew.getJob(), crew.getName(), crew.getProfilePath());
  }

  private MovieInfo convert(SearchMovie movie) {
    var releaseYear =
        Optional.ofNullable(movie.getReleaseDate())
            .filter(d -> !d.isBlank())
            .map(LocalDate::parse)
            .map(d -> String.valueOf(d.getYear()))
            .orElse("");
    return new MovieInfo(movie.getId(), movie.getTitle(), releaseYear);
  }
}
