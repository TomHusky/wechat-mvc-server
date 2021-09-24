package com.tomhusky.wechatmvc.server.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * @author luowj
 * @className: AccountInfo
 * @date 2021/9/13 12:03
 * @version：1.0
 * @description: 账号信息
 */
@Data
public class AccountInfo implements Serializable {

    private String token;

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
    @JsonIgnore
    @JSONField(serialize = false)
    private String password;

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
     * 状态
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private Integer status;

    /**
     * 性别
     */
    private Integer sex;

    private String signature;
}
