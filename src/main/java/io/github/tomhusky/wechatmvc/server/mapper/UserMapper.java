package io.github.tomhusky.wechatmvc.server.mapper;

import io.github.tomhusky.wechatmvc.server.common.base.CommonMapper;
import io.github.tomhusky.wechatmvc.server.entity.User;
import io.github.tomhusky.wechatmvc.server.vo.AccountInfo;
import io.github.tomhusky.wechatmvc.server.vo.query.SelectUserVo;

/**
 * @author luowj
 * @className: UserMapper
 * @date 2021/9/13 11:58
 * @version：1.0
 * @description: 用户mapper
 */
public interface UserMapper extends CommonMapper<User> {

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username: 用户名
     * @return com.tomhusky.wechatmvc.server.vo.AccountInfo
     */
    AccountInfo getAccountInfoByName(String username);

    /**
     * 查找用户，用于添加好友
     *
     * @param value 用户名或wxid
     * @return com.tomhusky.wechatmvc.server.vo.query.SelectUserVo
     */
    SelectUserVo selectUser(String value);
}