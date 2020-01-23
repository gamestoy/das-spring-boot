package com.despegar.dasboot.context;

import com.despegar.dasboot.controller.Headers;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ContextGenerator {
  private static final String REQUEST_UOW_PREFIX = "req";
  private static final String CUSTOM_HEADERS_PREFIX = "XMOVIE";
  private static final String HOSTNAME = "hostname";
  private static final String UOW = "uow";
  private TraceGenerator traceGenerator;

  public ContextGenerator(TraceGenerator traceGenerator) {
    this.traceGenerator = traceGenerator;
  }

  public RequestContext createContext(HttpServletRequest request) {
    var customHeaders =
      Collections.list(request.getHeaderNames()).stream()
        .filter(h -> h.startsWith(CUSTOM_HEADERS_PREFIX))
        .collect(Collectors.toMap(Function.identity(), request::getHeader));


    var uow = Optional.ofNullable(request.getHeader(Headers.UOW))
      .orElseGet(() -> traceGenerator.generateUOW(REQUEST_UOW_PREFIX));

    customHeaders.put(Headers.UOW, uow);
    return new RequestContext(uow, customHeaders);
  }

  public RequestContext createContext(String id) {
    var uow = traceGenerator.generateUOW(id);
    var customHeaders = Map.of(Headers.UOW, uow);
    return new RequestContext(uow, customHeaders);
  }

  public void addToMVC(RequestContext requestContext) {
    MDC.put(HOSTNAME, traceGenerator.getHostname());
    MDC.put(UOW, requestContext.getUow());
  }
}
