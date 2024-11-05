package com.example.springlearning.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author 匠承
 * @Date: 2024/11/4 23:52
 */
@Aspect
@Component
public class HelloAspect {

    @Pointcut("@annotation(com.example.springlearning.annotation.Hello)")
    public void openApiV2Pointcut() {}

    // 在执行AopController的每个方法前执行:
    @Before("openApiV2Pointcut()")
    public void doAccessCheck() {
        System.err.println("[HelloAspect][Before] do access check...");
    }

    // 在执行AopController的每个方法前后执行:
    @Around("openApiV2Pointcut()")
    public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
        System.err.println("[HelloAspect][Around] start " + pjp.getSignature());
        Object retVal = pjp.proceed();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        System.err.println("[HelloAspect][Around] done " + pjp.getSignature());

        System.out.println(method.getReturnType());
        return retVal;
    }
}
