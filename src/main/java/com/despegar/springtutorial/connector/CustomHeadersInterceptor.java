package com.despegar.springtutorial.connector;

import com.despegar.springtutorial.context.Context;
import com.despegar.springtutorial.context.RequestContext;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Optional;

public class CustomHeadersInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    Optional.ofNullable(Context.getRequestContext())
        .map(RequestContext::getHeaders)
        .ifPresent(h -> h.forEach((k, v) -> request.getHeaders().add(k, v)));
    return execution.execute(request, body);
  }
}
