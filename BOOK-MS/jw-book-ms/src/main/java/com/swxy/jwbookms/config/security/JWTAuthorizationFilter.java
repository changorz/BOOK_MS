package com.swxy.jwbookms.config.security;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


/**
 * @author chang
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserDetailsService userDetailsService;
    private RsaKeyProperties prop;

    public JWTAuthorizationFilter(UserDetailsService userDetailsService, AuthenticationManager authenticationManager, RsaKeyProperties prop) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.prop = prop;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String tokenHeader = request.getHeader(JwtUtils.TOKEN_HEADER);
        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith(JwtUtils.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        super.doFilterInternal(request, response, chain);
    }

    /**
     * 这里从token中获取用户信息并新建一个token
     *
     * @param tokenHeader
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        String token = tokenHeader.replace(JwtUtils.TOKEN_PREFIX, "");
        Payload<Map> infoFromToken = JwtUtils.getInfoFromToken(token, prop.getPublicKey(), Map.class);
        String username = JSON.parseObject(infoFromToken.getUserInfo().toString()).get("username").toString();
        Collection<? extends GrantedAuthority> authorities = userDetailsService.loadUserByUsername(username).getAuthorities();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        return usernamePasswordAuthenticationToken;
    }
}
