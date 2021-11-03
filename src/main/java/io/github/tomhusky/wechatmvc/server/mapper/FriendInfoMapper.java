package io.github.tomhusky.wechatmvc.server.mapper;

import io.github.tomhusky.wechatmvc.server.common.base.CommonMapper;
import io.github.tomhusky.wechatmvc.server.entity.FriendInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 好友信息
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/14 11:50
 */
@Mapper
public interface FriendInfoMapper extends CommonMapper<FriendInfo> {

    /**
     * 获取好友关系详情
     *
     * @param relatedId 关系id
     * @return io.github.tomhusky.wechatmvc.server.entity.FriendInfo
     */
    FriendInfo getFriendInfo(Integer relatedId);

    /**
     * 根据用户id获取好友关系详情
     *
     * @param userId   用户id
     * @param friendId 好友id
     * @return io.github.tomhusky.wechatmvc.server.entity.FriendInfo
     */
    FriendInfo getFriendInfoByUser(@Param("userId") Integer userId, @Param("friendId") Integer friendId);

}

