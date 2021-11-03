package io.github.tomhusky.wechatmvc.server.vo.update;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 更新好友视图
 *
 * @author luowj
 * @since 2021/9/15 16:05
 */
@Data
public class UpdateFriendVo {

    @NotBlank(message = "账号不能为空")
    private String username;

    /**
     * 备注
     */
    private String remark;

    /**
     * 免打扰
     */
    private Boolean notDisturb;
}
