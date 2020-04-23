package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class UserServiceAspect {

  @Around(value = "execution(* com.example.demo.service.UserService.getCacheableUsers(..))")
  public Object aroundGetCacheableUsers(ProceedingJoinPoint joinPoint) throws Throwable {
    log.debug("before getCacheableUsers");
    var result = joinPoint.proceed();
    log.debug("after getCacheableUsers");
    return result;
  }

  @Around(value = "execution(* com.example.demo.service.UserService.getPrivateUsersWithCacheableAnnotation(..))")
  public Object aroundGetPrivateUsersWithCacheableAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
    log.debug("before getPrivateUsersWithCacheableAnnotation");
    var result = joinPoint.proceed();
    log.debug("after getPrivateUsersWithCacheableAnnotation");
    return result;
  }

}
