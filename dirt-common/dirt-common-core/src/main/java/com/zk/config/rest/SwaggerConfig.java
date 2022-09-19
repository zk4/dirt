package com.zk.config.rest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket docket(){

         return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                //修正Byte转string的Bug
                .directModelSubstitute(Byte.class, Integer.class)
                .alternateTypeRules(pageableTypeRule())
                .select()
                .apis(RequestHandlerSelectors.any())

                .paths(PathSelectors.any())
                .build();
                //.securitySchemes(securitySchemes())
                //.securityContexts(securityContexts());

    }

    private AlternateTypeRule pageableTypeRule() {
        return AlternateTypeRules.newRule(Pageable.class, Page.class, 1);
    }
    //构建api文档的详细信息函数
    private ApiInfo apiInfo(){

        return new ApiInfoBuilder()
                //页面标题
                .title("页面标题")
                //创建人
                .contact(new Contact("liuzq","","liuzq.com"))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }


    private List<SecurityScheme> securitySchemes() {

        List<SecurityScheme> securitySchemes = new ArrayList<>();

        securitySchemes.add(new ApiKey("jwt", "Authorization", "header"));

        //securitySchemes.add(new BasicAuth("basicAuth"));

        return securitySchemes;

    }
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts=new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences=new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }
    @ApiModel
    public static class Page {

        @ApiModelProperty(value = "第page页,从0开始计数", example = "0")
        private Integer page ;

        @ApiModelProperty(value = "每页数据数量", example = "10")
        private Integer size ;

        @ApiModelProperty("按这种格式写：size=100&page=3&sort=id,asc&sort=name,desc")
        private List<String> sort;

        public Integer getPage() {

            return page;
        }

        public void setPage(Integer page) {

            this.page = page;
        }

        public Integer getSize() {

            return size;
        }

        public void setSize(Integer size) {

            this.size = size;
        }

        public List<String> getSort() {

            return sort;
        }

        public void setSort(List<String> sort) {

            this.sort = sort;
        }
    }
}