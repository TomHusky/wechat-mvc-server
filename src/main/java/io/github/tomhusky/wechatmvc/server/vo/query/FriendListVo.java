package io.github.tomhusky.wechatmvc.server.vo.query;

import lombok.Data;

/**
 * <p>
 * 好友列表
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/9/23 17:31
 */
@Data
public class FriendListVo {
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
     * 邮箱
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

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
     * 备注
     */
    private String remark;

    /**
     * 地址
     */
    private String area;

    /**
     * 好友来源
     */
    private String origin;

    /**
     * 标签
     */
    private String label;

    /**
     * 免打扰
     */
    private Boolean notDisturb;

    /**
     * 首字母，用于好友排序
     */
    private Character initial;
}
