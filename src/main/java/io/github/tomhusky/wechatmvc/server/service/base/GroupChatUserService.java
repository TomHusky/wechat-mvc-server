package io.github.tomhusky.wechatmvc.server.service.base;

import io.github.tomhusky.wechatmvc.server.entity.GroupChatUser;
import io.github.tomhusky.wechatmvc.server.common.base.BaseService;
import io.github.tomhusky.wechatmvc.server.entity.User;

import java.util.List;

/**
 *  服务类
 *
 * @author lwj
 * @date 2021-09-15
 */
public interface GroupChatUserService extends BaseService<GroupChatUser> {

    /**
     * 删除群聊用户
     *
     * @param groupNo  群聊编号
     * @param userId 用户id
     * @return boolean
     */
    boolean deleteUser(String groupNo,Integer userId);

    /**
     * 获取群聊指定用户信息
     *
     * @param groupNo  群编号
     * @param userId 用户id
     * @return io.github.tomhusky.wechatmvc.server.entity.GroupChatUser
     */
    GroupChatUser getGroupUser(String groupNo,Integer userId);

    /**
     * 获取群聊所有用户
     *
     * @param groupNo 群编号
     * @return java.util.List<io.github.tomhusky.wechatmvc.server.entity.User>
     */
    List<User> listGroupChatUser(String groupNo);

    /**
     * 获取群里的所有用户
     *
     * @param groupNo  群编号
     * @return java.util.List<io.github.tomhusky.wechatmvc.server.entity.GroupChatUser>
     */
    List<GroupChatUser> listByGroupNo(String groupNo);
}
