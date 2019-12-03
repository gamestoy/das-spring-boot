package com.despegar.dasboot.connector.tmdb.exception;

import com.despegar.dasboot.connector.exception.APIException;

public class TMDBUnexpectedErrorException extends APIException {

  public TMDBUnexpectedErrorException(String message) {
    super(500, message);
  }

}
