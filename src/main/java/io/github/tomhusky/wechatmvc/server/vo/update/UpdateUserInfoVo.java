package io.github.tomhusky.wechatmvc.server.vo.update;

import lombok.Data;

/**
 * 更新自己的信息视图
 *
 * @author luowj
 * @since 2021/9/15 16:05
 */
@Data
public class UpdateUserInfoVo {

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
    private String mobile;

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
