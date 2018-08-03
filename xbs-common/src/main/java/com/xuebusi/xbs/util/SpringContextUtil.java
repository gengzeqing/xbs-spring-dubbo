package com.xuebusi.xbs.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.xuebusi.xbs.cache.RedisGeneric;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring容器管理
 */
public class SpringContextUtil implements ApplicationContextAware {

	/**
	 * Spring应用上下文环境
	 */
	private static ApplicationContext applicationContext;

	/**
	 * 
	 * 实现了ApplicationContextAware 接口，必须实现该方法；
	 * 
	 * 通过传递applicationContext参数初始化成员变量applicationContext
	 * 
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("========================applicationContext:" + applicationContext);
		RedisGeneric redisGeneric = applicationContext.getBean(RedisGeneric.class);
		System.out.println("========================redisGeneric:" + redisGeneric);
		DruidDataSource druidDataSource = applicationContext.getBean(DruidDataSource.class);
		System.out.println("========================druidDataSource:" + druidDataSource);
		if(SpringContextUtil.applicationContext == null) {
			SpringContextUtil.applicationContext=applicationContext;
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> clazz) throws BeansException {
		return (T)applicationContext.getBean(clazz);
	}

}