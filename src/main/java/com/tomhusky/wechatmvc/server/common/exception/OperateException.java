package com.tomhusky.wechatmvc.server.common.exception;

import com.tomhusky.wechatmvc.server.common.exception.constant.ExceptionCode;

/**
 * @Author: caolei
 * @Despriction:
 * @Date:Created in 2019/4/8 14:52
 * @Modify By:
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
