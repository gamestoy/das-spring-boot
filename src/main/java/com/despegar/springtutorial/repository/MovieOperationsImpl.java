package com.despegar.springtutorial.repository;

import com.despegar.springtutorial.repository.entities.MovieItem;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDate;

public class MovieOperationsImpl implements MovieOperations {
  private MongoTemplate template;

  @Autowired
  public MovieOperationsImpl(MongoTemplate template) {
    this.template = template;
  }

  @Override
  public void addMovieToList(String listId, String movieId) {
    var query = Query.query(Criteria.where("_id").is(listId));
    var update = new Update().push("items", new MovieItem(movieId, LocalDate.now()));
    template.updateFirst(query, update, "movie_list");
  }

  @Override
  public void removeMovieToList(String listId, String movieId) {
    var query = Query.query(Criteria.where("_id").is(listId));
    var update = new Update().pull("items", new Document("_id", movieId));
    template.updateFirst(query, update, "movie_list");
  }


}
