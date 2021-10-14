package com.tomhusky.wechatmvc.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tomhusky.wechatmvc.server.common.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 好友申请
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/14 10:24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("friend_apply")
public class FriendApply extends AbstractEntity<Account> {

    @TableId(value = "apply_id", type = IdType.INPUT)
    private String applyId;

    /**
     * 申请者username
     */
    private String applyUser;

    /**
     * 接收者username
     */
    private String receiveUser;

    /**
     * 申请人给好友的备注
     */
    private String applyRemark;

    /**
     * 添加状态 0已发送 1 已添加 2 已拒绝
     */
    private String status;

    /**
     * 申请信息
     */
    private String applyInfo;

    /**
     * 好友来源
     */
    private String origin;
}
