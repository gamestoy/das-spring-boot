package com.despegar.dasboot.connector.tmdb.interceptors;

import com.despegar.dasboot.context.Context;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class CustomHeadersInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(
    HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    Context.getRequestContext().getHeaders().forEach((k, v) -> request.getHeaders().add(k, v));
    return execution.execute(request, body);
  }
}
