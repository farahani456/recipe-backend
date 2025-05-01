package com.my.recipe.controller;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ControllerLoggingAspect {
  private final String ENTRY_LOG = "ENTRY: {} - {} ";
  private final String EXIT_LOG = "EXIT: {} - {}";

  @Before("execution(* com.my.recipe.controller..*(..))")
  public void logBefore(JoinPoint joinPoint) {
    String className = joinPoint.getTarget().getClass().getSimpleName();
    String methodName = joinPoint.getSignature().getName();

    log.info(ENTRY_LOG, className, methodName);
  }

  @AfterReturning(pointcut = "execution(* com.my.recipe.controller..*(..))")
  public void logAfter(JoinPoint joinPoint) {
    String className = joinPoint.getTarget().getClass().getSimpleName();
    String methodName = joinPoint.getSignature().getName();

    log.info(EXIT_LOG, className, methodName);
  }
}
