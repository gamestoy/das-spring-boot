package com.despegar.dasboot.controller.interceptor;

import com.despegar.dasboot.controller.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class XClientInterceptor implements HandlerInterceptor {
  private static final Logger logger = LoggerFactory.getLogger(XClientInterceptor.class);

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception {
    logger.info("Validating headers");
    if (request.getHeader(Headers.X_CLIENT) == null) {
      logger.warn("Rejecting request: X-Client header not present");
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
    return true;
  }
}
