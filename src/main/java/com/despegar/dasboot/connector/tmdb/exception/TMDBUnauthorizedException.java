package com.despegar.dasboot.connector.tmdb.exception;

import com.despegar.dasboot.connector.exception.APIException;

public class TMDBUnauthorizedException extends APIException {

  public TMDBUnauthorizedException(String message) {
    super(401, message);
  }
}
