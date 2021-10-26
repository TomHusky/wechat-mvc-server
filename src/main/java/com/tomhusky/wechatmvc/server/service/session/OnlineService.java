package com.tomhusky.wechatmvc.server.service.session;

/**
 * <p>
 * 登录上线之后任务接口
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/15 10:38
 */
public interface OnlineService {

    /**
     * 启动上线任务处理
     *
     * @param username 用户账号
     */
    void startTask(String username);

    /**
     * 发送新好友验证消息
     *
     * @param username  用户账号
     */
    void sendNewFriendMsg(String username);

    /**
     * 发送未收到的离线消息
     *
     * @param username  用户账号
     */
    void sendOfflineChatMsg(String username);
}