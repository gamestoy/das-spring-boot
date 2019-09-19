package com.despegar.springtutorial.http.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LoggingInterceptor implements HandlerInterceptor {
  private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    var headersStr = getHeaders(request);
    logger.info(String.format("Init %s with headers [%s]", request.getRequestURL(), headersStr));
    return true;
  }

  private String getHeaders(HttpServletRequest request) {
    return Collections.list(request.getHeaderNames()).stream()
        .collect(
            Collectors.toMap(
                Function.identity(),
                h -> String.join(", ", Collections.list(request.getHeaders(h)))))
        .entrySet()
        .stream()
        .map(e -> e.getKey() + "=" + e.getValue())
        .collect(Collectors.joining(" | "));
  }
}
