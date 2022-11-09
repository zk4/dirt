package com.zk.dirt.split.advice;

import com.zk.dirt.split.SplitInvokerRegistry;
import com.zk.dirt.split.intef.SplitInvoker;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

@Slf4j
public class SplitInterceptor implements MethodInterceptor {
    private final SplitInvokerRegistry splitService;

    public SplitInterceptor(SplitInvokerRegistry splitService) {
        this.splitService = splitService;
    }


    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Object target = methodInvocation.getThis();
        Object[] params = methodInvocation.getArguments();

        SplitInvoker splitInvoker = this.splitService.getByMethod(method);
        if (splitInvoker == null){
            log.warn("failed to find split invoker for method {}", method);
            return method.invoke(target, params);
        }else {
            return splitInvoker.invoke(target, params);
        }
    }
}
