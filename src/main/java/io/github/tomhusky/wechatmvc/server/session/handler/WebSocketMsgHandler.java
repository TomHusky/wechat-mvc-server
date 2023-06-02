package io.github.tomhusky.wechatmvc.server.session.handler;

import io.github.tomhusky.websocket.CustomerWebSocketHandler;
import io.github.tomhusky.websocket.context.WebSocketContext;
import io.github.tomhusky.websocket.context.WebSocketContextHolder;
import io.github.tomhusky.wechatmvc.server.common.util.TokenBuild;
import io.github.tomhusky.wechatmvc.server.service.base.UserService;
import io.github.tomhusky.wechatmvc.server.service.session.OnlineService;
import io.github.tomhusky.wechatmvc.server.session.OnlineUserManage;
import io.github.tomhusky.wechatmvc.server.session.UserSessionDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author luowj
 * @date 2021/8/25 9:19
 * websocket自定义消息处理
 */
@Slf4j
@Component
public class WebSocketMsgHandler implements CustomerWebSocketHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private OnlineService onlineService;

    @Autowired
    private OnlineUserManage onlineUserManage;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        log.info("------------连接成功:{}-------------", webSocketSession.getId());
        String token = webSocketSession.getHandshakeHeaders().getFirst(TokenValidIntercept.TOKEN_HEAD);

        String redisToken = userService.getToken(token);
        String username = TokenBuild.getPayloads(redisToken).get("username").toString();

        onlineUserManage.add(username, webSocketSession.getId());

        // 上线之后任务处理
        onlineService.startTask(username);

        log.info("username:{}", username);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, TextMessage textMessage) {

        String username = onlineUserManage.getKey(webSocketSession.getId());
        if (username == null) {
            String token = webSocketSession.getHandshakeHeaders().getFirst(TokenValidIntercept.TOKEN_HEAD);
            String redisToken = userService.getToken(token);
            username = TokenBuild.getPayloads(redisToken).get("username").toString();
        }
        UserSessionDetail userSessionDetail = new UserSessionDetail(webSocketSession).setUsername(username);
        WebSocketContextHolder.setContext(new WebSocketContext(userSessionDetail));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        log.info("------------连接关闭{}-------------", webSocketSession.getId());
        onlineUserManage.removeAllSessionId(webSocketSession.getId());
    }
}
