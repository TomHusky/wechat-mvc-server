package com.tomhusky.wechatmvc.server.security;

import com.tomhusky.wechatmvc.server.common.exception.AuthException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * <p>
 * Security帮助类
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/9/24 13:37
 */
public class SecurityUtils {

    private SecurityUtils() {

    }

    /**
     * 获取用户账户
     */
    public static String getUsername() {
        try {
            return getLoginUser().getUsername();
        } catch (Exception e) {
            throw new AuthException("获取用户账户异常");
        }
    }

    /**
     * 获取用户
     */
    public static JwtUser getLoginUser() {
        try {
            return (JwtUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new AuthException("获取用户信息异常");
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

