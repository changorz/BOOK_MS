package com.swxy.jwbookms.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final RsaKeyProperties rsaKeyProperties;
    private final JWTAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private final JWTAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;
    private final DefaultLoginAccessDeniedHandler defaultLoginAccessDeniedHandler;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").access("hasRole('admin')")
                .antMatchers("/swagger-ui/**").permitAll()

                
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/druid/**").permitAll()
                .anyRequest().authenticated()   // 任何请求,登录后可以访问
                .and()
                .addFilterBefore(new JWTAuthorizationFilter(userDetailsService, authenticationManager(), rsaKeyProperties), LogoutFilter.class)
                .addFilterAt(this.userNamePwdAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // TODO authenticationEntryPoint 和 自定义登出操作
                // 权限不足 与 没有登录
                .exceptionHandling()
                .accessDeniedHandler(defaultAccessDeniedHandler)
                .authenticationEntryPoint(defaultLoginAccessDeniedHandler);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    public UsernamePasswordAuthenticationFilter userNamePwdAuthenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter pwdLoginFilter = new UsernamePasswordAuthenticationFilter(authenticationManager());
        pwdLoginFilter.setPasswordParameter("pwd");
        pwdLoginFilter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
        pwdLoginFilter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);
        return pwdLoginFilter;
    }

    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

}
