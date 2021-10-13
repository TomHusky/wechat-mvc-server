package com.tomhusky.wechatmvc.server.mapper;

import com.tomhusky.wechatmvc.server.common.base.CommonMapper;
import com.tomhusky.wechatmvc.server.entity.Account;
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
