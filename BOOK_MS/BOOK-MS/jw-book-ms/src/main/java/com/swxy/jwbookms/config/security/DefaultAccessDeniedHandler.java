package com.swxy.jwbookms.config.security;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.code.CommonCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 用户没有权限访问 返回
     *
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     */

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        Response responseData = new ResponseResult(CommonCode.UNAUTHENTICATED);
        ServletOutputStream out = response.getOutputStream();
        JSON.writeJSONString(out, responseData);
        out.flush();
        IoUtil.close(out);
    }

}