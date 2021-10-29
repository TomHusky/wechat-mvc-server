package io.github.tomhusky.wechatmvc.server.common.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Created by YanXingLiang on 2018/12/25.
 */
@Component
@Slf4j
public class RedisLock {

    /**
     * 获取锁的value，判断是否等于期待的value，满足的话，则删除该锁，并返回1；否则直接返回-1。
     */
    private static final DefaultRedisScript<Long> UNLOCK_LUA_SCRIPT = new DefaultRedisScript<>(
            "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return -1 end", Long.class
    );

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     *
     * @param key   - 唯一标志
     * @param value 当前时间+超时时间 也就是时间戳
     * @return
     */
    public boolean lock(String key, String value) {
        //对应setnx命令
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            //可以成功设置,也就是key不存在
            return true;
        }
        //判断锁超时 - 防止原来的操作异常，没有运行解锁操作  防止死锁
        String currentValue = redisTemplate.opsForValue().get(key);
        //如果锁过期
        //currentValue不为空且小于当前时间
        if (StrUtil.isNotEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //获取上一个锁的时间value,//对应getset，如果key存在
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);

            //假设两个线程同时进来这里，因为key被占用了，而且锁过期了。获取的值currentValue=A(get取的旧的值肯定是一样的),两个线程的value都是B,key都是K.锁时间已经过期了。
            //而这里面的getAndSet一次只会一个执行，也就是一个执行之后，上一个的value已经变成了B。只有一个线程获取的上一个值会是A，另一个线程拿到的值是B。
            if (StrUtil.isNotEmpty(oldValue) && oldValue.equals(currentValue)) {
                //oldValue不为空且oldValue等于currentValue，也就是校验是不是上个对应的商品时间戳，也是防止并发
                return true;
            }
        }
        return false;
    }

    public boolean tryLockTime(String key, String value, long expireTime, TimeUnit timeUnit) {
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, timeUnit);
        if (flag == null || !flag) {
            log.error("申请锁(" + key + "," + value + ")失败");
            return false;
        }
        return true;
    }

    /**
     * 解锁
     *
     * @param key   键
     * @param value 值
     */
    public void unlock(String key, String value) {
        Long result = redisTemplate.execute(UNLOCK_LUA_SCRIPT, Collections.singletonList(key), value);
        if (result == -1) {
            log.error("释放锁(" + key + "," + value + ")失败,该锁不存在或锁已经过期");
        }
    }
}
