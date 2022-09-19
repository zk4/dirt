package com.zk.dirt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DirtActionAspect {

    @Pointcut("@annotation(com.zk.dirt.annotation.DirtAction)")
    public void logPointCut() { }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        // todo
        return result;
    }

}
