package com.despegar.dasboot.controller.interceptor;

import com.despegar.dasboot.context.Context;
import com.despegar.dasboot.context.RequestContext;
import com.despegar.dasboot.controller.Headers;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ContextInterceptor implements HandlerInterceptor {
  private static final String HOSTNAME = "hostname";
  private static final String UOW = "uow";
  private String hostname;

  public ContextInterceptor() {
    try {
      this.hostname = InetAddress.getLocalHost().getHostName();
    } catch (Exception e) {
      this.hostname = "UNKNOWN";
    }
  }

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception {
    var uow =
      Optional.ofNullable(request.getHeader(Headers.UOW))
        .orElseGet(() -> UUID.randomUUID().toString());
    setContext(request, uow);
    MDC.put(HOSTNAME, hostname);
    MDC.put(UOW, uow);

    return true;
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
