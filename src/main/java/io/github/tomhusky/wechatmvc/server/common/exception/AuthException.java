package io.github.tomhusky.wechatmvc.server.common.exception;

import io.github.tomhusky.wechatmvc.server.common.exception.constant.ExceptionCode;

/**
 * @Author: xbuding
 * @Date: 2017/8/28
 * @Description:
 * @Version: 1.0
 * @History:
 */
public class AuthException extends BaseException {

    public AuthException() {
        super(ExceptionCode.UNAUTHORIZED.getCode(), ExceptionCode.UNAUTHORIZED.getMsg());
    }

    public AuthException(String message) {
        super(ExceptionCode.UNAUTHORIZED.getCode(), message);
    }

}
