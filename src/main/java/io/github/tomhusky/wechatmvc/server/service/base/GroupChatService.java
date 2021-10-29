package io.github.tomhusky.wechatmvc.server.service.base;

import io.github.tomhusky.wechatmvc.server.entity.GroupChat;
import io.github.tomhusky.wechatmvc.server.common.base.BaseService;
import io.github.tomhusky.wechatmvc.server.vo.add.CreateGroupChatVo;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupChatListVo;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupUserDetail;

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

    /**
     * 根据群编号获取所有群成员
     *
     * @param groupNo 群编号
     * @return java.util.List<com.tomhusky.wechatmvc.server.vo.query.GroupUserDetail>
     */
    List<GroupUserDetail> listGroupChatAllUser(String groupNo);
}
