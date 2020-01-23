package com.despegar.dasboot.connector.tmdb.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {
  private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

  @Override
  public ClientHttpResponse intercept(
    HttpRequest request, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
    logger.info(String.format("Call %s %s", request.getMethodValue(), request.getURI().toString()));
    return execution.execute(request, bytes);
  }
}
