package io.github.tomhusky.wechatmvc.server.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * redis配置
 *
 * @author lwj
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean(name = "objectRedisTemplate")
    public <M, T> RedisTemplate<M, T> masterObjectRedisTemplate(
            @Value("${spring.redis.host}") String hostName,
            @Value("${spring.redis.password:}") String password,
            @Value("${spring.redis.port}") int port,
            @Value("${spring.redis.database}") int database,
            @Value("${spring.redis.timeout:PT30S}") Duration timeout) {
        // 建立Redis的连接
        RedisConnectionFactory factory = this.getRedisConnectionFactory(hostName, password, port, database, timeout);
        FastJson2JsonRedisSerializer<Object> fastJson2JsonRedisSerializer = new FastJson2JsonRedisSerializer<>(Object.class);
        RedisTemplate<M, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJson2JsonRedisSerializer);
        redisTemplate.setDefaultSerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Bean("stringRedisTemplate")
    public StringRedisTemplate masterRedisStringTemplate(
            @Value("${spring.redis.host}") String hostName,
            @Value("${spring.redis.password:}") String password,
            @Value("${spring.redis.port}") int port,
            @Value("${spring.redis.database}") int database,
            @Value("${spring.redis.timeout:PT30S}") Duration timeout) {
        // 建立Redis的连接
        RedisConnectionFactory factory = this.getRedisConnectionFactory(hostName, password, port, database, timeout);
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        return redisTemplate;
    }

    public RedisConnectionFactory getRedisConnectionFactory(String hostName, String password, int port, int database, Duration timeout) {
        //redis连接配置
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        //设置连接的ip
        redisStandaloneConfiguration.setHostName(hostName);
        if (StrUtil.isNotBlank(password)) {
            redisStandaloneConfiguration.setPassword(password);
        }
        if (database != 0) {
            redisStandaloneConfiguration.setDatabase(database);
        }
        //端口号
        redisStandaloneConfiguration.setPort(port);
        //JedisConnectionFactory配置jedisPoolConfig
        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        //客户端超时时间单位是毫秒
        jedisClientConfiguration.connectTimeout(timeout);
        //工厂对象
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration.build());
    }
}
