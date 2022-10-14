package io.github.tomhusky.wechatmvc.server.controller;

import io.github.tomhusky.wechatmvc.server.common.JsonResult;
import io.github.tomhusky.wechatmvc.server.security.SecurityUtils;
import io.github.tomhusky.wechatmvc.server.service.base.GroupChatService;
import io.github.tomhusky.wechatmvc.server.vo.add.CreateGroupChatVo;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupChatListVo;
import io.github.tomhusky.wechatmvc.server.vo.update.InviteUserVo;
import io.github.tomhusky.wechatmvc.server.vo.update.UpdateGroupChatUserVo;
import io.github.tomhusky.wechatmvc.server.vo.update.UpdateGroupChatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 群聊接口
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/11 14:01
 */
@RequestMapping("groupChat")
@RestController
public class GroupChatController {

    @Autowired
    private GroupChatService groupChatService;

    @PostMapping("/createChat")
    public JsonResult<GroupChatListVo> listChatInfo(@RequestBody @Valid CreateGroupChatVo groupChatVo) {
        GroupChatListVo groupChat = groupChatService.createGroupChat(groupChatVo);
        return JsonResult.success(groupChat);
    }

    @PostMapping("/updateGroupChat")
    public JsonResult<Boolean> updateGroupChat(@RequestBody @Valid UpdateGroupChatVo groupChatVo) {
        boolean updateGroupChat = groupChatService.updateGroupChat(groupChatVo);
        return JsonResult.success(updateGroupChat);
    }

    @PostMapping("/userUpdateGroupChat")
    public JsonResult<Boolean> updateGroupChatUser(@RequestBody @Valid UpdateGroupChatUserVo updateGroupChatVo) {
        boolean updateGroupChat = groupChatService.userUpdateGroupChat(updateGroupChatVo);
        return JsonResult.success(updateGroupChat);
    }

    @GetMapping("/listGroupChat")
    public JsonResult<List<GroupChatListVo>> listGroupChat() {
        return JsonResult.success(groupChatService.listGroupChat(SecurityUtils.getUsername()));
    }

    @PostMapping("/deleteChatUser")
    public JsonResult<String> deleteChatUser(@RequestParam String groupNo) {
        boolean deleteChat = groupChatService.userDeleteChat(groupNo, SecurityUtils.getUsername());
        return JsonResult.success(deleteChat ? "成功" : "失败");
    }

    @PostMapping("/inviteUser")
    public JsonResult<String> inviteUser(@RequestBody InviteUserVo inviteUserVo) {
        boolean inviteUser = groupChatService.inviteUser(inviteUserVo);
        return JsonResult.success(inviteUser ? "成功" : "失败");
    }
}
