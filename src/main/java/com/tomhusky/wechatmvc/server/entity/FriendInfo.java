package com.tomhusky.wechatmvc.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tomhusky.wechatmvc.server.common.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author lwj
 * @date 2021-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("sys_friend_info")
public class FriendInfo extends AbstractEntity<FriendInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 好友关系id
     */
    private Integer relatedId;

    /**
     * 备注
     */
    private Integer remark;

    /**
     * 好友来源
     */
    private Integer origin;

    /**
     * 标签
     */
    private Integer label;

    /**
     * 免打扰
     */
    private Integer notDisturb;
}
