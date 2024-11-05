package com.example.springlearning.aop;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author 匠承
 * @Date: 2024/11/4 23:28
 */
@Aspect
@Component
public class LoggingAspect {
    // 在执行AopController的每个方法前执行:
    @Before("execution(public * com.example.springlearning.controller.AopController.*(..))")
    public void doAccessCheck() {
        System.err.println("[Before] do access check...");
    }

    // 在执行AopController的每个方法前后执行:
    @Around("execution(public * com.example.springlearning.controller.AopController.*(..))")
    public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
        System.err.println("[Around] start " + pjp.getSignature());
        Object retVal = pjp.proceed();
        System.err.println("[Around] done " + pjp.getSignature());
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Class<?> returnType = method.getReturnType();
        System.out.println("[Around] class: " + returnType);
        if (returnType == Date.class) {
            System.out.println("is date");
        }

        throw new RuntimeException("hello exception ");
    }
}
