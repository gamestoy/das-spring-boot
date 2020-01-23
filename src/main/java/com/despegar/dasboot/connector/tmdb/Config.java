package com.despegar.dasboot.connector.tmdb;

import com.despegar.dasboot.config.TMDBConfig;
import com.despegar.dasboot.connector.tmdb.interceptor.CustomHeadersInterceptor;
import com.despegar.dasboot.connector.tmdb.interceptor.LoggingInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Arrays;

@Component
public class Config {

  @Bean
  @Autowired
  public RestTemplate tmdbClient(TMDBConfig tmdbConfig, ErrorHandler errorHandler) {
    var config =
        RequestConfig.custom()
            .setConnectTimeout(tmdbConfig.getTimeout())
            .setSocketTimeout(tmdbConfig.getTimeout())
            .build();
    var client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    var factory = new HttpComponentsClientHttpRequestFactory(client);
    var restTemplate = new RestTemplate(factory);
    restTemplate.setInterceptors(Arrays.asList(new CustomHeadersInterceptor(), new LoggingInterceptor()));
    restTemplate.setErrorHandler(errorHandler);
    restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(tmdbConfig.getHost()));
    return restTemplate;
  }
}
