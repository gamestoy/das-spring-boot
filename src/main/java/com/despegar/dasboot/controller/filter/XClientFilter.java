package com.despegar.dasboot.controller.filter;

import com.despegar.dasboot.controller.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class XClientFilter implements Filter {
  private static final Logger logger = LoggerFactory.getLogger(XClientFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    logger.info("Validating headers");
    if (req.getHeader(Headers.X_CLIENT) == null) {
      logger.warn("Rejecting request: X-Client header not present");
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    } else {
      chain.doFilter(req, res);
    }
  }
}
