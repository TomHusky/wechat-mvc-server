package io.github.tomhusky.wechatmvc.server.controller;

import io.github.tomhusky.wechatmvc.server.service.base.GroupChatService;
import io.github.tomhusky.wechatmvc.server.common.JsonResult;
import io.github.tomhusky.wechatmvc.server.security.SecurityUtils;
import io.github.tomhusky.wechatmvc.server.vo.add.CreateGroupChatVo;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupChatListVo;
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

    /**
     * 获取用户群聊列表信息
     */
    @GetMapping("/listGroupChat")
    public JsonResult<List<GroupChatListVo>> listGroupChat() {
        return JsonResult.success(groupChatService.listGroupChat(SecurityUtils.getUsername()));
    }


}
