package com.swxy.jwbookms.config.security;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.code.CommonCode;
import com.swxy.jwbookms.common.response.plus.DataResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 在 Security 中验证成功默认跳转到上一次请求页面或者路径为 "/" 的页面，
 * 我们同样可以自定义：继承 SimpleUrlAuthenticationSuccessHandler
 * 这个类或者实现 AuthenticationSuccessHandler 接口。我这里建议采用继承的方式,
 * SimpleUrlAuthenticationSuccessHandler 是默认的处理器，
 * 采用继承可以契合里氏替换原则，提高代码的复用性和避免不必要的错误。
 */


@Component
public class JWTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private RsaKeyProperties rsaKeyProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        Map<String, Object> jwtMap = new HashMap<>();
        jwtMap.put("username", username);
        String token = JwtUtils.generateTokenExpireInSeconds(jwtMap, rsaKeyProperties.getPrivateKey(), 2 * 60 * 60);
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Response responseData = new DataResponseResult(new HashMap<String, Object>() {{
            put("token", JwtUtils.TOKEN_PREFIX + token);
        }}, CommonCode.Login_ac);
        ServletOutputStream out = response.getOutputStream();
        JSON.writeJSONString(out, responseData);
        out.flush();
        IoUtil.close(out);
    }
}
