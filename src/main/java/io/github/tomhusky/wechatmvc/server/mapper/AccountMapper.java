package io.github.tomhusky.wechatmvc.server.mapper;

import io.github.tomhusky.wechatmvc.server.common.base.CommonMapper;
import io.github.tomhusky.wechatmvc.server.entity.Account;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 账号mapper
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/13 11:19
 */
@Mapper
public interface AccountMapper extends CommonMapper<Account> {

}
