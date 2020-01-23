package com.despegar.dasboot.connector.tmdb.exception;

import com.despegar.dasboot.connector.exception.APIException;

public class TMDBBadRequestException extends APIException {

  public TMDBBadRequestException(String message) {
    super(400, message);
  }
}
