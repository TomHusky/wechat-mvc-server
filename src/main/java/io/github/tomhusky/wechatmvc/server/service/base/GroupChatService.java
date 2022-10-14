package io.github.tomhusky.wechatmvc.server.service.base;

import io.github.tomhusky.wechatmvc.server.common.base.BaseService;
import io.github.tomhusky.wechatmvc.server.entity.GroupChat;
import io.github.tomhusky.wechatmvc.server.vo.add.CreateGroupChatVo;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupChatListVo;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupUserDetail;
import io.github.tomhusky.wechatmvc.server.vo.update.InviteUserVo;
import io.github.tomhusky.wechatmvc.server.vo.update.UpdateGroupChatUserVo;
import io.github.tomhusky.wechatmvc.server.vo.update.UpdateGroupChatVo;

import java.util.List;

/**
 * 服务类
 *
 * @author lwj
 * @date 2021-09-15
 */
public interface GroupChatService extends BaseService<GroupChat> {

    /**
     * 根据群聊编号获取群聊
     *
     * @param groupNo 群聊编号
     * @return io.github.tomhusky.wechatmvc.server.entity.GroupChat
     */
    GroupChat getByNo(String groupNo);

    /**
     * 创建群聊
     *
     * @param groupChatVo 群聊信息
     * @return com.tomhusky.wechatmvc.server.vo.query.GroupChatListVo
     */
    GroupChatListVo createGroupChat(CreateGroupChatVo groupChatVo);

    /**
     * 更新群聊信息
     *
     * @param chatVo 群聊信息
     * @return boolean
     */
    boolean updateGroupChat(UpdateGroupChatVo chatVo);

    /**
     * 群用户更新群聊信息
     *
     * @param userVo 用户信息
     * @return boolean
     */
    boolean userUpdateGroupChat(UpdateGroupChatUserVo userVo);

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

    /**
     * 用户离开群聊
     *
     * @param groupNo  群聊编号
     * @param username 用户名
     * @return boolean
     */
    boolean userDeleteChat(String groupNo, String username);

    /**
     * 邀请用户加入群里
     *
     * @param inviteUserVo 请求参数
     * @return boolean
     */
    boolean inviteUser(InviteUserVo inviteUserVo);
}
