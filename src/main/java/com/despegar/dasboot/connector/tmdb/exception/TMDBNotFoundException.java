package com.despegar.dasboot.connector.tmdb.exception;

import com.despegar.dasboot.connector.exception.APIException;

public class TMDBNotFoundException extends APIException {

  public TMDBNotFoundException(String message) {
    super(404, message);
  }
}
