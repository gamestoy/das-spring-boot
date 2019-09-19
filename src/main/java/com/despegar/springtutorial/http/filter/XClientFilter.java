package com.despegar.springtutorial.http.filter;

import com.despegar.springtutorial.http.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(2)
public class XClientFilter implements Filter {
  private static final Logger logger = LoggerFactory.getLogger(XClientFilter.class);

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    var httpServletRequest = (HttpServletRequest) servletRequest;
    var httpServletResponse = (HttpServletResponse) servletResponse;

    if (httpServletRequest.getHeader(Headers.X_CLIENT) == null) {
      logger.warn("Rejecting request: X-Client header not present");
      httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
    } else {
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }
}
