package com.despegar.dasboot.connector.tmdb;

import com.despegar.dasboot.connector.tmdb.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

@Component
public class ErrorHandler implements ResponseErrorHandler {
  private Logger logger = LoggerFactory.getLogger(TMDBConnector.class);

  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    return !response.getStatusCode().is2xxSuccessful();
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    switch (response.getRawStatusCode()) {
      case 400:
        throw new TMDBBadRequestException("400 error calling TMDB");
      case 404:
        throw new TMDBNotFoundException("404 error calling TMDB");
      case 401:
        throw new TMDBUnauthorizedException("401 error calling TMDB");
      case 403:
        throw new TMDBForbiddenException("403 error calling TMDB");
      default:
        throw new TMDBUnexpectedErrorException(
            String.format("Error %s calling TMDB", response.getRawStatusCode()));
    }
  }

  @Override
  public void handleError(URI url, HttpMethod method, ClientHttpResponse response)
      throws IOException {
    logger.error(
        String.format(
            "Error %s executing %s %s",
            response.getRawStatusCode(), method.toString(), url.toString()));
    handleError(response);
  }
}
