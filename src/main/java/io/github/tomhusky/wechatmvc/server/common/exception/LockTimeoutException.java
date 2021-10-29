package io.github.tomhusky.wechatmvc.server.common.exception;


import io.github.tomhusky.wechatmvc.server.common.exception.constant.ExceptionCode;

/**
 * @Author: caolei
 * @Despriction:
 * @Date:Created in 2019/4/8 14:52
 * @Modify By:
 */
public class LockTimeoutException extends BaseException {
    private static final Integer CODE = ExceptionCode.ARGUMENT.getCode();

    public LockTimeoutException() {
        super(CODE, ExceptionCode.LOCK_TIMEOUT.getMsg());
    }

    public LockTimeoutException(String message) {
        super(CODE, message);
    }

}
