package com.tomhusky.wechatmvc.server.handler;

import io.github.tomhusky.websocket.CustomerWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author luowj
 * @className: WebSocketMsgHandler
 * @date 2021/8/25 9:19
 * @version：1.0
 * @description: websocket自定义消息处理
 */
@Slf4j
@Component
public class WebSocketMsgHandler implements CustomerWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        log.info("------------连接成功{}-------------", webSocketSession.getId());
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, TextMessage textMessage) {
        log.info(textMessage.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        log.info("------------连接关闭{}-------------", webSocketSession.getId());
    }
}
