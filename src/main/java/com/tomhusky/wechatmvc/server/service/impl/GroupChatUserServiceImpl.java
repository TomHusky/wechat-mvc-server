package com.tomhusky.wechatmvc.server.service.impl;

import com.tomhusky.wechatmvc.server.entity.GroupChatUser;
import com.tomhusky.wechatmvc.server.mapper.GroupChatUserMapper;
import com.tomhusky.wechatmvc.server.service.GroupChatUserService;
import com.tomhusky.wechatmvc.server.common.base.BaseServiceImpl;
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
