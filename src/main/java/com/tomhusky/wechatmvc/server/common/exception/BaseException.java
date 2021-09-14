package com.tomhusky.wechatmvc.server.common.exception;


import com.tomhusky.wechatmvc.server.common.exception.constant.ExceptionCode;

/**
 * @Author: xbuding
 * @Date: 2017/8/28
 * @Description: 基类异常
 * @Version: 1.0
 * @History:
 */
public class BaseException extends RuntimeException {
    private Integer code = ExceptionCode.OPERATE.getCode();

    public BaseException(String message) {
        super(message);
    }

    public BaseException(int code) {
        super("");
        this.code = code;
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
