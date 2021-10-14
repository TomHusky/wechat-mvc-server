package com.tomhusky.wechatmvc.server.mapper;

import com.tomhusky.wechatmvc.server.common.base.CommonMapper;
import com.tomhusky.wechatmvc.server.entity.FriendInfo;
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

