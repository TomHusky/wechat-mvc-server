package com.tomhusky.wechatmvc.server.session.handler;

import cn.hutool.core.text.CharSequenceUtil;
import com.tomhusky.wechatmvc.server.common.exception.JwtException;
import com.tomhusky.wechatmvc.server.security.JwtAuthenticationFilter;
import com.tomhusky.wechatmvc.server.service.UserService;
import io.github.tomhusky.websocket.interceptor.LoginValidIntercept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;

/**
 * @author luowj
 * @className: LoginValidIntercept
 * @date 2021/8/27 15:01
 * @version：1.0
 * @description: 登陆校验接口
 */
@Slf4j
@Component
public class TokenValidIntercept extends LoginValidIntercept {

    public static final String TOKEN_HEAD = "Authorization";

    @Autowired
    private UserService userService;

    @Override
    public boolean attemptAuthentication(ServerHttpRequest request, ServerHttpResponse response) {
        String token = request.getHeaders().getFirst(TOKEN_HEAD);
        if (token == null || CharSequenceUtil.isBlank(token)) {
            return false;
        }
        try {
            String redisToken = userService.getToken(token);
            return redisToken != null;
        } catch (JwtException e) {
            log.error(e.getMessage(), JwtAuthenticationFilter.class);
        }
        return false;
    }

    @Override
    public void successfulAuthentication(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.info("握手成功");
    }
}
