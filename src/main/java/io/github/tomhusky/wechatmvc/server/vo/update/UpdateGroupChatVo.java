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
public class UpdateGroupChatVo {

    /**
     * 群编号
     */
    @NotBlank(message = "群编号不能为空")
    private String groupNo;

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 群公告
     */
    private String notice;
}
