package com.tomhusky.wechatmvc.server.service;

import com.tomhusky.wechatmvc.server.common.base.BaseService;
import com.tomhusky.wechatmvc.server.entity.UserRelation;
import com.tomhusky.wechatmvc.server.vo.query.FriendListVo;

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
}
