package io.github.tomhusky.wechatmvc.server.vo.query;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * 群聊列表
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/9/23 17:31
 */
@Data
public class GroupChatListVo {

    /**
     * 群主id
     */
    private String ownerId;

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 群编号
     */
    private String groupNo;

    /**
     * 群公告
     */
    private String notice;

    /**
     * 群成员信息
     */
    private List<GroupUserDetail> userDetails;

    /**
     * 群头像
     */
    private String groupAvatar;


    /**
     * 群备注名
     */
    private String remark;

    /**
     * 免打扰
     */
    private Boolean notDisturb;
}
