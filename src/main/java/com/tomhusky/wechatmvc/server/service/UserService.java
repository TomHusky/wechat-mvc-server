package com.tomhusky.wechatmvc.server.service;


import com.tomhusky.wechatmvc.server.common.base.BaseService;
import com.tomhusky.wechatmvc.server.entity.User;
import com.tomhusky.wechatmvc.server.vo.AccountInfo;

import java.util.List;

/**
 * <p>
 * 系統用戶表 服务类
 * </p>
 *
 * @author lwj
 * @since 2020-09-02
 */
public interface UserService extends BaseService<User> {
    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return boolean
     */
    boolean addUser(User user);

    /**
     * 修改用户
     *
     * @param user 用户对象
     * @return boolean
     */
    boolean editUser(User user);

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return boolean
     */
    boolean deleteUser(Integer userId);


    /**
     * 批量删除用户
     *
     * @param ids 用户id集合
     * @return boolean
     */
    boolean batchDelete(List<Integer> ids);

    /**
     * 根据用户id获取用户
     *
     * @param userId 用户id
     * @return cn.greenbon.api.business.system.bean.User
     */
    User getUserById(Integer userId);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return cn.greenbon.api.business.system.bean.User
     */
    User getUserByName(String username);

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username: 用户名
     * @return com.tomhusky.wechatmvc.server.vo.AccountInfo
     */
    AccountInfo getAccountInfoByName(String username);

    /**
     * 后台用户修改密码
     *
     * @param userId      用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return boolean
     */
    boolean editUserPassword(Integer userId, String oldPassword, String newPassword);

    /**
     * 验证用户密码是否正常
     *
     * @param userId   用户id
     * @param password 密码
     * @return boolean
     */
    boolean validUserPassword(Integer userId, String password);

    /**
     * 重置密码
     *
     * @param userId 目标用户id
     * @return boolean
     */
    boolean resetPassword(Integer userId);

    /**
     * 获取redis中token
     *
     * @param tokenSign: md5之后的token
     * @return java.lang.String
     */
    String getToken(String tokenSign);

    /**
     * 保存token
     *
     * @param token: token源字符串
     * @return java.lang.String
     */
    String cacheToken(String token);
}
