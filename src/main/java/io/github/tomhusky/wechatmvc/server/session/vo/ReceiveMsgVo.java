package io.github.tomhusky.wechatmvc.server.session.vo;

import lombok.Data;

/**
 * <p>
 * 接收消息的vo
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/9/27 17:32
 */
@Data
public class ReceiveMsgVo {

    /**
     * 发送消息的主体 群聊对应群编号 好友对应username  目前只有群聊
     */
    private String sendId;

    /**
     * 发送消息的用户 当消息内容为 好友时 改值和sendId一致
     */
    private String username;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 消息类型 1 好友 2 群聊
     */
    private Integer msgType;

    /**
     * 消息内容类型 1 文本和表情 2 图片
     */
    private Integer contentType;

    /**
     * 发送时间
     */
    private Long sendTime;

    /**
     * 消息id，前端生成唯一 采用 username+随机字符
     */
    private String msgId;

}
