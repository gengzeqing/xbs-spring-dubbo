package com.xuebusi.xbs.cache.exception;

/**
 * 各种请求参数不匹配导致的异常
 */
public class CacheException extends Exception {

	private static final long serialVersionUID = 410918530519191826L;

	public CacheException(){
		super();
	}
	
	public CacheException(String message) {
		super(message);
	}

	public CacheException(Throwable cause) {
		super(cause);
	}

	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}
}
