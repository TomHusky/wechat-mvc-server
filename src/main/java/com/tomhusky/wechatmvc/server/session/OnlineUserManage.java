package com.tomhusky.wechatmvc.server.session;

import cn.hutool.core.util.StrUtil;
import io.github.tomhusky.websocket.SocketSessionManager;
import io.github.tomhusky.websocket.util.FastJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;

import java.util.Map;
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

    public static String get(String key) {
        return WEB_SOCKET_SESSION_MAP.get(key);
    }

    public static <T> boolean sendMessages(String username, T data) {
        String sendData = "";
        if (data != null) {
            try {
                sendData = FastJsonUtils.toString(data);
            } catch (Exception var6) {
                log.error("json序列化异常，{}", var6.getMessage());
                return false;
            }
        }
        String sessionId = WEB_SOCKET_SESSION_MAP.get(username);
        if (StrUtil.isNotBlank(sessionId)) {
            return SocketSessionManager.sendMessages(sessionId, new TextMessage(sendData));
        }
        return false;
    }
}
