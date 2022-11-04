package com.turkraft.springfilter.boot;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.turkraft.springfilter.FilterUtils;

/**
 * Configuration class used to load the resolvers.
 */

@Configuration
public class SpringFilterWebConfig implements WebMvcConfigurer {

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

    if (FilterUtils.isSpecificationDependencyPresent()) {
      argumentResolvers.add(new SpecificationFilterArgumentResolver());
    }

    if (FilterUtils.isSpringDataMongoDbDependencyPresent()) {
      argumentResolvers.add(new BsonFilterArgumentResolver());
    }

  }

}
