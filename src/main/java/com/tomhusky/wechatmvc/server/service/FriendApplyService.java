package com.tomhusky.wechatmvc.server.service;

import com.tomhusky.wechatmvc.server.common.base.BaseService;
import com.tomhusky.wechatmvc.server.entity.FriendApply;
import com.tomhusky.wechatmvc.server.vo.add.AddFriendVo;
import com.tomhusky.wechatmvc.server.vo.update.FriendApplyUpdate;

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