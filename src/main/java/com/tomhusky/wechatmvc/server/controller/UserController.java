package com.tomhusky.wechatmvc.server.controller;

import com.tomhusky.wechatmvc.server.common.JsonResult;
import com.tomhusky.wechatmvc.server.service.GroupChatService;
import com.tomhusky.wechatmvc.server.service.UserRelationService;
import com.tomhusky.wechatmvc.server.vo.query.FriendListVo;
import com.tomhusky.wechatmvc.server.vo.query.GroupChatListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户接口
 *
 * @author luowj
 * @since 2021/9/15 11:31
 */
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserRelationService userRelationService;

    @Autowired
    private GroupChatService groupChatService;

    /**
     * 获取用户好友列表信息
     *
     * @param username 用户名
     */
    @GetMapping("/listFriendInfo")
    public JsonResult<List<FriendListVo>> listFriendInfo(@RequestParam String username) {
        return JsonResult.success(userRelationService.listAllFriendInfo(username));
    }

    /**
     * 获取用户群聊列表信息
     *
     * @param username 用户名
     */
    @GetMapping("/listChatInfo")
    public JsonResult<List<GroupChatListVo>> listChatInfo(@RequestParam String username) {
        return JsonResult.success(groupChatService.listGroupChat(username));
    }
}
