package io.github.tomhusky.wechatmvc.server.service.base.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.github.tomhusky.wechatmvc.server.common.base.BaseServiceImpl;
import io.github.tomhusky.wechatmvc.server.common.enums.GroupUserType;
import io.github.tomhusky.wechatmvc.server.common.exception.DateNoneException;
import io.github.tomhusky.wechatmvc.server.entity.GroupChat;
import io.github.tomhusky.wechatmvc.server.entity.GroupChatUser;
import io.github.tomhusky.wechatmvc.server.entity.User;
import io.github.tomhusky.wechatmvc.server.mapper.GroupChatMapper;
import io.github.tomhusky.wechatmvc.server.service.base.GroupChatService;
import io.github.tomhusky.wechatmvc.server.service.base.GroupChatUserService;
import io.github.tomhusky.wechatmvc.server.service.base.ImageService;
import io.github.tomhusky.wechatmvc.server.service.base.UserService;
import io.github.tomhusky.wechatmvc.server.vo.add.CreateGroupChatVo;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupChatVo;
import io.github.tomhusky.wechatmvc.server.common.exception.OperateException;
import io.github.tomhusky.wechatmvc.server.security.JwtUser;
import io.github.tomhusky.wechatmvc.server.security.SecurityUtils;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupChatListVo;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupUserDetail;
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

    private List<GroupUserDetail> saveGroupChatUser(GroupChat groupChat, List<String> usernames) {
        User user = userService.getUserByName(groupChat.getOwnerId());
        if (user == null) {
            throw new OperateException("创建失败");
        }
        List<GroupUserDetail> objects = new ArrayList<>();
        List<String> avatars = new ArrayList<>();

        // 保存群主
        GroupChatUser ownerUser = new GroupChatUser();
        ownerUser.setGroupNo(groupChat.getGroupNo());
        ownerUser.setUserId(user.getId());
        ownerUser.setType(GroupUserType.OWNER.getValue());
        avatars.add(user.getAvatar());
        groupChatUserService.save(ownerUser);

        // 保存群成员
        objects.add(BeanUtil.copyProperties(user, GroupUserDetail.class));
        for (String username : usernames) {
            User u = userService.getUserByName(username);
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
}
