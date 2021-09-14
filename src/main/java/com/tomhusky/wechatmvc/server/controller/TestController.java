package com.tomhusky.wechatmvc.server.controller;

import io.github.tomhusky.websocket.annotation.SocketController;
import io.github.tomhusky.websocket.annotation.SocketRequestMapping;

/**
 * @author luowj
 * @className: TestController
 * @date 2021/9/13 15:06
 * @version：1.0
 * @description: 测试
 */
@SocketController
@SocketRequestMapping("/test")
public class TestController {

    @SocketRequestMapping("/ping")
    public String test() {
        return "ok";
    }

}