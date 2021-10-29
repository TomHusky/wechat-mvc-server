package io.github.tomhusky.wechatmvc.server.common.exception;

import io.github.tomhusky.wechatmvc.server.common.exception.constant.ExceptionCode;

/**
 * 分布式锁异常
 * @author caolei
 */
public class LockException extends BaseException {

    private static final Integer CODE = ExceptionCode.LOCK.getCode();

    public LockException(String message) {
        super(CODE, message);
    }
}
