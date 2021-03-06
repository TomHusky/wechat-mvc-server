package io.github.tomhusky.wechatmvc.server.service.base;


import io.github.tomhusky.wechatmvc.server.common.base.BaseService;
import io.github.tomhusky.wechatmvc.server.entity.User;
import io.github.tomhusky.wechatmvc.server.vo.AccountInfo;
import io.github.tomhusky.wechatmvc.server.vo.query.SelectUserVo;

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
     * 修改用户
     *
     * @param user 用户对象
     * @return boolean
     */
    boolean editUserByUsername(User user);

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
     * 根据wxid获取用户
     *
     * @param wxid wxid
     * @return com.tomhusky.wechatmvc.server.entity.User
     */
    User getByWxid(String wxid);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return cn.greenbon.api.business.system.bean.User
     */
    User getUserByName(String username);

    /**
     * 查找用户，用于添加好友
     *
     * @param value 用户名或wxid
     * @return com.tomhusky.wechatmvc.server.vo.query.SelectUserVo
     */
    SelectUserVo selectUser(String value);

    /**
     * 判断电子邮箱是否已经存在
     *
     * @param email 邮箱地址
     * @return boolean
     */
    boolean emailExit(String email);

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username: 用户名
     * @return com.tomhusky.wechatmvc.server.vo.AccountInfo
     */
    AccountInfo getAccountInfoByName(String username);

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

    /**
     * 退出登录移除token
     *
     * @param tokenSign md5之后的token
     */
    void removeToken(String tokenSign);
}
