package io.github.tomhusky.wechatmvc.server.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.RegisteredPayload;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

/**
 * @author luowj
 * @className: TokenBuild
 * @date 2021/9/13 11:37
 * @version：1.0
 * @description:
 */
@Slf4j
public class TokenBuild {

    private static final String KEY = "Tomhusky.io";

    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    private static final Long EXP = 60 * 60 * 24L;

    /**
     * 生成token的过期时间
     */
    private static Date generateExpDate() {
        return new Date(System.currentTimeMillis() + EXP * 1000);
    }

    /**
     * 判断token是否已经失效
     */
    public static boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 判断token是否已经失效
     */
    private static boolean isTokenExpired(JWT jwt) {
        Date expiredDate = getExpiredDateFromToken(jwt);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private static Date getExpiredDateFromToken(String token) {
        JSONObject payloads = getPayloads(token);
        return new Date(Long.parseLong(payloads.get(RegisteredPayload.EXPIRES_AT).toString()));
    }

    /**
     * 从token中获取过期时间
     */
    private static Date getExpiredDateFromToken(JWT jwt) {
        JSONObject payloads = jwt.getPayloads();
        return new Date(Long.parseLong(payloads.get(RegisteredPayload.EXPIRES_AT).toString()));
    }

    /**
     * token过期时间刷新
     */
    public static String refresh(String token) {
        try {
            JWT jwt = getJwt(token);
            if (jwt.verify() && isTokenExpired(jwt)) {
                jwt.setExpiresAt(generateExpDate());
                return jwt.sign();
            }
        } catch (JWTException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String getToken(String username) {
        JWT jwt = JWT.create().setPayload("username", username)
                .setExpiresAt(generateExpDate())
                .setKey(KEY.getBytes());
        return jwt.sign();
    }

    public static String getToken(String username, Map<String, Object> expandPayload) {
        JWT jwt = JWT.create().setPayload("username", username)
                .setKey(KEY.getBytes());
        if (CollUtil.isNotEmpty(expandPayload)) {
            for (Map.Entry<String, Object> entry : expandPayload.entrySet()) {
                jwt.setPayload(entry.getKey(), entry.getValue());
            }
        }
        return jwt.sign();
    }

    public static JSONObject getPayloads(String rightToken) {
        // 默认验证HS265的算法
        JWT jwt = getJwt(rightToken);
        return jwt.getPayloads();
    }

    public static JWT getJwt(String rightToken) {
        return JWT.of(rightToken).setKey(KEY.getBytes());
    }

    public static Object getPayloads(String rightToken, String name) {
        // 默认验证HS265的算法
        JWT jwt = getJwt(rightToken);
        return jwt.getPayload(name);
    }

    public static boolean verify(String rightToken) {
        // 默认验证HS265的算法
        JWT jwt = getJwt(rightToken);
        return jwt.verify();
    }
}
