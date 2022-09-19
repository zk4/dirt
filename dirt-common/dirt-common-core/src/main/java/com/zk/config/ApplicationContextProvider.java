package com.zk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    private static void setContext(ApplicationContext ctx) {
        ApplicationContextProvider.applicationContext = ctx;
    }

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        setContext(ctx);
    }

    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }
}