package com.tomhusky.wechatmvc.server.service;

import com.tomhusky.wechatmvc.server.entity.GroupChat;
import com.tomhusky.wechatmvc.server.common.base.BaseService;
import com.tomhusky.wechatmvc.server.vo.add.CreateGroupChatVo;
import com.tomhusky.wechatmvc.server.vo.query.GroupChatListVo;

import java.util.List;

/**
 * 服务类
 *
 * @author lwj
 * @date 2021-09-15
 */
public interface GroupChatService extends BaseService<GroupChat> {

    /**
     * 创建群聊
     *
     * @param groupChatVo 群聊信息
     * @return com.tomhusky.wechatmvc.server.vo.query.GroupChatListVo
     */
    GroupChatListVo createGroupChat(CreateGroupChatVo groupChatVo);

    /**
     * 获取群聊列表
     *
     * @param username 用户名
     * @return java.util.List<com.tomhusky.wechatmvc.server.vo.query.GroupChatListVo>
     */
    List<GroupChatListVo> listGroupChat(String username);

}
