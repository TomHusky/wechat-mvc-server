package io.github.tomhusky.wechatmvc.server.mapper;

import io.github.tomhusky.wechatmvc.server.entity.FriendInfo;
import io.github.tomhusky.wechatmvc.server.common.base.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

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

}

