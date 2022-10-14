package io.github.tomhusky.wechatmvc.server.vo.update;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 更新群聊信息
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/11 14:05
 */
@Data
public class UpdateGroupChatUserVo {

    /**
     * 群编号
     */
    @NotBlank(message = "群编号不能为空")
    private String groupNo;

    /**
     * 群里面的昵称
     */
    private String nickname;

    /**
     * 群备注名
     */
    private String remark;

    /**
     * 免打扰
     */
    private Boolean notDisturb;
}
