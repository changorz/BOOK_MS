package com.swxy.jwbookms.handler;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

public class PageArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Page.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        MethodParameter nestedParameter = parameter.nestedIfOptional();
        int size = 0;
        int current = 0;
        try {
            size = Integer.parseInt(resolveName("size", nestedParameter, webRequest).toString());
            current = Integer.parseInt(resolveName("current", nestedParameter, webRequest).toString());
        } catch (Exception e) {
        } finally {
            if (size < 1) {
                size = 10;
            }
            if (size > 100) {
                size = 100;
            }
            if (current < 1) {
                current = 1;
            }
        }
        return new Page<>().setCurrent(current).setSize(size);
    }

    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        Map<String, String> uriTemplateVars = (Map<String, String>) request.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        return (uriTemplateVars != null ? uriTemplateVars.get(name) : null);
    }

}
