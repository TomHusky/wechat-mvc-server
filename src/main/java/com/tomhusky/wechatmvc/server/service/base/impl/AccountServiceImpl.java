package com.tomhusky.wechatmvc.server.service.base.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.tomhusky.wechatmvc.server.common.base.BaseServiceImpl;
import com.tomhusky.wechatmvc.server.common.exception.OperateException;
import com.tomhusky.wechatmvc.server.common.exception.RoleException;
import com.tomhusky.wechatmvc.server.common.exception.constant.ExceptionCode;
import com.tomhusky.wechatmvc.server.entity.Account;
import com.tomhusky.wechatmvc.server.entity.User;
import com.tomhusky.wechatmvc.server.mapper.AccountMapper;
import com.tomhusky.wechatmvc.server.service.base.AccountService;
import com.tomhusky.wechatmvc.server.service.base.UserService;
import com.tomhusky.wechatmvc.server.vo.AccountInfo;
import com.tomhusky.wechatmvc.server.vo.add.AddAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author luowj
 * @className: AccountServiceImpl
 * @date 2021/9/15 15:58
 * @version：1.0
 * @description: 账号服务接口实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl extends BaseServiceImpl<AccountMapper, Account> implements AccountService {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Integer addAccount(AddAccountVo addAccountVo) {
        // 校验邮箱存在
        boolean emailExit = userService.emailExit(addAccountVo.getEmail());
        if (emailExit) {
            throw new OperateException("邮箱已经被绑定");
        }
        User userByName = userService.getUserByName(addAccountVo.getUsername());
        if (userByName != null) {
            throw new OperateException("账号不可用");
        }
        Account account = new Account();
        account.setUsername(addAccountVo.getUsername());
        account.setPassword(bCryptPasswordEncoder.encode(addAccountVo.getPassword()));
        boolean insert = this.save(account);
        if (insert) {
            User user = new User();
            user.setWxid(account.getUsername() + IdUtil.nanoId(4));
            BeanUtil.copyProperties(addAccountVo, user);
            userService.addUser(user);
        }
        return userService.count();
    }

    @Override
    public boolean editUserPassword(String username, String oldPassword, String newPassword) {
        if (CharSequenceUtil.isBlank(oldPassword) || CharSequenceUtil.isBlank(newPassword)) {
            throw new RoleException(ExceptionCode.EDIT.getCode(), "密码不能为空");
        }
        AccountInfo accountInfo = userService.getAccountInfoByName(username);
        if (accountInfo == null) {
            throw new RoleException(ExceptionCode.EDIT.getCode(), "用户不存在");
        }
        boolean validPassword = bCryptPasswordEncoder.matches(oldPassword, accountInfo.getPassword());
        if (!validPassword) {
            throw new RoleException(ExceptionCode.EDIT.getCode(), "旧密码校验失败");
        }
        if (newPassword.length() < 6) {
            throw new RoleException(ExceptionCode.EDIT.getCode(), "密码长度要大于6位");
        }
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(bCryptPasswordEncoder.encode(newPassword));
        return account.updateById();
    }

}
