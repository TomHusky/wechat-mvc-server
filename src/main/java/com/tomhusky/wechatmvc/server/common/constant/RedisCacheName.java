package com.tomhusky.wechatmvc.server.common.constant;

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
     * 登录token缓存前缀
     */
    public static final String LOGIN_TOKEN = "user:token:";

    /**
     * 登录token缓存前缀
     */
    public static final String LOGIN_USER = "user:info:";
}
