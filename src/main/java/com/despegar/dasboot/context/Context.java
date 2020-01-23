package com.despegar.dasboot.context;

public class Context {
  private static final ThreadLocal<RequestContext> state = new ThreadLocal<>();

  public static void setRequestContext(RequestContext requestContext) {
    state.set(requestContext);
  }

  public static RequestContext getRequestContext() {
    return state.get();
  }

  public static void clear() {
    state.remove();
  }
}
