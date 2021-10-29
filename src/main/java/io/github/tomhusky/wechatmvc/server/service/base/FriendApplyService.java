package io.github.tomhusky.wechatmvc.server.service.base;

import io.github.tomhusky.wechatmvc.server.common.base.BaseService;
import io.github.tomhusky.wechatmvc.server.entity.FriendApply;
import io.github.tomhusky.wechatmvc.server.vo.add.AddFriendVo;
import io.github.tomhusky.wechatmvc.server.vo.update.FriendApplyUpdate;

import java.util.List;

/**
 * <p>
 * 好友申请服务
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/14 11:14
 */
public interface FriendApplyService extends BaseService<FriendApply> {

    /**
     * 发送用户的所有新好友请求消息
     *
     * @param username 账号
     * @return boolean
     */
    boolean sendAllFriendApplyMsg(String username);

    /**
     * 获取用户的所有新好友请求
     *
     * @param username 账号
     * @return java.util.List<com.tomhusky.wechatmvc.server.entity.FriendApply>
     */
    List<FriendApply> listAllFriendApply(String username);

    /**
     * 申请添加好友
     *
     * @param addFriendVo 添加好友信息
     * @return java.lang.Boolean
     */
    Boolean applyAddFriend(AddFriendVo addFriendVo);

    /**
     * 处理好友申请
     *
     * @param applyUpdate 处理数据
     * @return java.lang.Boolean
     */
    Boolean handleApply(FriendApplyUpdate applyUpdate);
}