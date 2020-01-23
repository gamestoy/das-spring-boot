package com.despegar.dasboot.config;

import com.despegar.dasboot.context.ContextGenerator;
import com.despegar.dasboot.controller.interceptor.ContextInterceptor;
import com.despegar.dasboot.controller.interceptor.LoggingInterceptor;
import com.despegar.dasboot.controller.interceptor.XClientInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
  private ContextGenerator contextGenerator;

  @Autowired
  public MVCConfig(ContextGenerator contextGenerator) {
    this.contextGenerator = contextGenerator;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new ContextInterceptor(contextGenerator));
    registry.addInterceptor(new LoggingInterceptor());
    registry.addInterceptor(new XClientInterceptor());
  }
}
