package io.github.tomhusky.wechatmvc.server.session.controller;

import cn.hutool.core.bean.BeanUtil;
import io.github.tomhusky.wechatmvc.server.common.JsonResult;
import io.github.tomhusky.wechatmvc.server.common.enums.MsgUrlType;
import io.github.tomhusky.wechatmvc.server.service.base.GroupChatService;
import io.github.tomhusky.wechatmvc.server.session.OnlineUserManage;
import io.github.tomhusky.wechatmvc.server.session.UserSessionDetail;
import io.github.tomhusky.wechatmvc.server.common.enums.MsgType;
import io.github.tomhusky.wechatmvc.server.session.vo.ReceiveMsgVo;
import io.github.tomhusky.wechatmvc.server.session.vo.SendMsgVo;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupUserDetail;
import io.github.tomhusky.websocket.annotation.SocketController;
import io.github.tomhusky.websocket.annotation.SocketRequestMapping;
import io.github.tomhusky.websocket.context.WebSocketContext;
import io.github.tomhusky.websocket.context.WebSocketContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 消息接口
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/9/27 17:29
 */
@SocketRequestMapping("/chat")
@SocketController
public class MassageController {

    @Autowired
    private GroupChatService groupChatService;

    @SocketRequestMapping("/send")
    public JsonResult<String> sendMsg(@Valid SendMsgVo sendMsgVo) {
        WebSocketContext context = WebSocketContextHolder.getContext();
        UserSessionDetail sessionDetail = (UserSessionDetail) context.getSessionDetail();
        if (MsgType.FRIEND.getValue() == sendMsgVo.getMsgType()) {
            sendFriendMsg(sessionDetail, sendMsgVo);
        } else if (MsgType.GROUP.getValue() == sendMsgVo.getMsgType()) {
            sendGroupChatMsg(sessionDetail, sendMsgVo);
        }
        return JsonResult.success("ok");
    }

    public void sendFriendMsg(UserSessionDetail sessionDetail, SendMsgVo sendMsgVo) {
        // TODO 保存消息
        ReceiveMsgVo receiveMsgVo = BeanUtil.copyProperties(sendMsgVo, ReceiveMsgVo.class);
        receiveMsgVo.setSendId(sessionDetail.getUsername());
        receiveMsgVo.setUsername(sessionDetail.getUsername());
        OnlineUserManage.sendMessages(MsgUrlType.CHAT_MSG.getUrl(), sendMsgVo.getReceiveId(), JsonResult.success(receiveMsgVo));

    }

    public void sendGroupChatMsg(UserSessionDetail sessionDetail, SendMsgVo sendMsgVo) {
        // TODO 保存消息
        List<GroupUserDetail> groupUserDetails = groupChatService.listGroupChatAllUser(sendMsgVo.getReceiveId());
        for (GroupUserDetail groupUserDetail : groupUserDetails) {
            if (groupUserDetail.getUsername().equals(sessionDetail.getUsername())) {
                continue;
            }
            ReceiveMsgVo receiveMsgVo = BeanUtil.copyProperties(sendMsgVo, ReceiveMsgVo.class);
            receiveMsgVo.setSendId(sendMsgVo.getReceiveId());
            receiveMsgVo.setUsername(sessionDetail.getUsername());
            OnlineUserManage.sendMessages(MsgUrlType.CHAT_MSG.getUrl(), groupUserDetail.getUsername(), JsonResult.success(receiveMsgVo));
        }
    }
}
