package com.despegar.dasboot.connector.tmdb.exception;

import com.despegar.dasboot.connector.exception.APIException;

public class TMDBForbiddenException extends APIException {

  public TMDBForbiddenException(String message) {
    super(403, message);
  }
}
