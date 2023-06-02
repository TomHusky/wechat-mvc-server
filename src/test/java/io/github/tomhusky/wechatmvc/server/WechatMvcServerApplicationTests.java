package io.github.tomhusky.wechatmvc.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.tomhusky.websocket.bean.SocketRequest;
import io.github.tomhusky.wechatmvc.server.service.base.GroupChatService;
import io.github.tomhusky.wechatmvc.server.service.base.ImageService;
import io.github.tomhusky.wechatmvc.server.vo.query.GroupUserDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class WechatMvcServerApplicationTests {

    @Autowired
    private GroupChatService groupChatService;

    @Autowired
    private ImageService imageService;

    @Test
    void contextLoads() {
        List<GroupUserDetail> groupUserDetails = groupChatService.listGroupChatAllUser("ElrUBjj_Jt2438");
        List<String> avatars = new ArrayList<>();
        for (GroupUserDetail u : groupUserDetails) {
            avatars.add(u.getAvatar());
        }
        String elrUBjj_jt2438 = imageService.getGroupAvatar(avatars, "ElrUBjj_Jt2438");
        System.out.println(elrUBjj_jt2438);
    }

    @Test
    void testJson() throws JsonProcessingException {
        String str = "{\"url\":\"/chat/send\",\"body\":{\"receiveId\":\"1677900582\",\"msgContent\":\"2121\",\"msgType\":1,\"contentType\":1,\"sendTime\":1675135331420,\"msgId\":\"16779005821675135331420\"}}";

        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(objectMapper.readValue(str, SocketRequest.class));
    }

}
