package com.tomhusky.wechatmvc.server.entity;

import com.tomhusky.wechatmvc.server.common.base.AbstractEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 *
 * @author lwj
 * @date 2021-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class GroupChat extends AbstractEntity<GroupChat> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 群主id
     */
    private Integer ownerId;

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 群编号
     */
    private String groupNo;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
