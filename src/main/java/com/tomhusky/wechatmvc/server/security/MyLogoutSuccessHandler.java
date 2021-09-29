package com.tomhusky.wechatmvc.server.security;

import com.tomhusky.wechatmvc.server.common.JsonResult;
import com.tomhusky.wechatmvc.server.common.util.JSONUtils;
import com.tomhusky.wechatmvc.server.common.util.TokenBuild;
import com.tomhusky.wechatmvc.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>
 * 退出登录处理
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/9/26 9:49
 */
@Slf4j
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String tokenHeader = httpServletRequest.getHeader(TokenBuild.TOKEN_HEADER);
        String token = tokenHeader.replace(TokenBuild.TOKEN_PREFIX, "");
        try (PrintWriter out = httpServletResponse.getWriter()) {
            userService.removeToken(token);
            out.write(JSONUtils.toString(JsonResult.success("退出成功")));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
