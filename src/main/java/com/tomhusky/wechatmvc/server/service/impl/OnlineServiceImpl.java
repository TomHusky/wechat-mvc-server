package com.tomhusky.wechatmvc.server.service.impl;

import com.tomhusky.wechatmvc.server.common.util.Threads;
import com.tomhusky.wechatmvc.server.service.FriendApplyService;
import com.tomhusky.wechatmvc.server.service.OnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 上线任务接口实现
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/15 10:40
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OnlineServiceImpl implements OnlineService {

    @Autowired
    private FriendApplyService friendApplyService;

    @Async
    @Override
    public void startTask(String username) {
        Threads.sleepSecond(2);
        sendNewFriendMsg(username);
    }

    @Override
    public void sendNewFriendMsg(String username) {
        friendApplyService.sendAllFriendApplyMsg(username);
    }

    @Override
    public void sendOfflineChatMsg(String username) {

    }
}
