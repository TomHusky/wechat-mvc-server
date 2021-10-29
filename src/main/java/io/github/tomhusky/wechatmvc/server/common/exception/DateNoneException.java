package io.github.tomhusky.wechatmvc.server.common.exception;

import io.github.tomhusky.wechatmvc.server.common.exception.constant.ExceptionCode;


/**
 * 数据不存在异常
 *
 * @author lwj
 * @since 2021-09-23
 */
public class DateNoneException extends BaseException {
    private static final Integer CODE = ExceptionCode.ARGUMENT.getCode();

    public DateNoneException() {
        super(CODE, ExceptionCode.ARGUMENT.getMsg());
    }

    public DateNoneException(String message) {
        super(CODE, message);
    }
}
