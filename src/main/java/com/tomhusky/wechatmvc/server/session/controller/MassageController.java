package com.tomhusky.wechatmvc.server.session.controller;

import cn.hutool.core.bean.BeanUtil;
import com.tomhusky.wechatmvc.server.common.JsonResult;
import com.tomhusky.wechatmvc.server.common.enums.MsgType;
import com.tomhusky.wechatmvc.server.session.OnlineUserManage;
import com.tomhusky.wechatmvc.server.session.UserSessionDetail;
import com.tomhusky.wechatmvc.server.session.vo.ReceiveMsgVo;
import com.tomhusky.wechatmvc.server.session.vo.SendMsgVo;
import io.github.tomhusky.websocket.annotation.SocketController;
import io.github.tomhusky.websocket.annotation.SocketRequestMapping;
import io.github.tomhusky.websocket.context.WebSocketContext;
import io.github.tomhusky.websocket.context.WebSocketContextHolder;

/**
 * <p>
 * 消息接口
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/9/27 17:29
 */
@SocketRequestMapping("/msg")
@SocketController
public class MassageController {

    @SocketRequestMapping("/send")
    public JsonResult<String> sendMsg(SendMsgVo sendMsgVo) {
        WebSocketContext context = WebSocketContextHolder.getContext();
        UserSessionDetail sessionDetail = (UserSessionDetail) context.getSessionDetail();
        // TODO 保存消息
        ReceiveMsgVo receiveMsgVo = BeanUtil.copyProperties(sendMsgVo, ReceiveMsgVo.class);
        receiveMsgVo.setUsername(sessionDetail.getUsername());
        if (MsgType.FRIEND.getValue() == sendMsgVo.getMsgType()) {
            OnlineUserManage.sendMessages(sendMsgVo.getReceiveId(), JsonResult.success(receiveMsgVo));
        }
        return JsonResult.success("ok");
    }
}
