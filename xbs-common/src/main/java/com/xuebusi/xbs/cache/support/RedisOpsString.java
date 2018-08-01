package com.xuebusi.xbs.cache.support;

import com.xuebusi.xbs.cache.RedisGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Redis结果之-String数据类型操作
 */
@Service
public class RedisOpsString extends BaseRedisCache {

	@Autowired
	private RedisGeneric redisGeneric;
	
	
	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 * @return boolean
	 */
	public boolean exists(String key) {
		boolean exis = redisGeneric.getJedisCluster().exists(key);
        return exis;
	}

    /**
     * 返回1 代表插入成功
     * 返回0 代表插入失败
     * @param key
     * @param value
     * @return
     */
    public boolean setnx(String key ,String value) {
        long result = redisGeneric.getJedisCluster().setnx(key, value);
        return result == 1l ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 
     * @param key
     * @param value
     * @return
     */
    public String getSet(String key,String value){
        return redisGeneric.getJedisCluster().getSet(key, value);
    }

	/**
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value) {
		return redisGeneric.getJedisCluster().setex(key,super.timeOut(),value);
	}
	
	/**
	 * 设置key指定过期时间
	 * @param key
	 * @param value
	 * @param expire 过期时间（单位秒）
	 * @return
	 */
	public String set(String key, String value, int expire) {
		return redisGeneric.getJedisCluster().setex(key, expire, value);
	}
	
	/**
	 * 根据key获取对应的值
	 * @param key
	 * @return
	 */
	public String get(String key){
		return redisGeneric.getJedisCluster().get(key);
	}
	
	/**
	 * 追加值
	 * @param key
	 * @param value
	 * @return
	 */
	public long append(String key, String value){
		return redisGeneric.getJedisCluster().append(key, value);
	}
	
	/**
	 * 截取得出的子字符串
	 * @param key
	 * @param start(包含)
	 * @param end(包含)
	 * @return 获取key中字符串值的子字符串
	 */
	public String getRange(String key, long start, long end){
		return redisGeneric.getJedisCluster().getrange(key, start, end);
	}

    /**
     * 模糊获取 key
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisGeneric.getJedisCluster().hkeys(pattern);
    }

	/**
	 * 获取字符串长度
	 * @param key
	 * @return
	 */
	public long strlen(String key){
		return redisGeneric.getJedisCluster().strlen(key);
	}

	/**
	 * 指定Key的失效时间
	 * @throws
	 */
	public long expire(String key,int seconds){
		return redisGeneric.getJedisCluster().expire(key, seconds);
	}
	
	/**
	 * 指定Key的失效时间
	 * @throws
	 */
	public long expire(byte[] key,int seconds){
		return redisGeneric.getJedisCluster().expire(key, seconds);
	}
	
	/**
	 * 删除指定Key
	 * @throws
	 */
	public long deleteKey(String key){
		return redisGeneric.getJedisCluster().del(key);
	}
	
	public long deleteKey(byte[] key){
		return redisGeneric.getJedisCluster().delObject(key);
	}
	
	/**
	 * 基于byte存储Key
	 * @throws
	 */
	public String setObject(byte[] key,byte[] value){
		return redisGeneric.getJedisCluster().setObject(key, value);
	}
	
	/**
	 * 根据Byte的key获得byte数据
	 * @throws
	 */
	public byte[] getObject(byte[] key){
		return redisGeneric.getJedisCluster().getObject(key);
	}
	
	/**
	 * 根据Byte的key删除数据
	 * @throws
	 */
	public Long deleteObject(byte[] key){
		return redisGeneric.getJedisCluster().delObject(key);
	}
	
	/**
	 * 查询redis的Key过期时间
	 * @param key
	 * @throws
	 */
	public long ttl(String key) {
		return redisGeneric.getJedisCluster().ttl(key);
	}
	
	/**
	 * 查询redis的Key过期时间
	 * 当 key 不存在时，返回 -2 。
                     当 key 存在但没有设置剩余生存时间时，返回 -1 。否则，以毫秒为单位，返回 key 的剩余生存时间。
	 * @param key
	 * @throws
	 */
	public long pttl(String key) {
		return redisGeneric.getJedisCluster().pttl(key);
	}
}
