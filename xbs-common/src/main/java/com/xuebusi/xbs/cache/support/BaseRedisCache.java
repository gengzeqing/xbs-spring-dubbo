package com.xuebusi.xbs.cache.support;

import com.xuebusi.xbs.cache.RedisGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

@Service
public class BaseRedisCache {

    @Autowired
    protected RedisGeneric redisGeneric;

    public int timeOut() {
    	return redisGeneric.getTimeout();
    }

    public JedisCluster getRedisDao() {

        return redisGeneric.getJedisCluster();
    }

    /**
     * 当前 key 的值自增1
     * 如果返回1 则表示当前 key 之前没有初始值
     * @param key
     * @return
     */
    public Long incr(String key){
        return getRedisDao().incr(key);
    }

    /**
     * 当前 key 的值 +=  value
     * @param key
     * @param value
     * @return
     */
    public Long incr(String key, Long value) {
        return getRedisDao().incrBy(key, value);
    }

}
