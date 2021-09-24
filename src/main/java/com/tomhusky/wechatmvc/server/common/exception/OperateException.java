package com.tomhusky.wechatmvc.server.common.exception;

import com.tomhusky.wechatmvc.server.common.exception.constant.ExceptionCode;


/**
 * @author lwj
 */
public class OperateException extends BaseException {
    private static final Integer CODE = ExceptionCode.OPERATE.getCode();

    public OperateException() {
        super(CODE, ExceptionCode.OPERATE.getMsg());
    }

    public OperateException(String message) {
        super(CODE, message);
    }
}
