package com.tomhusky.wechatmvc.server.vo.add;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "账号不能为空")
    @Length(min = 5, max = 11, message = "用户名长度必须位5到11之间")
    private String username;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, message = "密码长度不能小于6位")
    private String password;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "请输入正确的邮箱")
    private String email;


    /**
     * 邮箱验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String code;
}
