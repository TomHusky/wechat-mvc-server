package io.github.tomhusky.wechatmvc.server.session;

import io.github.tomhusky.websocket.context.SessionDetail;
import org.springframework.web.socket.WebSocketSession;

/**
 * <p>
 * 用户回话信息
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/9/28 14:26
 */
public class UserSessionDetail implements SessionDetail {

    private final WebSocketSession webSocketSession;

    private String username;

    public UserSessionDetail(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    @Override
    public WebSocketSession getWebSocketSession() {
        return this.webSocketSession;
    }

    public UserSessionDetail setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getUsername() {
        return username;
    }
}
