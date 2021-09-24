package com.tomhusky.wechatmvc.server.mapper;

import com.tomhusky.wechatmvc.server.entity.UserRelation;
import com.tomhusky.wechatmvc.server.common.base.CommonMapper;
import com.tomhusky.wechatmvc.server.vo.query.FriendListVo;
import org.apache.ibatis.annotations.Mapper;

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
}
