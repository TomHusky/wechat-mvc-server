package io.github.tomhusky.wechatmvc.server.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 *
 * @author lwj
 **/

@Component
public class RedisStringCache {

    @Qualifier("stringRedisTemplate")
    @Autowired
    public StringRedisTemplate redisTemplate;

    /**
     * 判断是否存在此key
     *
     * @paraString key 缓存的键值
     * @return java.lang.boolean
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @paraString key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public ValueOperations<String, String> setCacheObject(String key, String value) {
        ValueOperations<String, String> operation = redisTemplate.opsForValue();
        operation.set(key, value);
        return operation;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @paraString key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     * @return 缓存的对象
     */
    public ValueOperations<String, String> setCacheObject(String key, String value, Integer timeout, TimeUnit timeUnit) {
        ValueOperations<String, String> operation = redisTemplate.opsForValue();
        operation.set(key, value, timeout, timeUnit);
        return operation;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @paraString key 缓存键值
     * @return 缓存键值对应的数据
     */
    public String getCacheObject(String key) {
        ValueOperations<String, String> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     */
    public void deleteObject(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     */
    public void deleteObject(Collection<String> collection) {
        redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @paraString key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public ListOperations<String, String> setCacheList(String key, List<String> dataList) {
        ListOperations<String, String> listOperation = redisTemplate.opsForList();
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
     * @paraString key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public ListOperations<String, String> replaceCacheList(String key, List<String> dataList) {
        if (Boolean.TRUE.equals(hasKey(key))) {
            deleteObject(key);
        }
        return setCacheList(key, dataList);
    }


    /**
     * 获得缓存的list对象
     *
     * @paraString key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public List<String> getCacheList(String key) {
        List<String> dataList = new ArrayList<>();
        ListOperations<String, String> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);
        for (int i = 0; i < size; i++) {
            dataList.add(listOperation.index(key, i));
        }
        return dataList;
    }

    /**
     * 缓存Set
     *
     * @paraString key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public BoundSetOperations<String, String> setCacheSet(String key, Set<String> dataSet) {
        BoundSetOperations<String, String> setOperation = redisTemplate.boundSetOps(key);
        Iterator<String> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @paraString key
     * @return
     */
    public Set<String> getCacheSet(String key) {
        BoundSetOperations<String, String> operation = redisTemplate.boundSetOps(key);
        return operation.members();
    }

    /**
     * 获得缓存的Map
     *
     * @paraString key
     * @return
     */
    public Map<Object, Object> getCacheMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
