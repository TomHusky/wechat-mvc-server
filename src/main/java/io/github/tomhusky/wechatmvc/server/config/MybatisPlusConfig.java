package io.github.tomhusky.wechatmvc.server.config;


import io.github.tomhusky.wechatmvc.server.config.injector.MySqlInjector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("io.github.tomhusky.wechatmvc.server.mapper")
public class MybatisPlusConfig {

    @Bean
    public MySqlInjector sqlInjector() {
        return new MySqlInjector();
    }
}
