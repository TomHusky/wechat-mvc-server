package io.github.tomhusky.wechatmvc.server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.tomhusky.wechatmvc.server.common.base.AbstractEntity;
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
@TableName("sys_account")
public class Account extends AbstractEntity<Account> {

    private static final long serialVersionUID = 1L;

    @TableId
    private String username;

    /**
     * 用户id
     */
    private String password;

}
