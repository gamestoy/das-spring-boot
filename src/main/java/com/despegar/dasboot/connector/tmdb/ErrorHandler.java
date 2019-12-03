package com.despegar.dasboot.connector.tmdb;

import com.despegar.dasboot.connector.tmdb.exception.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class ErrorHandler implements ResponseErrorHandler {
  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    return !response.getStatusCode().is2xxSuccessful();
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    switch(response.getRawStatusCode()) {
      case 400:
        throw new TMDBBadRequestException("400 error calling TMDB");
      case 404:
        throw new TMDBNotFoundException("404 error calling TMDB");
      case 401:
        throw new TMDBUnauthorizedException("401 error calling TMDB");
      case 403:
        throw new TMDBForbiddenException("403 error calling TMDB");
      default:
        throw new TMDBUnexpectedErrorException(String.format("Error %s calling TMDB", response.getRawStatusCode()));
    }
  }
}
