package com.despegar.dasboot.repository;

import com.despegar.dasboot.repository.entities.MovieList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ListRepository extends MongoRepository<MovieList, String>, MovieListOperations {
}
