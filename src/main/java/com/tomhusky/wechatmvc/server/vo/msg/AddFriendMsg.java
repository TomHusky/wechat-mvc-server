package com.tomhusky.wechatmvc.server.vo.msg;

import lombok.Data;

/**
 * <p>
 * 添加好友消息
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/14 9:57
 */
@Data
public class AddFriendMsg {

    /**
     * 申请id
     */
    private String applyId;

    /**
     * 用户名
     */
    private String username;

    /**
     * wxid
     */
    private String wxid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 地址
     */
    private String area;

    /**
     * 验证消息
     */
    private String info;

    /**
     * 添加状态 0已发送 1 已添加 2 已拒绝
     */
    private Integer status;
}
