package com.tomhusky.wechatmvc.server.vo.add;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * <p>
 * 创建群聊
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/11 14:05
 */
@Data
public class CreateGroupChatVo {
    /**
     * 群名称
     */
    private String groupName;

    /**
     * 群成员信息
     */
    @Size(min = 1, message = "min size is 1")
    private List<UserDetail> userDetails;

    @Data
    public static class UserDetail {
        /**
         * 用户名
         */
        private String username;

        /**
         * wxid
         */
        private String wxid;
    }
}
