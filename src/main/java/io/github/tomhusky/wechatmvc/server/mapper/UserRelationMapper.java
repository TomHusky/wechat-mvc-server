package io.github.tomhusky.wechatmvc.server.mapper;

import io.github.tomhusky.wechatmvc.server.common.base.CommonMapper;
import io.github.tomhusky.wechatmvc.server.entity.UserRelation;
import io.github.tomhusky.wechatmvc.server.vo.query.FriendListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  Mapper 接口
 * @author lwj
 * @date 2021-09-15
 */
@Mapper
public interface UserRelationMapper extends CommonMapper<UserRelation> {

    /**
     * 获取所有好友信息 不分页
     *
     * @param userId 用户id
     * @return java.util.List<com.tomhusky.wechatmvc.server.vo.query.FriendListVo>
     */
    List<FriendListVo> listAllFriendInfo(Integer userId);

    /**
     * 获取好友信息
     *
     * @param userId  用户id
     * @param friendId 好友id
     * @return com.tomhusky.wechatmvc.server.vo.query.FriendListVo
     */
    FriendListVo getFriendInfo(@Param("userId") Integer userId, @Param("friendId")Integer friendId);
}
