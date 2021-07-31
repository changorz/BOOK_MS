package com.swxy.jwbookms.config.security;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.code.CommonCode;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
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
public class JWTAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * UsernameNotFoundException（用户不存在：用户名）
     * DisabledException（用户已被禁用）
     * BadCredentialsException（坏的凭据：密码）
     * LockedException（账户锁定）
     * AccountExpiredException （账户过期）
     * CredentialsExpiredException（证书过期）
     *
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 返回401 表示未登录
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Response responseData = null;
        if (exception instanceof DisabledException) {
            responseData = new ResponseResult(CommonCode.Login_err_DisabledException);
        } else if (exception instanceof LockedException) {
            responseData = new ResponseResult(CommonCode.Login_err_LockedException);
        } else if (exception instanceof AccountExpiredException) {
            responseData = new ResponseResult(CommonCode.Login_err_AccountExpiredException);
        } else if (exception instanceof CredentialsExpiredException) {
            responseData = new ResponseResult(CommonCode.Login_err_CredentialsExpiredException);
        } else {
            responseData = new ResponseResult(CommonCode.Login_err_UsernameNotFoundException_BadCredentialsException);
        }
        ServletOutputStream out = response.getOutputStream();
        JSON.writeJSONString(out, responseData);
        out.flush();
        IoUtil.close(out);
    }


}
