package com.uva.productservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/** Class to log the time that each method takes in the system. */
@Aspect
@Component
@Slf4j
public class ExecutionTimeLogger {

  /**
   * Logs the time that the execution of the passed method takes.
   *
   * @param joinPoint with the method to observe the execution time.
   * @return the result of the observed method.
   * @throws Throwable any exception threw by the observed method.
   */
  @Around("execution(* com.uva.productservice..*(..))")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.nanoTime();

    Object result = joinPoint.proceed();

    long end = System.nanoTime();
    long durationMs = (end - start) / 1_000_000;

    String methodName = joinPoint.getSignature().toShortString();
    log.info("Execution time of " + methodName + " :: " + durationMs + " ms");

    return result;
  }
}
