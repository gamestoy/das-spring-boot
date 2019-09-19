package com.despegar.springtutorial.connector.tmdb;

import com.despegar.springtutorial.config.TMDBConfig;
import com.despegar.springtutorial.connector.CustomHeadersInterceptor;
import com.despegar.springtutorial.connector.LoggingInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class Config {

  @Bean
  @Autowired
  public RestTemplate tmdbClient(TMDBConfig tmdbConfig) {
    var config = RequestConfig.custom().build();
    CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    var factory = new HttpComponentsClientHttpRequestFactory(client);
    factory.setConnectTimeout(tmdbConfig.getConnectionTimeout());
    factory.setReadTimeout(tmdbConfig.getTimeout());
    var restTemplate = new RestTemplate(factory);
    restTemplate.setInterceptors(
        Arrays.asList(new CustomHeadersInterceptor(), new LoggingInterceptor()));
    return restTemplate;
  }
}
