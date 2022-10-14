package io.github.tomhusky.wechatmvc.server.vo.update;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 邀请用户到群聊
 * <p/>
 *
 * @author luowj
 * @version 1.0
 * @since 2022/10/12 17:45
 */
@Data
public class InviteUserVo {

    @NotBlank(message = "群编号不能为空")
    private String groupNo;

    @NotEmpty(message = "用户不能为空")
    private List<String> users;
}
