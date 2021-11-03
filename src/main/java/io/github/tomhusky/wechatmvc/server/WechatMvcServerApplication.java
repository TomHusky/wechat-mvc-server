package io.github.tomhusky.wechatmvc.server;

import io.github.tomhusky.wechatmvc.server.common.util.SpringContextUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lwj
 */
@SpringBootApplication
@MapperScan("io.github.tomhusky.wechatmvc.server.mapper")
public class WechatMvcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatMvcServerApplication.class, args);
        String activeProfile = SpringContextUtils.getActiveProfile();
        System.out.println("**********[启动环境：" + activeProfile + "]**********");
    }

}
