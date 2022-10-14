package io.github.tomhusky.wechatmvc.server.service.base.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.github.tomhusky.wechatmvc.server.common.base.BaseServiceImpl;
import io.github.tomhusky.wechatmvc.server.common.enums.GroupUserType;
import io.github.tomhusky.wechatmvc.server.common.exception.DateNoneException;
import io.github.tomhusky.wechatmvc.server.common.exception.OperateException;
import io.github.tomhusky.wechatmvc.server.entity.GroupChat;
import io.github.tomhusky.wechatmvc.server.entity.GroupChatUser;
import io.github.tomhusky.wechatmvc.server.entity.User;
import io.github.tomhusky.wechatmvc.server.mapper.GroupChatMapper;
import io.github.tomhusky.wechatmvc.server.security.JwtUser;
import io.github.tomhusky.wechatmvc.server.security.SecurityUtils;
import io.github.tomhusky.wechatmvc.server.service.base.GroupChatService;
import io.github.tomhusky.wechatmvc.server.service.base.GroupChatUserService;
import io.github.tomhusky.wechatmvc.server.service.base.ImageService;
import io.github.tomhusky.wechatmvc.server.service.base.UserService;
import io.github.tomhusky.wechatmvc.server.vo.add.CreateGroupChatVo;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupChatListVo;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupChatVo;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupUserDetail;
import io.github.tomhusky.wechatmvc.server.vo.update.InviteUserVo;
import io.github.tomhusky.wechatmvc.server.vo.update.UpdateGroupChatUserVo;
import io.github.tomhusky.wechatmvc.server.vo.update.UpdateGroupChatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public GroupChat getByNo(String groupNo) {
        return this.lambdaQuery().eq(GroupChat::getGroupNo, groupNo)
                .one();
    }

    @Override
    public GroupChatListVo createGroupChat(CreateGroupChatVo groupChatVo) {
        JwtUser loginUser = SecurityUtils.getLoginUser();
        String groupNo = IdUtil.nanoId(10);
        synchronized (this) {
            String times = System.currentTimeMillis() + "";
            groupNo = groupNo + times.substring(times.length() - 4);
        }
        GroupChat groupChat = new GroupChat();
        if (StrUtil.isBlank(groupChatVo.getGroupName())) {
            groupChatVo.setGroupName("群聊");
        }
        groupChat.setGroupName(groupChatVo.getGroupName());
        groupChat.setOwnerId(loginUser.getUser().getUsername());
        groupChat.setGroupNo(groupNo);

        this.save(groupChat);

        List<GroupUserDetail> groupUserDetails = saveGroupChatUser(groupChat, groupChatVo.getUsernames());
        GroupChatListVo groupChatListVo = BeanUtil.copyProperties(groupChat, GroupChatListVo.class);
        groupChatListVo.setUserDetails(groupUserDetails);

        this.updateById(groupChat);

        return groupChatListVo;
    }

    @Override
    public boolean updateGroupChat(UpdateGroupChatVo chatVo) {
        GroupChat groupChat = this.getByNo(chatVo.getGroupNo());
        if (groupChat == null) {
            throw new DateNoneException("群聊不存在");
        }
        if (CharSequenceUtil.isNotBlank(chatVo.getGroupName())) {
            groupChat.setGroupName(chatVo.getGroupName());
        }
        if (CharSequenceUtil.isNotBlank(chatVo.getNotice()) && groupChat.getOwnerId().equals(SecurityUtils.getUsername())) {
            groupChat.setNotice(chatVo.getNotice());
        }
        return updateById(groupChat);
    }

    @Override
    public boolean userUpdateGroupChat(UpdateGroupChatUserVo userVo) {
        GroupChat groupChat = this.getByNo(userVo.getGroupNo());
        if (groupChat == null) {
            throw new DateNoneException("群聊不存在");
        }
        Integer userId = SecurityUtils.getLoginUser().getUser().getUserId();
        GroupChatUser groupUser = groupChatUserService.getGroupUser(userVo.getGroupNo(), userId);
        if (groupUser == null) {
            throw new DateNoneException("用户不在群聊");
        }
        GroupChatUser groupChatUser = BeanUtil.copyProperties(userVo, GroupChatUser.class);
        groupChatUser.setId(groupUser.getId());
        return groupChatUserService.updateById(groupChatUser);
    }

    private List<GroupUserDetail> saveGroupChatUser(GroupChat groupChat, List<String> usernames) {
        User user = userService.getUserByName(groupChat.getOwnerId());
        if (user == null) {
            throw new OperateException("创建失败");
        }
        List<GroupUserDetail> objects = new ArrayList<>();
        List<User> avatars = new ArrayList<>();

        // 保存群主
        GroupChatUser ownerUser = new GroupChatUser();
        ownerUser.setGroupNo(groupChat.getGroupNo());
        ownerUser.setUserId(user.getId());
        ownerUser.setNickname(user.getNickname());
        ownerUser.setType(GroupUserType.OWNER.getValue());
        avatars.add(user);
        groupChatUserService.save(ownerUser);

        // 保存群成员
        objects.add(BeanUtil.copyProperties(user, GroupUserDetail.class));
        for (String username : usernames) {
            User u = userService.getUserByName(username);
            GroupChatUser groupChatUser = new GroupChatUser();
            groupChatUser.setGroupNo(groupChat.getGroupNo());
            groupChatUser.setUserId(u.getId());
            groupChatUser.setNickname(u.getNickname());
            avatars.add(u);

            groupChatUserService.save(groupChatUser);

            GroupUserDetail groupUserDetail = new GroupUserDetail();
            BeanUtil.copyProperties(u, groupUserDetail);
            groupUserDetail.setGroupNickname(groupChatUser.getNickname());
            objects.add(groupUserDetail);
        }
        // 生成头像
        initGroupAvatar(groupChat, avatars);

        return objects;
    }

    private void initGroupAvatar(GroupChat groupChat, List<User> users) {
        if (users.size() > 9) {
            users = users.subList(0, 9);
        }
        List<String> avatars = new ArrayList<>();
        for (User u : users) {
            avatars.add(u.getAvatar());
        }
        // 生成头像
        groupChat.setGroupAvatar(imageService.getGroupAvatar(avatars, groupChat.getGroupNo()));
    }

    @Override
    public List<GroupChatListVo> listGroupChat(String username) {
        User user = userService.getUserByName(username);
        if (user == null) {
            throw new DateNoneException("用户不存在");
        }
        List<GroupChatVo> groupChats = baseMapper.listGroupChatByUser(user.getId());
        if (groupChats.isEmpty()) {
            return Collections.emptyList();
        }
        List<GroupChatListVo> groupChatListVoList = new ArrayList<>();
        for (GroupChatVo groupChat : groupChats) {
            GroupChatListVo groupChatListVo = new GroupChatListVo();
            BeanUtil.copyProperties(groupChat, groupChatListVo);
            List<GroupUserDetail> userDetails = baseMapper.listGroupChatUserInfo(groupChat.getGroupNo());
            groupChatListVo.setUserDetails(userDetails);
            groupChatListVoList.add(groupChatListVo);
        }
        return groupChatListVoList;
    }

    @Override
    public List<GroupUserDetail> listGroupChatAllUser(String groupNo) {
        return baseMapper.listGroupChatUserInfo(groupNo);
    }

    @Override
    public boolean userDeleteChat(String groupNo, String username) {
        User user = userService.getUserByName(username);
        if (user == null) {
            throw new DateNoneException("用户不存在");
        }
        GroupChat groupChat = getByNo(groupNo);
        if (groupChat == null) {
            throw new DateNoneException("群聊不存在");
        }

        boolean deleteUser = groupChatUserService.deleteUser(groupNo, user.getId());
        if (deleteUser) {
            List<User> users = groupChatUserService.listGroupChatUser(groupNo);
            initGroupAvatar(groupChat, users);

            updateById(groupChat);
        }
        return deleteUser;
    }

    @Override
    public boolean inviteUser(InviteUserVo inviteUserVo) {
        GroupChat groupChat = getByNo(inviteUserVo.getGroupNo());
        if (groupChat == null) {
            throw new DateNoneException("群聊不存在");
        }
        List<User> groupChatUsers = groupChatUserService.listGroupChatUser(groupChat.getGroupNo());

        if (inviteUserVo.getUsers().size() + groupChatUsers.size() > 30) {
            throw new OperateException("群聊最多30人");
        }

        long exit = groupChatUsers.stream().map(User::getUsername).filter(item -> inviteUserVo.getUsers().contains(item))
                .count();
        if (exit != 0) {
            throw new OperateException("存在已经在群聊的用户");
        }
        List<GroupChatUser> addList = inviteUserVo.getUsers().stream().map(item -> {
            User userById = userService.getUserByName(item);
            GroupChatUser chatUser = new GroupChatUser();
            chatUser.setUserId(userById.getId());
            chatUser.setGroupNo(groupChat.getGroupNo());
            chatUser.setNickname(userById.getNickname());
            chatUser.setType(GroupUserType.NORMAL.getValue());
            chatUser.setNotDisturb(false);
            return chatUser;
        }).collect(Collectors.toList());

        groupChatUserService.saveBatch(addList);

        if (groupChatUsers.size() < 9) {
            List<User> users = groupChatUserService.listGroupChatUser(groupChat.getGroupNo())
                    .stream().limit(9).collect(Collectors.toList());
            // 生成头像
            initGroupAvatar(groupChat, users);
            this.updateById(groupChat);
        }
        return true;
    }
}
