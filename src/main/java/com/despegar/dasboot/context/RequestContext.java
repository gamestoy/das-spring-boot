package com.despegar.dasboot.context;

import java.util.Map;

public class RequestContext {
  private Map<String, String> headers;

  public RequestContext(Map<String, String> headers) {
    this.headers = headers;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }
}
