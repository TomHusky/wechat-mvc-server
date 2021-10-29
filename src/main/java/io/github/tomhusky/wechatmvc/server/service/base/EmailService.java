package io.github.tomhusky.wechatmvc.server.service.base;

import javax.mail.MessagingException;

/**
 * <p>
 * 邮件服务接口
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/12 16:31
 */
public interface EmailService {
    /**
     * 发送邮件验证码
     *
     * @param receiver 收件人
     * @param verCode  验证码
     * @return void
     */
    void sendEmailVerCode(String receiver, String verCode) throws MessagingException;

    /**
     * 发送邮件验证码
     *
     * @param receiver 收件人
     * @return boolean
     */
    boolean sendEmailVerCode(String receiver);

    /**
     * 校验验证码
     *
     * @param receiver 收件人
     * @param verCode  验证码
     * @return boolean
     */
    boolean validCode(String receiver, String verCode);
}