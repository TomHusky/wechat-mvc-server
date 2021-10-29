package io.github.tomhusky.wechatmvc.server.mapper;

import io.github.tomhusky.wechatmvc.server.vo.query.GroupChatVo;
import io.github.tomhusky.wechatmvc.server.common.base.CommonMapper;
import io.github.tomhusky.wechatmvc.server.entity.GroupChat;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupUserDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author lwj
 * @date 2021-09-15
 */
@Mapper
public interface GroupChatMapper extends CommonMapper<GroupChat> {
    /**
     * 获取用户群聊列表
     *
     * @param groupNo 群编号
     * @return java.util.List<com.tomhusky.wechatmvc.server.vo.query.GroupUserDetail>
     */
    List<GroupUserDetail> listGroupChatUserInfo(String groupNo);

    /**
     * 获取用户的群聊列表
     *
     * @param userId 用户id
     * @return java.util.List<com.tomhusky.wechatmvc.server.vo.query.GroupChatVo>
     */
    List<GroupChatVo> listGroupChatByUser(Integer userId);
}
