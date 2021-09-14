package com.tomhusky.wechatmvc.server.common.exception;

import com.tomhusky.wechatmvc.server.common.exception.constant.ExceptionCode;

/**
 * @Author: xbuding
 * @Date: 2017/8/28
 * @Description:
 * @Version: 1.0
 * @History:
 */
public class TokenException extends BaseException {

    private static final Integer CODE = ExceptionCode.TOKEN_ERROR.getCode();

    public TokenException(String message) {
        super(CODE, message);
    }

}
