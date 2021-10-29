package io.github.tomhusky.wechatmvc.server.controller;

import io.github.tomhusky.wechatmvc.server.common.JsonResult;
import io.github.tomhusky.wechatmvc.server.service.base.FriendApplyService;
import io.github.tomhusky.wechatmvc.server.service.base.UserRelationService;
import io.github.tomhusky.wechatmvc.server.vo.add.AddFriendVo;
import io.github.tomhusky.wechatmvc.server.vo.query.FriendListVo;
import io.github.tomhusky.wechatmvc.server.vo.update.FriendApplyUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 好友接口
 *
 * @author luowj
 * @since 2021/9/15 11:31
 */
@RequestMapping("friend")
@RestController
public class FriendController {

    @Autowired
    private UserRelationService userRelationService;

    @Autowired
    private FriendApplyService friendApplyService;

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
     * 更新好友信息
     */
    @PostMapping("/updateFriendInfo")
    public JsonResult<Boolean> updateFriendInfo(@RequestBody @Valid AddFriendVo userVo) {
        return JsonResult.success(friendApplyService.applyAddFriend(userVo));
    }

    /**
     * 申请添加好友
     */
    @PostMapping("/applyAddFriend")
    public JsonResult<Boolean> applyAddFriend(@RequestBody @Valid AddFriendVo userVo) {
        return JsonResult.success(friendApplyService.applyAddFriend(userVo));
    }

    /**
     * 处理申请
     */
    @PostMapping("/handleFriendApply")
    public JsonResult<Boolean> handleFriendApply(@RequestBody @Valid FriendApplyUpdate applyUpdate) {
        return JsonResult.success(friendApplyService.handleApply(applyUpdate));
    }

}
