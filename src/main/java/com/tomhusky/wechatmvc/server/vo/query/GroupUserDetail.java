package com.tomhusky.wechatmvc.server.vo.query;

import lombok.Data;

/**
 * <p>
 * 群成员用户
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/11 16:48
 */
@Data
public class GroupUserDetail {

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
}
