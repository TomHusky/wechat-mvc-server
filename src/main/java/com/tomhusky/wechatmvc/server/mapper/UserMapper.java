package com.tomhusky.wechatmvc.server.mapper;

import com.tomhusky.wechatmvc.server.common.base.BaseDao;
import com.tomhusky.wechatmvc.server.entity.User;
import com.tomhusky.wechatmvc.server.vo.AccountInfo;

/**
 * @author luowj
 * @className: UserMapper
 * @date 2021/9/13 11:58
 * @version：1.0
 * @description: 用户mapper
 */
public interface UserMapper extends BaseDao<User> {

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username: 用户名
     * @return com.tomhusky.wechatmvc.server.vo.AccountInfo
     */
    AccountInfo getAccountInfoByName(String username);

}