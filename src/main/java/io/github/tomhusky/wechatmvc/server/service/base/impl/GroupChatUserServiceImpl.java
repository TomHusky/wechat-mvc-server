package io.github.tomhusky.wechatmvc.server.service.base.impl;

import io.github.tomhusky.wechatmvc.server.common.base.BaseServiceImpl;
import io.github.tomhusky.wechatmvc.server.common.exception.DateNoneException;
import io.github.tomhusky.wechatmvc.server.entity.GroupChatUser;
import io.github.tomhusky.wechatmvc.server.entity.User;
import io.github.tomhusky.wechatmvc.server.mapper.GroupChatUserMapper;
import io.github.tomhusky.wechatmvc.server.service.base.GroupChatUserService;
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
public class GroupChatUserServiceImpl extends BaseServiceImpl<GroupChatUserMapper, GroupChatUser> implements GroupChatUserService {

    @Override
    public boolean deleteUser(String groupNo, Integer userId) {
        GroupChatUser groupUser = getGroupUser(groupNo, userId);
        if (groupUser == null) {
            throw new DateNoneException("用户不在此群聊");
        }
        return this.removeById(groupUser);
    }

    @Override
    public GroupChatUser getGroupUser(String groupNo, Integer userId) {
        return this.lambdaQuery().eq(GroupChatUser::getGroupNo, groupNo)
                .eq(GroupChatUser::getUserId, userId)
                .last("limit 1").one();
    }

    @Override
    public List<User> listGroupChatUser(String groupNo) {
        return this.baseMapper.listGroupChatUser(groupNo);
    }

    @Override
    public List<GroupChatUser> listByGroupNo(String groupNo) {
        return this.lambdaQuery().eq(GroupChatUser::getGroupNo,groupNo).list();
    }


}
