package com.despegar.dasboot.context;

import java.util.Map;

public class RequestContext {
  private Map<String, String> headers;
  private String uow;

  public RequestContext(String uow, Map<String, String> headers) {
    this.uow = uow;
    this.headers = headers;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public String getUow() {
    return uow;
  }
}
