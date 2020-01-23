package com.despegar.dasboot.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
public class PerformanceAspect {
  private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

  @Around("@annotation(performance)")
  public Object trackExecutionTime(ProceedingJoinPoint joinPoint, Performance performance)
    throws Throwable {
    var start = Instant.now();
    var proceed = joinPoint.proceed();
    var finish = Instant.now();
    var name =
      performance.value().equals("")
        ? joinPoint.getSignature().toShortString()
        : performance.value();
    logger.info(
      String.format("Call to %s took %s ms", name, Duration.between(start, finish).toMillis()));
    return proceed;
  }

}
