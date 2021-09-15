package com.tomhusky.wechatmvc.server.vo.add;

import lombok.Data;

/**
 * @author luowj
 * @className: AddAccountVo
 * @date 2021/9/15 16:05
 * @version：1.0
 * @description: 新增账号视图
 */
@Data
public class AddAccountVo {
    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

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
}
