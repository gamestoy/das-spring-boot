package com.despegar.springtutorial.repository;

import com.despegar.springtutorial.repository.entities.MovieList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ListRepository extends MongoRepository<MovieList, String>, MovieOperations {

  List<MovieList> findAllByUser(String user);
}
