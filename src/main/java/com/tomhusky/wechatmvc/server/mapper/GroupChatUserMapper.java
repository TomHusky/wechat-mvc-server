package com.tomhusky.wechatmvc.server.mapper;

import com.tomhusky.wechatmvc.server.entity.GroupChatUser;
import com.tomhusky.wechatmvc.server.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
/**
 *  Mapper 接口
 * @author lwj
 * @date 2021-09-15
 */
@Mapper
public interface GroupChatUserMapper extends BaseDao<GroupChatUser> {

}
