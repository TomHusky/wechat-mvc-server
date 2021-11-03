package io.github.tomhusky.wechatmvc.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.tomhusky.wechatmvc.server.common.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author lwj
 * @date 2021-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
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
    private String remark;

    /**
     * 好友来源
     */
    private String origin;

    /**
     * 标签
     */
    private String label;

    /**
     * 免打扰
     */
    private Boolean notDisturb;

    /**
     * 首字母，用于好友排序
     */
    private String initial;
}
