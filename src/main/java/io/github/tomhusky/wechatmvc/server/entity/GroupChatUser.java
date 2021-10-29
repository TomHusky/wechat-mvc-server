package io.github.tomhusky.wechatmvc.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.tomhusky.wechatmvc.server.common.base.AbstractEntity;
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
public class GroupChatUser extends AbstractEntity<GroupChatUser> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类型 0 群主 1 普通 2管理员
     */
    private Integer type;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 群昵称
     */
    private String nickname;

    /**
     * 群聊编码
     */
    private String groupNo;

    /**
     * 免打扰
     */
    private Boolean notDisturb;


    /**
     * 群备注名
     */
    private String remark;
}
