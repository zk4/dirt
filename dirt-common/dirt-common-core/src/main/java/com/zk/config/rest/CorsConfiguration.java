package com.zk.config.rest;

import com.zk.config.tenant.TenantInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// TODO: 感觉内容不太对。cors 就 cors
@Configuration
public class CorsConfiguration {


    public CorsConfiguration() {
        System.out.println("hello");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }

            /**
             * 添加拦截器
             * @param registry .
             */
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //注册拦截器
                //registry.addInterceptor(new RequestInterceptor());

                registry.addWebRequestInterceptor(new TenantInterceptor());
                //registration.addPathPatterns("/**");
            }
        };
    }

}
