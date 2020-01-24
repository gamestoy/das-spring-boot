package com.despegar.dasboot.service.movie;

import com.despegar.dasboot.connector.tmdb.dto.*;
import com.despegar.dasboot.model.movie.Movie;
import com.despegar.dasboot.model.movie.MovieCast;
import com.despegar.dasboot.model.movie.MovieCrew;
import com.despegar.dasboot.model.movie.MovieInfo;
import com.despegar.dasboot.model.review.MovieReview;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieTransformer {
  private static final Set<String> jobs =
      new HashSet<>(Arrays.asList("director", "screenplay", "novel"));

  public Movie convert(
      MovieData movie,
      Optional<Credits> credits,
      List<MovieReview> reviews,
      Optional<SimilarMovies> similar) {
    var genres = movie.getGenres().stream().map(Genre::getName).collect(Collectors.toList());
    var cast =
        credits
            .map(
                c -> c.getCast().stream().limit(10).map(this::convert).collect(Collectors.toList()))
            .orElse(Collections.emptyList());
    var crew =
        credits
            .map(
                c ->
                    c.getCrew().stream()
                        .filter(cr -> jobs.contains(cr.getJob().toLowerCase()))
                        .map(this::convert)
                        .collect(Collectors.toList()))
            .orElse(Collections.emptyList());
    var similarMovies =
        similar
            .map(
                s ->
                    s.getResults().stream()
                        .limit(5)
                        .map(this::convert)
                        .collect(Collectors.toList()))
            .orElse(Collections.emptyList());
    return new Movie(
        String.valueOf(movie.getId()),
        movie.getTitle(),
        movie.getOverview(),
        genres,
        cast,
        crew,
        reviews,
        similarMovies);
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
