package io.github.tomhusky.wechatmvc.server.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 *
 * @author lwj
 **/

@Component
public class RedisCache<M, T> {

    @Qualifier("objectRedisTemplate")
    @Autowired
    public RedisTemplate<M, T> redisTemplate;

    /**
     * 判断是否存在此key
     *
     * @param key 缓存的键值
     * @return java.lang.boolean
     */
    public Boolean hasKey(M key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public ValueOperations<M, T> setCacheObject(M key, T value) {
        ValueOperations<M, T> operation = redisTemplate.opsForValue();
        operation.set(key, value);
        return operation;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     * @return 缓存的对象
     */
    public ValueOperations<M, T> setCacheObject(M key, T value, Integer timeout, TimeUnit timeUnit) {
        ValueOperations<M, T> operation = redisTemplate.opsForValue();
        operation.set(key, value, timeout, timeUnit);
        return operation;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public T getCacheObject(M key) {
        ValueOperations<M, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     */
    public void deleteObject(M key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     */
    public void deleteObject(Collection<M> collection) {
        redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public ListOperations<M, T> setCacheList(M key, List<T> dataList) {
        ListOperations<M, T> listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                listOperation.leftPush(key, dataList.get(i));
            }
        }
        return listOperation;
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public ListOperations<M, T> replaceCacheList(M key, List<T> dataList) {
        if (Boolean.TRUE.equals(hasKey(key))) {
            deleteObject(key);
        }
        return setCacheList(key, dataList);
    }


    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public List<T> getCacheList(M key) {
        List<T> dataList = new ArrayList<>();
        ListOperations<M, T> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);
        for (int i = 0; i < size; i++) {
            dataList.add(listOperation.index(key, i));
        }
        return dataList;
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public BoundSetOperations<M, T> setCacheSet(M key, Set<T> dataSet) {
        BoundSetOperations<M, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public Set<T> getCacheSet(M key) {
        BoundSetOperations<M, T> operation = redisTemplate.boundSetOps(key);
        return operation.members();
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public Map<Object, Object> getCacheMap(M key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<M> keys(M pattern) {
        return redisTemplate.keys(pattern);
    }
}
