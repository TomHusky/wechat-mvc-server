package com.tomhusky.wechatmvc.server.service.impl;

import com.tomhusky.wechatmvc.server.common.base.BaseServiceImpl;
import com.tomhusky.wechatmvc.server.entity.GroupChat;
import com.tomhusky.wechatmvc.server.mapper.GroupChatMapper;
import com.tomhusky.wechatmvc.server.service.GroupChatService;
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
public class GroupChatServiceImpl extends BaseServiceImpl<GroupChatMapper, GroupChat> implements GroupChatService {

}
