/**
 * 
 */
package com.xuebusi.xbs.util.uuid;

import java.net.InetAddress;

/**
*    
* 项目名称：framework-common   
* 类名称：UUIDGenerator   
* 类描述：   唯一主键生成办法。从Hibernate中提取出来
* 创建人：Administrator   
* 创建时间：2015年2月9日 上午10:59:37   
* 修改人：Administrator   
* 修改时间：2015年2月9日 上午10:59:37   
* 修改备注：   
* @version    
*
 */
public class UUIDGenerator {
	@SuppressWarnings("unused")
	private String name;
	private static final int IP;
	private final static String sep = "";

	public static int IptoInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	static {
		int ipadd;
		try {
			ipadd = IptoInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	private  short counter = (short) 0;
	private  final int JVM = (int) (System.currentTimeMillis() >>> 8);

	public UUIDGenerator(String name) {
		this.name = name;
	}

	/**
	 * Unique across JVMs on this machine (unless they load this class in the
	 * same quater second - very unlikely)
	 */
	protected  int getJVM() {
		return JVM;
	}

	/**
	 * Unique in a millisecond for this JVM instance (unless there are >
	 * Short.MAX_VALUE instances created in a millisecond)
	 */
	protected  short getCount() {
		synchronized (UUIDGenerator.class) {
			if (counter < 0)
				counter = 0;
			return counter++;
		}
	}

	/**
	 * Unique in a local network
	 */
	protected  int getIP() {
		return IP;
	}

	/**
	 * Unique down to millisecond
	 */
	protected  short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	protected  int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	protected  String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected  String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	public synchronized  String generate() {
		return new StringBuffer(36).append(format(getIP())).append(sep)
				.append(format(getJVM())).append(sep)
				.append(format(getHiTime())).append(sep)
				.append(format(getLoTime())).append(sep)
				.append(format(getCount())).toString();
	}

	public static void main(String[] args){
		UUIDGenerator uuid = new UUIDGenerator("ok");
		for(int i=0;i<100;i++){
			System.out.println(uuid.generate());
		}
	}
}