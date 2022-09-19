//package com.zk.dirt.rest.async;
//
//import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.AsyncConfigurer;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.concurrent.Executor;
//
//@Configuration
//@EnableAsync
//public class AsyncConfiguration implements WebMvcConfigurer, AsyncConfigurer {
//    // named executor
//    // 这样使用 @Async("threadPoolTaskExecutor")
//    @Bean(name = "threadPoolTaskExecutor")
//    public Executor executor1(){
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(4);
//        executor.setMaxPoolSize(4);
//        executor.setQueueCapacity(50);
//        executor.setThreadNamePrefix("M#Thread::");
//        executor.initialize();
//        return executor;
//    }
//
//
//    // application level executor
//    // 这样使用 @Async
//    @Override
//    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(4);
//        executor.setMaxPoolSize(4);
//        executor.setQueueCapacity(50);
//        executor.setThreadNamePrefix("A#Thread::");
//        executor.initialize();
//        configurer.setTaskExecutor(executor);
//    }
//
//    // 配置超时异常处理
//    @Override
//    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return new AsyncExceptionHandler();
//    }
//}