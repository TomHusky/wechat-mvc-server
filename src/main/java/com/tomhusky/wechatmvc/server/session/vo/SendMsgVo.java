package com.tomhusky.wechatmvc.server.session.vo;

import lombok.Data;

/**
 * <p>
 * 发送消息的vo
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/9/27 17:32
 */
@Data
public class SendMsgVo {

    /**
     * 收消息的用户 群聊对应群编号  好友对应username
     */
    private String receiveId;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 消息类型 1 好友 2 群聊
     */
    private Integer msgType;

    /**
     * 发送时间
     */
    private Long sendTime;

    /**
     * 消息id，前端生成唯一 采用 username+随机字符
     */
    private String msgId;

}
