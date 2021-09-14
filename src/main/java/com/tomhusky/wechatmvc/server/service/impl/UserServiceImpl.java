package com.tomhusky.wechatmvc.server.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tomhusky.wechatmvc.server.common.base.BaseServiceImpl;
import com.tomhusky.wechatmvc.server.common.constant.RedisCacheName;
import com.tomhusky.wechatmvc.server.common.exception.BaseException;
import com.tomhusky.wechatmvc.server.common.exception.RoleException;
import com.tomhusky.wechatmvc.server.common.exception.constant.ExceptionCode;
import com.tomhusky.wechatmvc.server.common.util.RedisCache;
import com.tomhusky.wechatmvc.server.common.util.RedisStringCache;
import com.tomhusky.wechatmvc.server.common.util.TokenBuild;
import com.tomhusky.wechatmvc.server.entity.User;
import com.tomhusky.wechatmvc.server.mapper.UserMapper;
import com.tomhusky.wechatmvc.server.service.UserService;
import com.tomhusky.wechatmvc.server.vo.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 系統用戶表 服务实现类
 * </p>
 *
 * @author lwj
 * @since 2020-09-02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RedisCache<String, AccountInfo> redisCache;

    @Autowired
    private RedisStringCache tokenCache;

    @Override
    public boolean addUser(User user) {
        User userByName = getUserByName(user.getUsername());
        if (userByName != null) {
            throw new RoleException(ExceptionCode.INSERT.getCode(), "用户名已存在");
        }
        if (StrUtil.isBlank(user.getPassword())) {
            user.setPassword("123456");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return save(user);
    }

    @Override
    public boolean editUser(User user) {
        if (user == null) {
            return false;
        }
        User userById = getUserById(user.getId());
        if (userById == null) {
            throw new RoleException("用户不存在");
        }
        user.setPassword(null);
        user.setStatus(null);
        user.setUsername(null);
        boolean update = this.updateById(user);
        if (update) {
            redisCache.deleteObject(user.getUsername());
        }
        return update;
    }


    @Override
    public boolean deleteUser(Integer userId) {
        if (userId != null) {
            User user = this.getUserById(userId);
            if (user == null) {
                throw new BaseException(ExceptionCode.DELETE.getCode(), "用户不存在");
            }
            boolean remove = removeById(userId);
            if (remove) {
                redisCache.deleteObject(user.getUsername());
            }
            return remove;
        }
        return false;
    }


    @Override
    public boolean batchDelete(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BaseException(ExceptionCode.DELETE.getCode(), "id不能为空");
        }
        return removeByIds(ids);
    }

    @Override
    public User getUserById(Integer userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getId, userId);
        return this.getOne(queryWrapper);
    }

    @Override
    public User getUserByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUsername, username);
        return this.getOne(queryWrapper);
    }

    @Override
    public AccountInfo getAccountInfoByName(String username) {
        AccountInfo accountInfo = this.baseMapper.getAccountInfoByName(username);
        if (accountInfo != null) {
            redisCache.setCacheObject(username, accountInfo);
        }
        return accountInfo;
    }

    @Override
    public boolean editUserPassword(Integer userId, String oldPassword, String newPassword) {
        if (CharSequenceUtil.isBlank(oldPassword) || CharSequenceUtil.isBlank(newPassword)) {
            throw new RoleException(ExceptionCode.EDIT.getCode(), "密码不能为空");
        }
        User user = this.getUserById(userId);
        if (user == null) {
            throw new RoleException(ExceptionCode.EDIT.getCode(), "用户不存在");
        }
        boolean validPassword = validUserPassword(user.getId(), oldPassword);
        if (!validPassword) {
            throw new RoleException(ExceptionCode.EDIT.getCode(), "旧密码校验失败");
        }
        if (newPassword.length() < 6) {
            throw new RoleException(ExceptionCode.EDIT.getCode(), "密码长度要大于6位");
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        boolean update = this.updateById(user);
        if (update) {
            redisCache.deleteObject(user.getUsername());
        }
        return update;
    }

    @Override
    public boolean validUserPassword(Integer userId, String password) {
        User user = this.getUserById(userId);
        if (user == null) {
            throw new RoleException(ExceptionCode.DELETE.getCode(), "用户不存在");
        }
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

    @Override
    public boolean resetPassword(Integer userId) {
        User user = this.getUserById(userId);
        if (user == null) {
            throw new RoleException(ExceptionCode.DELETE.getCode(), "用户不存在");
        }
        user.setPassword(bCryptPasswordEncoder.encode("123456"));
        boolean update = this.updateById(user);
        if (update) {
            redisCache.deleteObject(user.getUsername());
        }
        return update;
    }

    @Override
    public String getToken(String tokenSign) {
        String cacheObject = tokenCache.getCacheObject(RedisCacheName.LOGIN_TOKEN + tokenSign);
        if (CharSequenceUtil.isNotEmpty(cacheObject)) {
            String refreshToken = TokenBuild.refresh(cacheObject);
            if (refreshToken != null) {
                tokenCache.setCacheObject(tokenSign, refreshToken, 1, TimeUnit.DAYS);
            }
        }
        return cacheObject;
    }

    @Override
    public String cacheToken(String token) {
        String md5 = MD5.create().digestHex(token);
        tokenCache.setCacheObject(RedisCacheName.LOGIN_TOKEN + md5, token, 1, TimeUnit.DAYS);
        return md5;
    }

}
