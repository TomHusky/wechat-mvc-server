package io.github.tomhusky.wechatmvc.server.vo.add;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 添加好友视图
 *
 * @author luowj
 * @since 2021/9/15 16:05
 */
@Data
public class AddFriendVo {

    @NotBlank(message = "账号不能为空")
    private String username;

    /**
     * 验证消息
     */
    private String info;

    /**
     * 备注
     */
    private String remark;
}
