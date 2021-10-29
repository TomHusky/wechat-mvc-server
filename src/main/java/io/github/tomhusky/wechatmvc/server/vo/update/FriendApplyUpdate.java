package io.github.tomhusky.wechatmvc.server.vo.update;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 好友申请处理
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/14 11:29
 */
@Data
public class FriendApplyUpdate {

    @NotBlank(message = "申请id不能为空")
    private String applyId;

    /**
     * 审核状态 1 通过 2 拒绝
     */
    @NotNull
    private Integer status;

}
