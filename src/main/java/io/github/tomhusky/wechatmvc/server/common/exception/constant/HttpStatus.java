package io.github.tomhusky.wechatmvc.server.common.exception.constant;

/**
 * <p>
 * http状态
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/9/26 17:21
 */
public enum HttpStatus {

    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "访问受限，授权过期"),
    NOT_FOUND(404, "* 资源，服务未找到");

    private final Integer code;
    private final String msg;

    HttpStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
