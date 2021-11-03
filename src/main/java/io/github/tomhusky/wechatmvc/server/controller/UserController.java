package io.github.tomhusky.wechatmvc.server.controller;

import cn.hutool.core.bean.BeanUtil;
import io.github.tomhusky.wechatmvc.server.entity.User;
import io.github.tomhusky.wechatmvc.server.service.base.GroupChatService;
import io.github.tomhusky.wechatmvc.server.common.JsonResult;
import io.github.tomhusky.wechatmvc.server.service.base.UserRelationService;
import io.github.tomhusky.wechatmvc.server.service.base.UserService;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupChatListVo;
import io.github.tomhusky.wechatmvc.server.vo.query.SelectUserVo;
import io.github.tomhusky.wechatmvc.server.vo.update.UpdateFriendVo;
import io.github.tomhusky.wechatmvc.server.vo.update.UpdateUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private UserService userService;

    /**
     * 获取用户群聊列表信息
     *
     * @param username 用户名
     */
    @GetMapping("/listChatInfo")
    public JsonResult<List<GroupChatListVo>> listChatInfo(@RequestParam String username) {
        return JsonResult.success(groupChatService.listGroupChat(username));
    }

    /**
     * 搜索用户
     *
     * @param value 账号或者wxid
     */
    @GetMapping("/selectUser")
    public JsonResult<SelectUserVo> selectUser(@RequestParam String value) {
        return JsonResult.success(userService.selectUser(value));
    }


    /**
     * 更新用户信息
     */
    @PostMapping("/updateInfo")
    public JsonResult<Boolean> updateFriendInfo(@RequestBody UpdateUserInfoVo userVo) {
        User user = BeanUtil.copyProperties(userVo, User.class);
        return JsonResult.success(userService.editUserByUsername(user));
    }

}
