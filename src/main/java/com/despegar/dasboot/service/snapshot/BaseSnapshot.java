package com.despegar.dasboot.service.snapshot;

import com.despegar.dasboot.context.ContextGenerator;
import org.springframework.beans.factory.annotation.Autowired;

abstract class BaseSnapshot {
  private ContextGenerator contextGenerator;

  protected abstract String getId();

  protected void initTrace() {
    var requestContext = this.contextGenerator.createContext(getId());
    this.contextGenerator.addToMVC(requestContext);
  }

  @Autowired
  public final void setContextGenerator(ContextGenerator contextGenerator) {
    this.contextGenerator = contextGenerator;
  }
}
