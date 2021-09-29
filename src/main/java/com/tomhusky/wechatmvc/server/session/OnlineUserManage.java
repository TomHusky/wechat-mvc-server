package com.tomhusky.wechatmvc.server.session;

import cn.hutool.core.util.StrUtil;
import io.github.tomhusky.websocket.SocketSessionManager;
import io.github.tomhusky.websocket.bean.SocketResult;
import io.github.tomhusky.websocket.util.FastJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
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
@Slf4j
public class OnlineUserManage {

    private static final Map<String, String> WEB_SOCKET_SESSION_MAP = new ConcurrentHashMap<>();

    private OnlineUserManage() {
    }

    public static synchronized void add(String username, String sessionId) {
        WEB_SOCKET_SESSION_MAP.computeIfAbsent(username, k -> sessionId);
    }

    public static String remove(String key) {
        String sessionId = WEB_SOCKET_SESSION_MAP.get(key);
        WEB_SOCKET_SESSION_MAP.remove(key);
        return sessionId;
    }

    public static synchronized void removeAllSessionId(String sessionId) {
        Collection<String> col = WEB_SOCKET_SESSION_MAP.values();
        while (col.contains(sessionId)) {
            col.remove(sessionId);
        }
    }

    public static String get(String key) {
        return WEB_SOCKET_SESSION_MAP.get(key);
    }

    public static String getKey(String value) {
        Set<Map.Entry<String, String>> entries = WEB_SOCKET_SESSION_MAP.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static <T> boolean sendMessages(String username, T data) {
        String sessionId = WEB_SOCKET_SESSION_MAP.get(username);
        if (StrUtil.isNotBlank(sessionId)) {
            return SocketSessionManager.sendMessages(sessionId, SocketResult.build(data,"/msg/receive"));
        }
        return false;
    }
}
