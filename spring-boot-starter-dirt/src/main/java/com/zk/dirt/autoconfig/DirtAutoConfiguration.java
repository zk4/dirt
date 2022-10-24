package com.zk.dirt.autoconfig;

import com.zk.dirt.JPAPersistProxyImp;
import com.zk.dirt.MetaTableProvider;
import com.zk.dirt.TableColumnsProvider;
import com.zk.dirt.aop.DirtActionAspect;
import com.zk.dirt.core.DirtContext;
import com.zk.dirt.entity.MetaType;
import com.zk.dirt.rest.DirtController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/***
 * Dirt AutoConfiguration
 * @author <a href="mailto:xiaoymin@foxmail.com">liuzq7@gmail.com</a>
 * 2019/10/22 21:08
 */
@Configuration
@EnableConfigurationProperties({DirtProperties.class})
@ConditionalOnProperty(name = "dirt.enable",havingValue = "true")
@Import({
        DirtContext.class,
        DirtActionAspect.class,
        JPAPersistProxyImp.class,
        MetaTableProvider.class,
        TableColumnsProvider.class,
        DirtController.class,
        MetaType.class
})
public class DirtAutoConfiguration {

    private final Environment environment;

    Logger logger= LoggerFactory.getLogger(DirtAutoConfiguration.class);

    public DirtAutoConfiguration(Environment environment) {
        this.environment = environment;
    }


    @ComponentScan(
            basePackages = {
                    "com.zk.dirt.entity",
                    "com.zk.dirt.core"
            }
    )
    public class DirtEnhanceAutoConfiguration{


        /**
         * Register Primary Bean with ServiceModelToSwagger2Mapper to Support i18n
         * @param knife4jProperties Knife4j properties
         * @param messageSource i18n MessageSource
         * @param modelMapper  modelMapper
         * @param parameterMapper parameterMapper
         * @param securityMapper securityMapper
         * @param licenseMapper licenseMapper
         * @param vendorExtensionsMapper vendorExtensionsMapper
         * @return ServiceModelToSwagger2Mapper
         */
        //@Bean
        //@ConditionalOnBean(value = MessageSource.class)
        //@Qualifier("ServiceModelToSwagger2Mapper")
        //@Primary
        //public Knife4jI18nServiceModelToSwagger2MapperImpl knife4jI18nServiceModelToSwagger2Mapper(Knife4jProperties knife4jProperties, MessageSource messageSource, ModelMapper modelMapper, ParameterMapper parameterMapper, SecurityMapper securityMapper, LicenseMapper licenseMapper, VendorExtensionsMapper vendorExtensionsMapper){
        //    Locale locale= Locale.forLanguageTag(knife4jProperties.getSetting().getLanguage().getValue());
        //    return new Knife4jI18nServiceModelToSwagger2MapperImpl(messageSource,locale,modelMapper,parameterMapper,securityMapper,licenseMapper,vendorExtensionsMapper);
        //}
    }

    /**
     * Configuration CorsFilter
     * @since 2.0.4
     * @return
     */
    @Bean("dirtCorsFilter")
    @ConditionalOnMissingBean(CorsFilter.class)
    @ConditionalOnProperty(name = "dirt.cors",havingValue = "true")
    public CorsFilter corsFilter(){
        logger.info("init CorsFilter...");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setMaxAge(10000L);
        //匹配所有API
        source.registerCorsConfiguration("/**",corsConfiguration);
        CorsFilter corsFilter=new CorsFilter(source);
        return corsFilter;
    }

    //
    ///**
    // * Auto register enhance Bean to process Knife4j function
    // * @param knife4jProperties enhance properties
    // * @return openapi-extension
    // */
    //@Bean(initMethod = "start")
    //@ConditionalOnMissingBean(OpenApiExtensionResolver.class)
    //@ConditionalOnProperty(name = "knife4j.enable",havingValue = "true")
    //public OpenApiExtensionResolver markdownResolver(Knife4jProperties knife4jProperties){
    //    Knife4jSetting setting=knife4jProperties.getSetting();
    //    if (setting==null){
    //        setting=new Knife4jSetting();
    //    }
    //    OpenApiExtendSetting extendSetting=new OpenApiExtendSetting();
    //    BeanUtils.copyProperties(setting,extendSetting);
    //    extendSetting.setLanguage(setting.getLanguage().getValue());
    //    return new OpenApiExtensionResolver(extendSetting, knife4jProperties.getDocuments());
    //}





}
