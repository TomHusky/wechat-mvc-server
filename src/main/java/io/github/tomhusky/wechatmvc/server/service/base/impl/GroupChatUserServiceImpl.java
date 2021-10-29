package io.github.tomhusky.wechatmvc.server.service.base.impl;

import io.github.tomhusky.wechatmvc.server.common.base.BaseServiceImpl;
import io.github.tomhusky.wechatmvc.server.entity.GroupChatUser;
import io.github.tomhusky.wechatmvc.server.mapper.GroupChatUserMapper;
import io.github.tomhusky.wechatmvc.server.service.base.GroupChatUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  服务实现类
 *
 * @author lwj
 * @date 2021-09-15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupChatUserServiceImpl extends BaseServiceImpl<GroupChatUserMapper, GroupChatUser> implements GroupChatUserService {

}
