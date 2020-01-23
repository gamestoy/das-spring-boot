package com.despegar.dasboot.repository;

import com.despegar.dasboot.repository.entities.MovieItem;
import com.despegar.dasboot.repository.entities.MovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class ListRepositoryImpl implements MovieListOperations {
  private MongoTemplate template;

  @Autowired
  public ListRepositoryImpl(MongoTemplate template) {
    this.template = template;
  }

  @Override
  public long addMovieToList(String listId, List<MovieItem> movieIds) {
    var query = Query.query(Criteria.where(Fields.ID).is(listId));
    var update = new Update().push(Fields.ITEMS).each(movieIds);
    return template.updateFirst(query, update, MovieList.class).getModifiedCount();
  }

  @Override
  public long removeMovieFromList(String listId, List<String> movieIds) {
    var query = Query.query(Criteria.where(Fields.ID).is(listId));
    var update = new Update().pull(Fields.ITEMS, Query.query(Criteria.where(Fields.ID).in(movieIds)));
    return template.updateFirst(query, update, MovieList.class).getModifiedCount();
  }

}
