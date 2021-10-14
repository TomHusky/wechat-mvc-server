package com.tomhusky.wechatmvc.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.tomhusky.wechatmvc.server.common.base.BaseServiceImpl;
import com.tomhusky.wechatmvc.server.common.enums.ApplyStatus;
import com.tomhusky.wechatmvc.server.common.enums.MsgUrlType;
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
import com.tomhusky.wechatmvc.server.vo.update.FriendApplyUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            User userByName = userService.getUserByName(username);
            AddFriendMsg addFriendMsg = BeanUtil.copyProperties(userByName, AddFriendMsg.class);
            addFriendMsg.setApplyId(friendApply.getApplyId());
            addFriendMsg.setInfo(addFriendVo.getInfo());
            OnlineUserManage.sendMessages(MsgUrlType.NEW_FRIEND_MSG.getUrl(), username, addFriendMsg);
        }
        return true;
    }

    @Override
    public Boolean handleApply(FriendApplyUpdate applyUpdate) {
        FriendApply friendApply = getById(applyUpdate.getApplyId());
        if (friendApply == null) {
            throw new OperateException("申请不存在");
        }
        if (applyUpdate.getStatus().equals(ApplyStatus.Added.getValue())) {
            //TODO 添加成功
            userRelationService.addFriend(friendApply);
        }
        friendApply.setStatus(friendApply.getApplyId());
        return updateById(friendApply);
    }

}
