package io.github.tomhusky.wechatmvc.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.tomhusky.wechatmvc.server.common.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lwj
 * @date 2021-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class GroupChat extends AbstractEntity<GroupChat> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
