package com.tomhusky.wechatmvc.server;

import com.tomhusky.wechatmvc.server.service.base.GroupChatService;
import com.tomhusky.wechatmvc.server.service.base.ImageService;
import com.tomhusky.wechatmvc.server.vo.query.GroupUserDetail;
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

}
