package com.tomhusky.wechatmvc.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.tomhusky.wechatmvc.server.common.JsonResult;
import com.tomhusky.wechatmvc.server.common.base.BaseServiceImpl;
import com.tomhusky.wechatmvc.server.common.enums.ApplyStatus;
import com.tomhusky.wechatmvc.server.common.enums.MsgUrlType;
import com.tomhusky.wechatmvc.server.common.exception.DateNoneException;
import com.tomhusky.wechatmvc.server.common.exception.OperateException;
import com.tomhusky.wechatmvc.server.entity.FriendApply;
import com.tomhusky.wechatmvc.server.entity.User;
import com.tomhusky.wechatmvc.server.mapper.FriendApplyMapper;
import com.tomhusky.wechatmvc.server.security.SecurityUtils;
import com.tomhusky.wechatmvc.server.service.FriendApplyService;
import com.tomhusky.wechatmvc.server.service.UserRelationService;
import com.tomhusky.wechatmvc.server.service.UserService;
import com.tomhusky.wechatmvc.server.session.OnlineUserManage;
import com.tomhusky.wechatmvc.server.vo.add.AddFriendVo;
import com.tomhusky.wechatmvc.server.vo.msg.AddFriendMsg;
import com.tomhusky.wechatmvc.server.vo.query.FriendListVo;
import com.tomhusky.wechatmvc.server.vo.update.FriendApplyUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 好友申请服务实现
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/14 11:15
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class FriendApplyServiceImpl extends BaseServiceImpl<FriendApplyMapper, FriendApply> implements FriendApplyService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRelationService userRelationService;

    @Override
    public boolean sendAllFriendApplyMsg(String username) {
        List<FriendApply> friendApplies = listAllFriendApply(username);
        if (friendApplies.isEmpty()) {
            return true;
        }
        sendMsg(friendApplies, username);
        return true;
    }

    @Override
    public List<FriendApply> listAllFriendApply(String username) {
        return this.lambdaQuery().eq(FriendApply::getReceiveUser, username)
                .eq(FriendApply::getStatus, ApplyStatus.APPLYING.getValue())
                .list();
    }

    @Override
    public Boolean applyAddFriend(AddFriendVo addFriendVo) {
        String username = SecurityUtils.getUsername();
        FriendApply friendApply = new FriendApply();

        friendApply.setApplyId(IdUtil.nanoId(10) + IdUtil.fastSimpleUUID().substring(23, 31));
        friendApply.setApplyUser(username);
        friendApply.setReceiveUser(addFriendVo.getUsername());
        friendApply.setApplyRemark(addFriendVo.getRemark());
        friendApply.setApplyInfo(addFriendVo.getInfo());
        friendApply.setOrigin("账号搜索");

        this.save(friendApply);

        if (OnlineUserManage.isOnline(username)) {
            friendApply.setStatus(ApplyStatus.APPLYING.getValue());
            sendMsg(Collections.singletonList(friendApply), friendApply.getReceiveUser());
        }
        return true;
    }

    private void sendMsg(List<FriendApply> friendApplyList, String username) {
        List<AddFriendMsg> addFriendMsgList = new ArrayList<>();
        for (FriendApply friendApply : friendApplyList) {
            User userByName = userService.getUserByName(friendApply.getApplyUser());
            AddFriendMsg addFriendMsg = BeanUtil.copyProperties(userByName, AddFriendMsg.class);
            addFriendMsg.setApplyId(friendApply.getApplyId());
            addFriendMsg.setInfo(friendApply.getApplyInfo());
            addFriendMsg.setStatus(friendApply.getStatus());
            addFriendMsgList.add(addFriendMsg);
        }
        OnlineUserManage.sendMessages(MsgUrlType.NEW_FRIEND_MSG.getUrl(), username, JsonResult.success(addFriendMsgList));
    }

    @Override
    public Boolean handleApply(FriendApplyUpdate applyUpdate) {
        FriendApply friendApply = getById(applyUpdate.getApplyId());
        if (friendApply == null) {
            throw new OperateException("申请不存在");
        }
        if (applyUpdate.getStatus().equals(ApplyStatus.Added.getValue())) {
            userRelationService.addFriend(friendApply);
            // 发送添加好友消息
            sendAddFriendMsg(friendApply);
        }
        friendApply.setStatus(applyUpdate.getStatus());
        return updateById(friendApply);
    }

    private void sendAddFriendMsg(FriendApply friendApply) {
        User user = userService.getUserByName(friendApply.getApplyUser());
        User friend = userService.getUserByName(friendApply.getReceiveUser());
        if (user == null || friend == null) {
            throw new DateNoneException("用户不存在");
        }
        FriendListVo applyUser = userRelationService.getFriendInfo(user.getId(), friend.getId());
        FriendListVo receiveUser = userRelationService.getFriendInfo(friend.getId(), user.getId());
        OnlineUserManage.sendMessages(MsgUrlType.ADD_FRIEND_MSG.getUrl(), friendApply.getApplyUser(), JsonResult.success(applyUser));
        OnlineUserManage.sendMessages(MsgUrlType.ADD_FRIEND_MSG.getUrl(), friendApply.getReceiveUser(), JsonResult.success(receiveUser));
    }

}
