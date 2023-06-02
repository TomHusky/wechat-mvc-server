package io.github.tomhusky.wechatmvc.server.service.base.impl;

import io.github.tomhusky.wechatmvc.server.common.constant.RedisCacheName;
import io.github.tomhusky.wechatmvc.server.common.util.RedisStringCache;
import io.github.tomhusky.wechatmvc.server.common.util.VerCodeGenerateUtil;
import io.github.tomhusky.wechatmvc.server.service.base.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 邮件服务接口实现类
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/12 16:32
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private RedisStringCache emailCache;

    public static final String CACHE_NAME = RedisCacheName.EMAIL_CODE;

    @Override
    public void sendEmailVerCode(String receiver, String verCode) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        message.setSubject("YouChat验证码");
        message.setText("尊敬的用户,您好:<br/>"
                + "<br/>本次请求的邮件验证码为:<H2 style=\"color:#3390FF;\"><br/>" + verCode + "</H2><br/>本验证码5分钟内有效，请及时输入。（请勿泄露此验证码）<br/>"
                + "<br/>如非本人操作，请忽略该邮件。<br/>(这是一封自动发送的邮件，请不要直接回复）", true);
        message.setTo(receiver);
        message.setFrom(sender);
        mailSender.send(message.getMimeMessage());
    }

    @Override
    public boolean sendEmailVerCode(String receiver) {
        String code = VerCodeGenerateUtil.generateVerCode();
        try {
            sendEmailVerCode(receiver, code);
            emailCache.setCacheObject(CACHE_NAME + receiver, code, 5, TimeUnit.MINUTES);
            return true;
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean validCode(String receiver, String verCode) {
        String cacheObject = emailCache.getCacheObject(CACHE_NAME + receiver);
        if (cacheObject == null) {
            return false;
        }
        boolean equals = verCode.equals(cacheObject);
        if (equals) {
            emailCache.deleteObject(CACHE_NAME + receiver);
        }
        return equals;
    }

}
