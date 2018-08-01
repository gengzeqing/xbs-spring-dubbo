package com.xuebusi.xbs.cache.aop;

import com.xuebusi.xbs.cache.exception.CacheException;
import com.xuebusi.xbs.cache.rule.RedisKeyRule;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopRedisRuleAspect {

	/**
	 * 在进入Service方法之前执行
	 * 
	 * @param joinPoint
	 *            切面对象
	 * @throws CacheException
	 */
	/*
	 * @Before("execution(* com.xuebusi.*..*support..*.*(..))") public void
	 * before(JoinPoint point) throws CacheException { Object key =
	 * point.getArgs()[0]; boolean isKeyRule = RedisKeyRule.isKeyRule(key);
	 * if(isKeyRule == Boolean.FALSE){ System.out.println("JoinPoint - value");
	 * throw new
	 * CacheException("redis Key is not effective,key be similar to [object-model-key]"
	 * ); } }
	 */

	@Before("execution(* com.xuebusi.*..*support..*.*(..))")
	// @Before(value = "com.xuebusi.xbs.cache.support.RedisOpsString(..)")
	public void before(JoinPoint joinPoint) throws CacheException {
		Object[] keys = joinPoint.getArgs();
		if (null != keys && keys.length > 0) {
			Object key = joinPoint.getArgs()[0];
			boolean isKeyRule = RedisKeyRule.isKeyRule(key);
			if (isKeyRule == Boolean.FALSE) {
				CacheException ex = new CacheException("redis Key is not effective,key be similar to [object-model-key]");
				ex.printStackTrace();
				throw ex;
			}
		}
	}
}