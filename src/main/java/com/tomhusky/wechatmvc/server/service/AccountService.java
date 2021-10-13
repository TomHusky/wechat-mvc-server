package com.tomhusky.wechatmvc.server.service;

import com.tomhusky.wechatmvc.server.common.base.BaseService;
import com.tomhusky.wechatmvc.server.entity.Account;
import com.tomhusky.wechatmvc.server.vo.add.AddAccountVo;

/**
 * @author luowj
 * @className: AccountService
 * @date 2021/9/15 15:58
 * @version：1.0
 * @description: 账号服务接口
 */
public interface AccountService extends BaseService<Account> {

    /**
     * 添加账号
     *
     * @param addAccountVo: 账号信息
     * @return java.lang.Integer 返回当前用户的数量
     */
    Integer addAccount(AddAccountVo addAccountVo);

    /**
     * 后台用户修改密码
     *
     * @param username    账号
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return boolean
     */
    boolean editUserPassword(String username, String oldPassword, String newPassword);

}
