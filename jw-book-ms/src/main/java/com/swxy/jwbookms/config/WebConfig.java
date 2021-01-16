package com.swxy.jwbookms.config;

import com.swxy.jwbookms.handler.PageArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // 校验对象Page,注入参数
        resolvers.add(new PageArgumentResolver());
    }


}
