package com.despegar.dasboot.controller.error;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class ErrorAttributes extends DefaultErrorAttributes {

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
    var errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
    return Map.of(
        "status", errorAttributes.get("status"), "message", errorAttributes.get("message"));
  }
}
