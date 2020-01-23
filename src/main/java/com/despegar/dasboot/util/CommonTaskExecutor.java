package com.despegar.dasboot.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:threadpools.properties")
public class CommonTaskExecutor {
  private Integer corePoolSize;
  private Integer maxPoolSize;
  private Integer queue;

  public CommonTaskExecutor(
    @Value("${common.coresize}") Integer coreSize,
    @Value("${common.maxpoolsize}") Integer maxPoolSize,
    @Value("${common.queue}") Integer queue) {
    this.corePoolSize = coreSize;
    this.maxPoolSize = maxPoolSize;
    this.queue = queue;
  }

  @Bean
  public AsyncTaskExecutor commonThreadPoolTaskExecutor() {
    ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
    pool.setCorePoolSize(corePoolSize);
    pool.setMaxPoolSize(maxPoolSize);
    pool.setQueueCapacity(queue);
    pool.setWaitForTasksToCompleteOnShutdown(true);
    pool.setTaskDecorator(new ContextTaskDecorator());
    pool.initialize();
    return pool;
  }
}
