package com.despegar.dasboot.util;

import com.despegar.dasboot.context.Context;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

public class ContextTaskDecorator implements TaskDecorator {

  @Override
  public Runnable decorate(Runnable runnable) {
    var mdcMap = MDC.getCopyOfContextMap();
    var context = Context.getRequestContext();
    return () -> {
      try {
        Context.setRequestContext(context);
        MDC.setContextMap(mdcMap);
        runnable.run();
      } finally {
        MDC.clear();
        Context.clear();
      }
    };
  }
}
