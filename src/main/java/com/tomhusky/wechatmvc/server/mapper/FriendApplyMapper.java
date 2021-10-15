package com.tomhusky.wechatmvc.server.mapper;

import com.tomhusky.wechatmvc.server.common.base.CommonMapper;
import com.tomhusky.wechatmvc.server.entity.FriendApply;
import com.tomhusky.wechatmvc.server.vo.msg.AddFriendMsg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 好友申请
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/14 11:13
 */
@Mapper
public interface FriendApplyMapper extends CommonMapper<FriendApply> {

    /**
     * 获取用户的所有新好友请求
     *
     * @param username 账号
     * @return java.util.List<com.tomhusky.wechatmvc.server.vo.msg.AddFriendMsg>
     */
    List<AddFriendMsg> listAllFriendApply(String username);
}
