package io.github.tomhusky.wechatmvc.server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.tomhusky.wechatmvc.server.common.util.TokenBuild;
import io.github.tomhusky.wechatmvc.server.common.JsonResult;
import io.github.tomhusky.wechatmvc.server.service.base.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author : lwj
 * @Date: 2020-09-04 15:43
 * @Description : 登录认证成功的处理
 */
@Slf4j
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication auth) {
        response.setContentType("application/json;charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            JwtUser jwtUser = (JwtUser) auth.getPrincipal();
            String token = TokenBuild.getToken(jwtUser.getUsername());
            String md5Token = userService.cacheToken(token);
            jwtUser.getUser().setToken(md5Token);
            out.write(mapper.writeValueAsString(JsonResult.success(jwtUser.getUser())));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}