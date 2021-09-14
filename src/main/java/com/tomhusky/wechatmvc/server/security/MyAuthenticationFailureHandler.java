package com.tomhusky.wechatmvc.server.security;

import com.tomhusky.wechatmvc.server.common.JsonResult;
import com.tomhusky.wechatmvc.server.common.exception.JwtException;
import com.tomhusky.wechatmvc.server.common.util.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author : lwj
 * @Date: 2020-09-04 15:38
 * @Description : 登录认证验证失败的处理
 */
@Slf4j
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            JsonResult<String> ajaxResult;
            if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
                ajaxResult = JsonResult.fail("账户名或者密码输入错误!");
            } else if (exception instanceof LockedException) {
                ajaxResult = JsonResult.fail("账户被锁定，请联系管理员!");
            } else if (exception instanceof CredentialsExpiredException) {
                ajaxResult = JsonResult.fail("密码过期，请联系管理员!");
            } else if (exception instanceof AccountExpiredException) {
                ajaxResult = JsonResult.fail("账户过期，请联系管理员!");
            } else if (exception instanceof DisabledException) {
                ajaxResult = JsonResult.fail("账户被禁用，请联系管理员!");
            } else if (exception instanceof JwtException) {
                ajaxResult = JsonResult.fail(exception.getMessage());
            } else {
                ajaxResult = JsonResult.fail("登录失败!");
            }
            out.write(JSONUtils.toString(ajaxResult));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}