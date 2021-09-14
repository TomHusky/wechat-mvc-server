package com.tomhusky.wechatmvc.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tomhusky.wechatmvc.server.mapper")
public class WechatMvcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatMvcServerApplication.class, args);
    }

}
