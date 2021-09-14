package com.tomhusky.wechatmvc.server.common.exception;

import com.tomhusky.wechatmvc.server.common.exception.constant.ExceptionCode;

/**
 * @author luowj
 */
public class UnauthorizedException extends BaseException {

    private static final Integer CODE = ExceptionCode.UNAUTHORIZED.getCode();

    public UnauthorizedException(String message) {
        super(CODE, message);
    }

}
