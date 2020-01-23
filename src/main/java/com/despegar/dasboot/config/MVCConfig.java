package com.despegar.dasboot.config;

import com.despegar.dasboot.controller.interceptor.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new ContextInterceptor());
    registry.addInterceptor(new LoggingInterceptor());
    registry.addInterceptor(new XClientInterceptor());
  }
}
