package com.selab.webexample.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Aspect
@Component
public class ServiceAspect {

    @Around("execution(* com.selab.webexample.service.*.*(..))")
    public Object doAroundService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Signature proceedingSignature = proceedingJoinPoint.getSignature();

        long startTime = Instant.now().toEpochMilli();
        Object result = proceedingJoinPoint.proceed();
        long endTime = Instant.now().toEpochMilli();

        long elapsed = endTime - startTime;
        String logMessage = String.format("%s: finish in %sms", proceedingSignature.toShortString(), elapsed);
        if (elapsed < 300) {
            log.trace(logMessage);
        } else {
            log.warn(logMessage);
        }

        return result;
    }
}
