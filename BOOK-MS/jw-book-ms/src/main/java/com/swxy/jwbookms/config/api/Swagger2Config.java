package com.swxy.jwbookms.config.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("dev")
@EnableOpenApi
public class Swagger2Config {
    /**
     * 通过 createRestApi函数来构建一个DocketBean
     * 函数名,可以随意命名,喜欢什么命名就什么命名
     */
    @Bean
    public Docket createRestApi1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo1())
                .enable(true)
                .groupName("BOOK_MS API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.swxy.jwbookms.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    //构建 api文档的详细信息函数
    private ApiInfo apiInfo1() {
        return new ApiInfoBuilder()
                //页面标题
                .title("毕设—BOOK_MS API")
                .version("1.0")
                //简介
                .description("API接口描述")
                .build();
    }


    private static List<Parameter> defaultHeader() {
        ParameterBuilder appType = new ParameterBuilder();
        appType.name("app-type").description("应用类型").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        ParameterBuilder appToken = new ParameterBuilder();
        appToken.name("app-token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> pars = new ArrayList<>();
        pars.add(appType.build());
        pars.add(appToken.build());
        return pars;
    }

    // 全站统一header设置  https://www.jianshu.com/p/3122977af5a4
    private static List<ApiKey> unifiedAuth() {
        List<ApiKey> arrayList = new ArrayList();
        arrayList.add(new ApiKey("Authorization", "Authorization", "header"));
        return arrayList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    private static List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

}