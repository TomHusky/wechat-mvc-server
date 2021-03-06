package io.github.tomhusky.wechatmvc.server.service.base.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.tomhusky.wechatmvc.server.common.base.BaseServiceImpl;
import io.github.tomhusky.wechatmvc.server.common.constant.RedisCacheName;
import io.github.tomhusky.wechatmvc.server.common.exception.BaseException;
import io.github.tomhusky.wechatmvc.server.common.exception.RoleException;
import io.github.tomhusky.wechatmvc.server.common.exception.constant.ExceptionCode;
import io.github.tomhusky.wechatmvc.server.common.util.RedisCache;
import io.github.tomhusky.wechatmvc.server.common.util.RedisStringCache;
import io.github.tomhusky.wechatmvc.server.common.util.TokenBuild;
import io.github.tomhusky.wechatmvc.server.entity.User;
import io.github.tomhusky.wechatmvc.server.mapper.UserMapper;
import io.github.tomhusky.wechatmvc.server.security.SecurityUtils;
import io.github.tomhusky.wechatmvc.server.service.base.UserService;
import io.github.tomhusky.wechatmvc.server.vo.AccountInfo;
import io.github.tomhusky.wechatmvc.server.vo.query.SelectUserVo;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RedisCache<String, AccountInfo> redisCache;

    @Autowired
    private RedisStringCache tokenCache;

    @Override
    public boolean addUser(User user) {
        User userByName = getUserByName(user.getUsername());
        if (userByName != null) {
            throw new RoleException(ExceptionCode.INSERT.getCode(), "用户名已存在");
        }
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
        user.setStatus(null);
        user.setUsername(null);
        boolean update = this.updateById(user);
        if (update) {
            redisCache.deleteObject(RedisCacheName.LOGIN_USER + user.getUsername());
        }
        return update;
    }

    @Override
    public boolean editUserByUsername(User user) {
        if (user == null) {
            return false;
        }
        user.setUsername(SecurityUtils.getUsername());
        User userByName = getUserByName(user.getUsername());
        if (userByName == null) {
            throw new RoleException("用户不存在");
        }
        user.setId(userByName.getId());
        user.setStatus(null);
        user.setUsername(null);
        boolean update = this.updateById(user);
        if (update) {
            redisCache.deleteObject(RedisCacheName.LOGIN_USER + user.getUsername());
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
                redisCache.deleteObject(RedisCacheName.LOGIN_USER + user.getUsername());
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
        return this.lambdaQuery().eq(User::getId, userId).last("limit 1").one();
    }

    @Override
    public User getByWxid(String wxid) {
        return this.lambdaQuery().eq(User::getWxid, wxid).last("limit 1").one();
    }

    @Override
    public User getUserByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUsername, username);
        return this.getOne(queryWrapper);
    }

    @Override
    public SelectUserVo selectUser(String value) {
        return this.baseMapper.selectUser(value);
    }

    @Override
    public boolean emailExit(String email) {
        return this.lambdaQuery().eq(User::getEmail, email).one() != null;
    }

    @Override
    public AccountInfo getAccountInfoByName(String username) {
        AccountInfo cacheObject = redisCache.getCacheObject(RedisCacheName.LOGIN_USER + username);
        if (cacheObject != null) {
            return cacheObject;
        }
        AccountInfo accountInfo = this.baseMapper.getAccountInfoByName(username);
        if (accountInfo != null) {
            redisCache.setCacheObject(RedisCacheName.LOGIN_USER + username, accountInfo);
        }
        return accountInfo;
    }


    @Override
    public String getToken(String tokenSign) {
        String cacheObject = tokenCache.getCacheObject(RedisCacheName.LOGIN_TOKEN + tokenSign);
        if (CharSequenceUtil.isNotEmpty(cacheObject)) {
            String refreshToken = TokenBuild.refresh(cacheObject);
            if (refreshToken != null) {
                tokenCache.setCacheObject(RedisCacheName.LOGIN_TOKEN + tokenSign, refreshToken, 1, TimeUnit.HOURS);
            }
        }
        return cacheObject;
    }

    @Override
    public String cacheToken(String token) {
        String md5 = MD5.create().digestHex(token);
        tokenCache.setCacheObject(RedisCacheName.LOGIN_TOKEN + md5, token, 1, TimeUnit.HOURS);
        return md5;
    }

    @Override
    public void removeToken(String tokenSign) {
        tokenCache.deleteObject(RedisCacheName.LOGIN_TOKEN + tokenSign);
    }

}
