package com.swxy.jwbookms.config.security;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.code.CommonCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chang
 */
@Component
public class DefaultLoginAccessDeniedHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Response responseData = new ResponseResult(CommonCode.Login_NO);
        ServletOutputStream out = response.getOutputStream();
        JSON.writeJSONString(out, responseData);
        out.flush();
        IoUtil.close(out);
    }
}