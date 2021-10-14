package com.tomhusky.wechatmvc.server.security;

import com.tomhusky.wechatmvc.server.common.exception.AuthException;
import com.tomhusky.wechatmvc.server.vo.AccountInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

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
            Authentication authentication = getAuthentication();
            if (authentication.getPrincipal() instanceof JwtUser) {
                return (JwtUser) authentication.getPrincipal();
            }else {
                AccountInfo accountInfo = new AccountInfo();
                accountInfo.setUsername(authentication.getName());
                return new JwtUser(accountInfo);
            }
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

