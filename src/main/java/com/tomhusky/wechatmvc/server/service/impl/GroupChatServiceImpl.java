package com.tomhusky.wechatmvc.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.tomhusky.wechatmvc.server.common.base.BaseServiceImpl;
import com.tomhusky.wechatmvc.server.common.exception.DateNoneException;
import com.tomhusky.wechatmvc.server.common.exception.OperateException;
import com.tomhusky.wechatmvc.server.entity.GroupChat;
import com.tomhusky.wechatmvc.server.entity.GroupChatUser;
import com.tomhusky.wechatmvc.server.entity.User;
import com.tomhusky.wechatmvc.server.mapper.GroupChatMapper;
import com.tomhusky.wechatmvc.server.security.JwtUser;
import com.tomhusky.wechatmvc.server.security.SecurityUtils;
import com.tomhusky.wechatmvc.server.service.GroupChatService;
import com.tomhusky.wechatmvc.server.service.GroupChatUserService;
import com.tomhusky.wechatmvc.server.service.ImageService;
import com.tomhusky.wechatmvc.server.service.UserService;
import com.tomhusky.wechatmvc.server.vo.add.CreateGroupChatVo;
import com.tomhusky.wechatmvc.server.vo.query.GroupChatListVo;
import com.tomhusky.wechatmvc.server.vo.query.GroupUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 服务实现类
 *
 * @author lwj
 * @date 2021-09-15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupChatServiceImpl extends BaseServiceImpl<GroupChatMapper, GroupChat> implements GroupChatService {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupChatUserService groupChatUserService;

    @Autowired
    private ImageService imageService;

    @Override
    public GroupChatListVo createGroupChat(CreateGroupChatVo groupChatVo) {
        JwtUser loginUser = SecurityUtils.getLoginUser();
        String groupNo = IdUtil.nanoId(10);
        synchronized (this) {
            String times = System.currentTimeMillis() + "";
            groupNo = groupNo + times.substring(times.length() - 4);
        }
        GroupChat groupChat = new GroupChat();
        groupChat.setGroupName(groupChatVo.getGroupName());
        groupChat.setOwnerId(loginUser.getUser().getWxid());
        groupChat.setGroupNo(groupNo);

        this.save(groupChat);

        GroupChatListVo groupChatListVo = BeanUtil.copyProperties(groupChat, GroupChatListVo.class);
        saveGroupChatUser(groupChat, groupChatVo.getUserDetails());

        this.updateById(groupChat);

        return groupChatListVo;
    }

    private List<GroupUserDetail> saveGroupChatUser(GroupChat groupChat, List<CreateGroupChatVo.UserDetail> userDetailList) {
        User user = userService.getByWxid(groupChat.getOwnerId());
        if (user == null) {
            throw new OperateException("创建失败");
        }
        List<GroupUserDetail> objects = new ArrayList<>();
        List<String> avatars = new ArrayList<>();

        objects.add(BeanUtil.copyProperties(user, GroupUserDetail.class));
        for (CreateGroupChatVo.UserDetail userDetail : userDetailList) {
            User u = userService.getByWxid(userDetail.getWxid());
            GroupChatUser groupChatUser = new GroupChatUser();
            groupChatUser.setGroupNo(groupChat.getGroupNo());
            groupChatUser.setUserId(u.getId());

            avatars.add(u.getAvatar());

            groupChatUserService.save(groupChatUser);
            GroupUserDetail groupUserDetail = new GroupUserDetail();
            BeanUtil.copyProperties(u, groupUserDetail);
            objects.add(groupUserDetail);
        }
        // 生成头像
        groupChat.setGroupAvatar(imageService.getGroupAvatar(avatars, groupChat.getGroupNo()));

        return objects;
    }

    @Override
    public List<GroupChatListVo> listGroupChat(String username) {
        User user = userService.getUserByName(username);
        if (user == null) {
            throw new DateNoneException("用户不存在");
        }
        List<GroupChat> groupChats = baseMapper.listGroupChatByUser(user.getId());
        if (groupChats.isEmpty()) {
            return Collections.emptyList();
        }
        List<GroupChatListVo> groupChatListVoList = new ArrayList<>();
        for (GroupChat groupChat : groupChats) {
            GroupChatListVo groupChatListVo = new GroupChatListVo();
            groupChatListVo.setGroupNo(groupChat.getGroupNo());
            groupChatListVo.setGroupName(groupChat.getGroupName());
            groupChatListVo.setGroupAvatar(groupChat.getGroupAvatar());
            groupChatListVo.setOwnerId(groupChat.getOwnerId());
            groupChatListVo.setNotice(groupChat.getNotice());
            List<GroupUserDetail> userDetails = baseMapper.listGroupChatUserInfo(groupChat.getGroupNo());
            groupChatListVo.setUserDetails(userDetails);
            groupChatListVoList.add(groupChatListVo);
        }
        return groupChatListVoList;
    }
}
