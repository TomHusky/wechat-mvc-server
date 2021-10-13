package com.tomhusky.wechatmvc.server.controller;

import com.tomhusky.wechatmvc.server.common.JsonResult;
import com.tomhusky.wechatmvc.server.service.GroupChatService;
import com.tomhusky.wechatmvc.server.vo.add.CreateGroupChatVo;
import com.tomhusky.wechatmvc.server.vo.query.GroupChatListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

}
