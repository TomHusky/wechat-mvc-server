package io.github.tomhusky.wechatmvc.server.common.constant;

/**
 * @author luowj
 * @className: RedisCacheName
 * @date 2021/9/13 17:48
 * @version：1.0
 * @description: redis缓存key
 */
public class RedisCacheName {

    private RedisCacheName() {

    }

    /**
     * 登录token缓存
     */
    public static final String LOGIN_TOKEN = "user:token:";

    /**
     * 登录用户信息缓存
     */
    public static final String LOGIN_USER = "user:info:";

    /**
     * 邮件验证码缓存
     */
    public static final String EMAIL_CODE = "code:ver:";

    /**
     * 好友申请缓存
     */
    public static final String FRIEND_APPLY = "friend:apply:";
}
