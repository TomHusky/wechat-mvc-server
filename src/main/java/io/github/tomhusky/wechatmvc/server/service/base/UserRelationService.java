package io.github.tomhusky.wechatmvc.server.service.base;

import io.github.tomhusky.wechatmvc.server.common.base.BaseService;
import io.github.tomhusky.wechatmvc.server.entity.UserRelation;
import io.github.tomhusky.wechatmvc.server.entity.FriendApply;
import io.github.tomhusky.wechatmvc.server.vo.query.FriendListVo;
import io.github.tomhusky.wechatmvc.server.vo.update.UpdateFriendVo;

import java.util.List;

/**
 * 服务类
 *
 * @author lwj
 * @date 2021-09-15
 */
public interface UserRelationService extends BaseService<UserRelation> {

    /**
     * 获取所有好友信息 不分页
     *
     * @param username 账号名
     * @return java.util.List<com.tomhusky.wechatmvc.server.vo.query.FriendListVo>
     */
    List<FriendListVo> listAllFriendInfo(String username);


    /**
     * 获取好友关系
     *
     * @param userId  用户id
     * @param friendId 好友id
     * @return io.github.tomhusky.wechatmvc.server.entity.UserRelation
     */
    UserRelation getFriendRelation(Integer userId, Integer friendId);

    /**
     * 获取好友信息
     *
     * @param userId  用户id
     * @param friendId 好友id
     * @return com.tomhusky.wechatmvc.server.vo.query.FriendListVo
     */
    FriendListVo getFriendInfo(Integer userId,Integer friendId);

    /**
     * 获取好友信息
     *
     * @param username 好友账户名
     * @return com.tomhusky.wechatmvc.server.vo.query.FriendListVo
     */
    FriendListVo getFriendInfoByUsername(String username);

    /**
     * 添加好友关系
     *
     * @param friendApply 好友申请信息
     * @return boolean
     */
    boolean addFriend(FriendApply friendApply);

    /**
     * 更新好友信息
     *
     * @param updateFriendVo 更新好友信息
     * @return java.lang.Boolean
     */
    Boolean updateFriendInfo(UpdateFriendVo updateFriendVo);
}
