package com.tomhusky.wechatmvc.server.vo.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <p>
 * 群聊列表VO
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/20 9:40
 */
@Data
public class GroupChatVo {

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
