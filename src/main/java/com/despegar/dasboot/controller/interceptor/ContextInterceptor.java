package com.despegar.dasboot.controller.interceptor;

import com.despegar.dasboot.context.Context;
import com.despegar.dasboot.context.ContextGenerator;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContextInterceptor implements HandlerInterceptor {
  private ContextGenerator contextGenerator;

  public ContextInterceptor(ContextGenerator contextGenerator) {
    this.contextGenerator = contextGenerator;
  }

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception {
    var requestContext = contextGenerator.createContext(request);
    contextGenerator.addToMVC(requestContext);
    Context.setRequestContext(requestContext);

    return true;
  }

}
