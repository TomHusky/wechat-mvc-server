package com.tomhusky.wechatmvc.server.common.enums;

/**
 * <p>
 * 消息url
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/14 9:32
 */
public enum MsgUrlType {

    NEW_FRIEND_MSG("/friend/newFriend", "新好友消息"),
    ADD_FRIEND_MSG("/friend/addFriend", "添加好友消息"),
    CHAT_MSG("/chat/msg", "新好友消息");


    private final String url;

    private final String info;

    MsgUrlType(String url, String info) {
        this.url = url;
        this.info = info;
    }

    public String getUrl() {
        return url;
    }

    public String getInfo() {
        return info;
    }
}
