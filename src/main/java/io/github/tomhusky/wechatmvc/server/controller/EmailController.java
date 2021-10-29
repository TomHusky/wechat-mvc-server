package io.github.tomhusky.wechatmvc.server.controller;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import io.github.tomhusky.wechatmvc.server.common.JsonResult;
import io.github.tomhusky.wechatmvc.server.service.base.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 邮件接口
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/12 16:39
 */
@Slf4j
@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    private static final TimedCache<String, String> CODE_CACHE = CacheUtil.newTimedCache(60 * 1000L);

    @PostMapping("/sendVerCode")
    public JsonResult sendEmail(@RequestParam("emailAddress") String emailAddress) {

        if (CODE_CACHE.containsKey(emailAddress)) {
            return JsonResult.fail("请求太频繁，请稍后重试！");
        }
        try {
            boolean send = emailService.sendEmailVerCode(emailAddress);
            if (send) {
                return JsonResult.fail("邮件发送成功");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return JsonResult.fail("邮件发送失败");
    }
}
