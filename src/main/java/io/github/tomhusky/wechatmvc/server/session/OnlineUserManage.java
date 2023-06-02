package io.github.tomhusky.wechatmvc.server.session;

import cn.hutool.core.text.CharSequenceUtil;
import io.github.tomhusky.websocket.SocketSendMsgManager;
import io.github.tomhusky.websocket.bean.SocketResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 在线用户管理
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/9/24 11:44
 */
@Component
@Slf4j
public class OnlineUserManage {

    private final StringRedisTemplate redisTemplate;
    private static final ConcurrentHashMap<String, String> WEB_SOCKET_SESSION_MAP = new ConcurrentHashMap<>();

    private static final String KEY_PREFIX = "online:";

    public OnlineUserManage(StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = stringRedisTemplate;
    }

    public synchronized void add(String username, String sessionId) {
        redisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + username, sessionId);
        WEB_SOCKET_SESSION_MAP.put(sessionId, username);
    }

    public String remove(String username) {
        String sessionId = redisTemplate.opsForValue().get(KEY_PREFIX + username);
        if (sessionId != null) {
            redisTemplate.delete(KEY_PREFIX + username);
            WEB_SOCKET_SESSION_MAP.remove(sessionId);
        }
        return sessionId;
    }

    public synchronized void removeAllSessionId(String sessionId) {
        String username = WEB_SOCKET_SESSION_MAP.get(sessionId);
        if (username != null) {
            redisTemplate.delete(KEY_PREFIX + username);
        }
    }

    public String getKey(String sessionId) {
        return WEB_SOCKET_SESSION_MAP.get(sessionId);
    }

    public String get(String username) {
        return redisTemplate.opsForValue().get(KEY_PREFIX + username);
    }

    public Boolean isOnline(String username) {
        return redisTemplate.hasKey(KEY_PREFIX + username);
    }


    public <T> void sendMessages(String address, String username, T data) {
        String sessionId = this.get(username);
        if (CharSequenceUtil.isNotBlank(sessionId)) {
            SocketSendMsgManager.sendMessages(sessionId, SocketResult.build(data, address));
        }
    }
}
