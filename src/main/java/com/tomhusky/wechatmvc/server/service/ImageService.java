package com.tomhusky.wechatmvc.server.service;

import java.util.List;

/**
 * <p>
 * 图片处理接口
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/12 9:33
 */
public interface ImageService {

    /**
     * 生成群聊图片
     *
     * @param images 图片列表
     * @param groupNo 群聊编号
     * @return java.lang.String
     */
    String getGroupAvatar(List<String> images,String groupNo);

}
