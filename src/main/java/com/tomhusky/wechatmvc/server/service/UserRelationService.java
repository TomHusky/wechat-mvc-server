package com.tomhusky.wechatmvc.server.service;

import com.tomhusky.wechatmvc.server.common.base.BaseService;
import com.tomhusky.wechatmvc.server.entity.FriendApply;
import com.tomhusky.wechatmvc.server.entity.UserRelation;
import com.tomhusky.wechatmvc.server.vo.query.FriendListVo;
import org.apache.ibatis.annotations.Param;

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
     * 获取好友信息
     *
     * @param userId  用户id
     * @param friendId 好友id
     * @return com.tomhusky.wechatmvc.server.vo.query.FriendListVo
     */
    FriendListVo getFriendInfo(Integer userId,Integer friendId);

    /**
     * 添加好友关系
     *
     * @param friendApply 好友申请信息
     * @return boolean
     */
    boolean addFriend(FriendApply friendApply);
}
