package com.tomhusky.wechatmvc.server.service.impl;

import com.tomhusky.wechatmvc.server.common.base.BaseServiceImpl;
import com.tomhusky.wechatmvc.server.common.exception.DateNoneException;
import com.tomhusky.wechatmvc.server.entity.User;
import com.tomhusky.wechatmvc.server.entity.UserRelation;
import com.tomhusky.wechatmvc.server.mapper.UserRelationMapper;
import com.tomhusky.wechatmvc.server.service.UserRelationService;
import com.tomhusky.wechatmvc.server.service.UserService;
import com.tomhusky.wechatmvc.server.vo.query.FriendListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务实现类
 *
 * @author lwj
 * @date 2021-09-15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRelationServiceImpl extends BaseServiceImpl<UserRelationMapper, UserRelation> implements UserRelationService {

    @Autowired
    private UserService userService;

    @Override
    public List<FriendListVo> listAllFriendInfo(String username) {
        User user = userService.getUserByName(username);
        if (user == null) {
            throw new DateNoneException("用户不存在");
        }
        return mapper.listAllFriendInfo(user.getId());
    }
}
