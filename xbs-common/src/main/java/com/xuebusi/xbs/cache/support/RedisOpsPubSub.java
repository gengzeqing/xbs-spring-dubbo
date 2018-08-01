package com.xuebusi.xbs.cache.support;

import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPubSub;

/**
 * Redis结果之-订阅发布服务类
 */
@Service
public class RedisOpsPubSub extends BaseRedisCache {

    /**
     * 订阅消息
     * @param channel
     * @param pubSub
     */
    public void subscriber(String channel, JedisPubSub pubSub) {
        redisGeneric.getJedisCluster().subscriber(channel, pubSub);
    }

    /**
     * 发布消息
     * @param channel
     * @param value
     * @return
     */
    public Long published(String channel,String value){
       return redisGeneric.getJedisCluster().published(channel, value);
    }
}
