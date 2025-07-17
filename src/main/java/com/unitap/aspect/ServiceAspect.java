package com.unitap.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceAspect {

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void serviceMethods() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {
    }

    @Around("restControllerMethods()")
    public Object logRestControllerMethods(ProceedingJoinPoint jp) throws Throwable {
        log.info("RestController: Entering method: {} with args: {}", jp.getSignature().getName(), jp.getArgs());
        Object result = jp.proceed();
        log.info("RestController: Exiting method: {} with result: {}", jp.getSignature().getName(), result);

        return result;
    }

    @Around("serviceMethods()")
    public Object logServiceMethods(ProceedingJoinPoint jp) throws Throwable {
        log.info("Service: Entering method {} with args: {}", jp.getSignature().getName(), jp.getArgs());
        Object result = jp.proceed();
        log.info("Service: Exiting method {} with result: {}", jp.getSignature().getName(), result);

        return result;
    }
}
