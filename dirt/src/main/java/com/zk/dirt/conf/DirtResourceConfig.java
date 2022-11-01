package com.zk.dirt.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DirtResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/dirt/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/dirt/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/dirt/**").addResourceLocations("classpath:/static/");
    }
}
