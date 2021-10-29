package io.github.tomhusky.wechatmvc.server.common.exception;

import io.github.tomhusky.wechatmvc.server.common.exception.constant.ExceptionCode;

/**
 * @Author : lwj
 * @Date: 2020-09-03 14:19
 * @Description : 角色操作异常
 */
public class RoleException extends RuntimeException {

    private Integer code;

    public RoleException(){
        super("角色操作异常");
    }
    public RoleException(String message){
        super(message);
        this.code = ExceptionCode.OPERATE.getCode();
    }

    public RoleException(int code, String message){
        super(message);
        this.code = code;
    }
    public int getCode() {
        return this.code;
    }
}
