package io.github.tomhusky.wechatmvc.server.mapper;

import io.github.tomhusky.wechatmvc.server.entity.GroupChatUser;
import io.github.tomhusky.wechatmvc.server.common.base.CommonMapper;
import io.github.tomhusky.wechatmvc.server.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper 接口
 * @author lwj
 * @date 2021-09-15
 */
@Mapper
public interface GroupChatUserMapper extends CommonMapper<GroupChatUser> {

    /**
     * 获取群聊所有用户
     *
     * @param groupNo 群编号
     * @return java.util.List<io.github.tomhusky.wechatmvc.server.entity.User>
     */
    List<User> listGroupChatUser(String groupNo);


}
