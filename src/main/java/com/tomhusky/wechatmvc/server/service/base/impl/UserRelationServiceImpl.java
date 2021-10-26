package com.tomhusky.wechatmvc.server.service.base.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.tomhusky.wechatmvc.server.common.base.BaseServiceImpl;
import com.tomhusky.wechatmvc.server.common.exception.DateNoneException;
import com.tomhusky.wechatmvc.server.entity.FriendApply;
import com.tomhusky.wechatmvc.server.entity.FriendInfo;
import com.tomhusky.wechatmvc.server.entity.User;
import com.tomhusky.wechatmvc.server.entity.UserRelation;
import com.tomhusky.wechatmvc.server.mapper.FriendInfoMapper;
import com.tomhusky.wechatmvc.server.mapper.UserRelationMapper;
import com.tomhusky.wechatmvc.server.service.base.UserRelationService;
import com.tomhusky.wechatmvc.server.service.base.UserService;
import com.tomhusky.wechatmvc.server.vo.query.FriendListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务实现类
 *
 * @author lwj
 * @date 2021-09-15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRelationServiceImpl extends BaseServiceImpl<UserRelationMapper, UserRelation> implements UserRelationService {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendInfoMapper friendInfoMapper;

    @Override
    public List<FriendListVo> listAllFriendInfo(String username) {
        User user = userService.getUserByName(username);
        if (user == null) {
            throw new DateNoneException("用户不存在");
        }
        return mapper.listAllFriendInfo(user.getId());
    }

    @Override
    public FriendListVo getFriendInfo(Integer userId,Integer friendId) {
        return mapper.getFriendInfo(userId, friendId);
    }

    @Override
    public boolean addFriend(FriendApply friendApply) {
        User applyUser = userService.getUserByName(friendApply.getApplyUser());
        User receiveUser = userService.getUserByName(friendApply.getReceiveUser());
        if (applyUser == null || receiveUser == null) {
            throw new DateNoneException("添加好友失败");
        }
        // 添加好友
        UserRelation fromRelation = new UserRelation().
                setUserId(applyUser.getId()).setFriendId(receiveUser.getId());
        // 被添加好友
        UserRelation toRelation = new UserRelation().
                setUserId(receiveUser.getId()).setFriendId(applyUser.getId());

        this.save(fromRelation);
        this.save(toRelation);

        FriendInfo applyFriendInfo = new FriendInfo().setRelatedId(fromRelation.getId())
                .setRemark(friendApply.getApplyRemark()).setOrigin(friendApply.getOrigin());

        // 获取好友首字母
        if (CharSequenceUtil.isNotBlank(applyFriendInfo.getRemark())) {
            String firstLetter = PinyinUtil.getFirstLetter(applyFriendInfo.getRemark().substring(0, 1), ",");
            applyFriendInfo.setInitial(CharSequenceUtil.upperFirst(firstLetter));
        } else {
            String firstLetter = PinyinUtil.getFirstLetter(applyUser.getNickname().substring(0, 1), ",");
            applyFriendInfo.setInitial(CharSequenceUtil.upperFirst(firstLetter));
        }

        FriendInfo receiveFriendInfo = new FriendInfo().setRelatedId(toRelation.getId()).setRemark(applyUser.getNickname())
                .setOrigin(friendApply.getOrigin());

        String firstLetter = PinyinUtil.getFirstLetter(receiveFriendInfo.getRemark().substring(0, 1), ",");
        receiveFriendInfo.setInitial(CharSequenceUtil.upperFirst(firstLetter));


        friendInfoMapper.insert(applyFriendInfo);
        friendInfoMapper.insert(receiveFriendInfo);

        return true;
    }

}
