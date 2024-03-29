package com.zk.dirt.autoconfig;

import com.zk.dirt.JPAPersistProxyImp;
import com.zk.dirt.MetaTableProvider;
import com.zk.dirt.TableColumnsProvider;
import com.zk.dirt.aop.DirtActionAspect;
import com.zk.dirt.entity.MetaType;
import com.zk.dirt.rest.DirtController;
import com.zk.dirt.service.DirtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/***
 * Dirt AutoConfiguration
 * @author <a href="mailto:xiaoymin@foxmail.com">liuzq7@gmail.com</a>
 * 2023/2/28
 */
@Configuration
@EnableConfigurationProperties({DirtProperties.class})
@ConditionalOnProperty(name = "dirt.enable",havingValue = "true")
@Import({
        DirtService.class,
        DirtActionAspect.class,
        JPAPersistProxyImp.class,
        MetaTableProvider.class,
        TableColumnsProvider.class,
        DirtController.class,
        MetaType.class
})
@EntityScan(basePackages = {"com.zk.dirt.entity"})
public class DirtAutoConfiguration {

    private final Environment environment;

    Logger logger= LoggerFactory.getLogger(DirtAutoConfiguration.class);

    public DirtAutoConfiguration(Environment environment) {
        this.environment = environment;
    }

    /**
     * Configuration CorsFilter
     * @since 2.0.4
     * @return
     */
    @Bean("dirtCorsFilter")
    @ConditionalOnMissingBean(CorsFilter.class)
    @ConditionalOnProperty(name = "dirt.cors",havingValue = "true")
    public CorsFilter corsFilter() {
        logger.info("init CorsFilter...");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setMaxAge(10000L);
        //匹配所有API
        source.registerCorsConfiguration("/**", corsConfiguration);
        CorsFilter corsFilter = new CorsFilter(source);
        return corsFilter;
    }
}
