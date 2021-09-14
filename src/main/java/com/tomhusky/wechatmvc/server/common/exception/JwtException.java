package com.tomhusky.wechatmvc.server.common.exception;

import com.tomhusky.wechatmvc.server.common.exception.constant.ExceptionCode;
import org.springframework.security.core.AuthenticationException;

/**
 * @Author : lwj
 * @Date: 2020-09-03 14:19
 * @Description : jwt异常
 */
public class JwtException extends AuthenticationException {

    private Integer code = ExceptionCode.TOKEN.getCode();

    public JwtException() {
        super("认证操作异常");
    }

    public JwtException(String message) {
        super(message);
    }

    public JwtException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
