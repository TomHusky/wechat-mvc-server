package io.github.tomhusky.wechatmvc.server.common.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * <p>
 * 随机验证码工具类
 * <p/>
 *
 * @author lwj
 * @version 1.0
 * @since 2021/10/12 16:38
 */
public class VerCodeGenerateUtil {

    private VerCodeGenerateUtil() {

    }

    private static final String SYMBOLS = "0123456789";

    private static final Random RANDOM = new SecureRandom();

    /**
     * 生成6位随机数字
     *
     * @return 返回6位数字验证码
     */
    public static String generateVerCode() {
        char[] nonceChars = new char[6];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }
}
