package com.xuebusi.provide.launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StandardProjectServiceLauncher {
	private static final Logger logger = LoggerFactory.getLogger(StandardProjectServiceLauncher.class);
    @SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
    	logger.info("启动");
    	new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    	logger.info("启动完成");
    	synchronized (StandardProjectServiceLauncher.class) {
			while (true) {
				try {
					StandardProjectServiceLauncher.class.wait();
				} catch (InterruptedException e) {
					logger.error("后台服务异常终止:" + e.getMessage(), e);
				}
			}
		}
    }
}