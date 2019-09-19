package com.despegar.springtutorial.http.filter;

import com.despegar.springtutorial.context.Context;
import com.despegar.springtutorial.context.RequestContext;
import com.despegar.springtutorial.http.Headers;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Order(1)
public class ContextFilter implements Filter {
  private static final String HOSTNAME = "hostname";
  private static final String UOW = "uow";
  private String hostname;

  public ContextFilter() throws UnknownHostException {
    this.hostname = InetAddress.getLocalHost().getHostName();
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    var httpServletRequest = (HttpServletRequest) request;

    var uow =
        Optional.ofNullable(httpServletRequest.getHeader(Headers.UOW))
            .orElseGet(() -> UUID.randomUUID().toString());

    setContext(httpServletRequest, uow);

    MDC.put(HOSTNAME, hostname);
    MDC.put(UOW, uow);

    chain.doFilter(request, response);
  }

  private void setContext(HttpServletRequest request, String uow) {
    var customHeaders =
        Collections.list(request.getHeaderNames()).stream()
            .filter(h -> h.startsWith("XMOVIE"))
            .collect(Collectors.toMap(Function.identity(), request::getHeader));

    customHeaders.put(Headers.UOW, uow);
    var requestContext = new RequestContext(customHeaders);
    Context.setRequestContext(requestContext);
  }
}
