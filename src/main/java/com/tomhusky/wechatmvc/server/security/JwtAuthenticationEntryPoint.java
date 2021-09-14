package com.tomhusky.wechatmvc.server.security;

import com.tomhusky.wechatmvc.server.common.JsonResult;
import com.tomhusky.wechatmvc.server.common.exception.JwtException;
import com.tomhusky.wechatmvc.server.common.exception.constant.ExceptionCode;
import com.tomhusky.wechatmvc.server.common.util.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 401返回 token有误  统一处理 AuthenticationException 异常
 *
 * @author lwj
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (authException instanceof JwtException) {
                out.write(JSONUtils.toString(JsonResult.fail(((JwtException) authException).getCode(), authException.getMessage())));
            } else if (authException instanceof BadCredentialsException) {
                out.write(JSONUtils.toString(JsonResult.fail(ExceptionCode.UNAUTHORIZED.getCode(), authException.getMessage())));
            } else {
                out.write(JSONUtils.toString(JsonResult.fail(ExceptionCode.UNAUTHORIZED.getCode(),
                        ExceptionCode.UNAUTHORIZED.getMsg())));
            }
            out.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
