package com.xuebusi.xbs.common.beans;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Java Class工具类
 * Bean 对象间的Copy
 * @version V1.0.1
 * @date 2018年1月8日 下午5:33:51
 * @Description:
 */
public class ClassUtil<T> {
	public static boolean HandleNull;		//空值
	public static boolean HandlePrivate;	//私有
	public static boolean HandleStatic;	//静态
	public static boolean HandleFinal;	//不可变
	public static boolean HandleConst = HandleStatic && HandleFinal;	//常量，"static final"修饰

	/**
	 * 初始化参数
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月22日 下午12:02:48
	 * @Title: initHandle
	 * @Description:
	 */
	public static void InitHandle() {
		HandleNull = false;
		HandlePrivate = false;
		HandleStatic = false;
		HandleFinal = false;
		HandleConst = HandleStatic && HandleFinal;
	}

	/**
	 * 多个基类属性值对拷
	 * @param bases {@link List}<{@link T}>：对拷基类集合；
	 * @param c {@link Class}<{@link E}>：要拷贝的类；
	 * @return {@link List}<{@link E}>：被拷贝的实例化类。
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月10日 下午2:02:51
	 * @Title: CopyAttrs
	 * @Description:
	 */
	public static <T, E> List<E> CopyAttrs(List<T> bases, Class<E> c) {
		if (bases == null)
			return null;

		int sz = bases.size();
		List<E> result = new ArrayList<E>(sz);
		for (int i = 0; i < sz; i++) {
			T base = bases.get(i);
			E e = CopyAttr(base, c);
			result.add(e);
		}
		return result;
	}
	/**
	 * 属性值对拷
	 * @param base {@link Object}：对拷基类；
	 * @param c {@link Class}<{@link E}>：要拷贝的类；
	 * @return {@link E}：被拷贝的实例化类。
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月9日 下午6:15:26
	 * @Title: CopyAttr
	 * @Description:
	 */
	public static <T, E> E CopyAttr(T base, Class<E> c) {
		Object[] csa = CopysAttr(base, c);
		if (csa == null)
			return null;
		@SuppressWarnings("unchecked")
		E result = (E) csa[0];
		return result;
	}
	/**
	 * 属性值对拷
	 * @param base {@link Object}：对拷基类；
	 * @param cs {@link Class}<?>...：要拷贝的类组；
	 * @return {@link Object}[]：被拷贝的实例化类组。
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月8日 下午6:04:11
	 * @Title: CopysAttr
	 * @Description:
	 */
	@SuppressWarnings("unchecked")
	public static Object[] CopysAttr(Object base, Class<?>... cs) {
		if (base == null || cs == null || cs.length < 1)
			return null;

		int cLen = cs.length;
		Object[] results = new Object[cLen];
		for (int i = 0; i < cLen; i++) {
			try {
				results[i] = cs[i].newInstance();	//实例化
//				cs[i] = results[i].getClass();		//重置为实例化后的Class
			} catch (InstantiationException | IllegalAccessException e) {
//				log.error("提示：被拷贝Class，实例化异常。", e);
				continue;
			}
		}

		Class<? extends Object> bc = base.getClass();
		Field[] bfs = bc.getDeclaredFields();
		for (int i = 0; i < bfs.length; i++) {
			Field bf = bfs[i];
			bf.setAccessible(true);		//属性设置为可访问的
			Object v;					//base属性值
			try {
				v = bf.get(base);
				if (v == null && !HandleNull)
					continue;
			} catch (IllegalArgumentException | IllegalAccessException e) {
//				log.error("提示：获取基类属性[" + bf + "]值异常。", e);
				continue;
			}
			String bAttr = bf.getName();
			Class<?> bt = bf.getType();
			for (int j = 0; j < cLen; j++) {
				Object newc = results[j];
				if (newc != null)
				try {
					Field cf = cs[j].getDeclaredField(bAttr);	//如果不含此属性名称，则进入catch()
					{	//是否final属性（final修饰不做修改）
						int m = cf.getModifiers();
						if (Modifier.isFinal(m) && !HandleFinal)
							continue;
					}
					Class<?> ct = cf.getType();
					cf.setAccessible(true);
					if (v != null) {
						if (ct.isAssignableFrom(String.class)) {
							v = new String(v.toString());
						} else if (ct.isAssignableFrom(Integer.class) || ct.isAssignableFrom(int.class)) {
							v = new Integer(v.toString());
						} else if (ct.isAssignableFrom(Long.class) || ct.isAssignableFrom(long.class)) {
							v = new Long(v.toString());
						} else if (ct.isAssignableFrom(Double.class) || ct.isAssignableFrom(double.class)) {
							v = new Double(v.toString());
						} else if (ct.isAssignableFrom(Float.class) || ct.isAssignableFrom(float.class)) {
							v = new Float(v.toString());
						} else if (ct.isAssignableFrom(BigDecimal.class)) {
							v = new BigDecimal(v.toString());
						} else if (ct.isAssignableFrom(Boolean.class) || ct.isAssignableFrom(boolean.class)) {
							v = new Boolean(v.toString());
						} else if (ct.isAssignableFrom(Character.class) || ct.isAssignableFrom(char.class)) {
							v = new Character(v.toString().charAt(0));
						} else if (ct.isAssignableFrom(Byte.class) || ct.isAssignableFrom(byte.class)) {
							v = new Byte(v.toString());
						} else if (ct.isAssignableFrom(Short.class) || ct.isAssignableFrom(short.class)) {
							v = new Short(v.toString());
						} else if (ct.isAssignableFrom(bt)) {
							if (List.class.isAssignableFrom(bt)) {
								ParameterizedType cvt = (ParameterizedType) cf.getGenericType();	//获取Field的定义类型Type
								Type[] cvtAs = cvt.getActualTypeArguments();	//泛型类型组"<T, E, ...>"
/*								String tn = cvtAs[0].getTypeName();				//真实类型字符
								Class<?> cvc = Class.forName(tn);				//反射获取类型 */
								Class<?> cvc = (Class<?>) cvtAs[0];

								List<?> vs = (List<?>) v;
								List<Object> cvs = (List<Object>) v.getClass().newInstance();	//按基类base此属性的实例化类型，创建相同实例。错误方式：cf.get(newc);
								for (int k = 0, sz = vs.size(); k < sz; k++) {
									Object nc = CopyAttr(vs.get(k), cvc);
									cvs.add(nc);
								}
								v = cvs;
							}
						} else {
							v = CopyAttr(v, ct);
						}
					}
					cf.set(newc, v);
				} catch (Exception e) {
//					log.error("提示：被拷贝Class，赋值异常。", e);
					continue;
				}
			}
		}
		return results;
	}

	class TypeHandle implements ClassType {

		@Override
		public Class<Object> isObject() {
			return null;
		}

		@Override
		public Class<String> isString() {
			return null;
		}

		@Override
		public Class<Integer> isInt() {
			return null;
		}

		@Override
		public Class<Long> isLong() {
			return null;
		}

		@Override
		public Class<Double> isDouble() {
			return null;
		}

		@Override
		public Class<Float> isFloat() {
			return null;
		}

		@Override
		public Class<Byte> isByte() {
			return null;
		}

		@Override
		public Class<Short> isShort() {
			return null;
		}

		@Override
		public Class<Boolean> isBoolean() {
			return null;
		}

		@Override
		public Class<Character> isChar() {
			return null;
		}

		@Override
		public Class<BigDecimal> isBigDecimal() {
			return null;
		}

		@Override
		public Class<?> isOther() {
			return null;
		}
		
	}
}

/* ================================================== */

/**
 * 类型接口
 * @version V1.0.1
 * @author Haining.Liu
 * @date 2018年1月11日 下午3:24:36
 * @Description:
 */
interface ClassType {
	Class<?> type = null;
/*	public ClassType(Class<?> type) {
		this.type = type;
	}*/

	public default void exec() {
		this.preHandle();
		try {
			this.confirmType();
		} catch (Exception e) {
//			log.error("提示：Class类型断定处理异常。", e);
		}
		this.afterHandle();
	}
	/**
	 * 默认前置处理
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月11日 上午11:24:52
	 * @Title: preHandle
	 * @Description:
	 */
	default void preHandle() {
		return;
	}
	/**
	 * 默认后置处理
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月11日 上午11:25:07
	 * @Title: afterHandle
	 * @Description:
	 */
	default void afterHandle() {
		return;
	}
	/**
	 * 确定类型
	 * @return
	 * @throws Exception
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月18日 下午3:07:51
	 * @Title: confirmType
	 * @Description:
	 */
	default Class<?> confirmType() throws Exception {
		if (type == null)
			throw new NullPointerException("haven't class type.");

		if (type.isAssignableFrom(String.class)) {
			return this.isString();
		} else if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
			return this.isInt();
		} else if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class)) {
			return this.isLong();
		} else if (type.isAssignableFrom(Double.class) || type.isAssignableFrom(double.class)) {
			return this.isDouble();
		} else if (type.isAssignableFrom(Float.class) || type.isAssignableFrom(float.class)) {
			return this.isFloat();
		} else if (type.isAssignableFrom(BigDecimal.class)) {
			return this.isBigDecimal();
		} else if (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)) {
			return this.isBoolean();
		} else if (type.isAssignableFrom(Character.class) || type.isAssignableFrom(char.class)) {
			return this.isChar();
		} else if (type.isAssignableFrom(Byte.class) || type.isAssignableFrom(byte.class)) {
			return this.isByte();
		} else if (type.isAssignableFrom(Short.class) || type.isAssignableFrom(short.class)) {
			return this.isShort();
		} else if (type.isAssignableFrom(Object.class)) {
			return this.isObject();
		} else {
			return this.isOther();
		}
	}
	/**
	 * {@link Object}类型
	 * @return {@link Class}<{@link Object}>
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月24日 下午12:28:40
	 * @Title: isObject
	 * @Description:
	 */
	public Class<Object> isObject();
	/**
	 * {@link String}类型
	 * @return {@link Class}<{@link String}>
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月24日 下午12:28:18
	 * @Title: isString
	 * @Description:
	 */
	public Class<String> isString();
	/**
	 * int或{@link Integer}类型
	 * @return {@link Class}<{@link Integer}>
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月24日 下午12:27:51
	 * @Title: isInt
	 * @Description:
	 */
	public Class<Integer> isInt();
	/**
	 * long或{@link Long}类型
	 * @return {@link Class}<{@link Long}>
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月24日 下午12:26:57
	 * @Title: isLong
	 * @Description:
	 */
	public Class<Long> isLong();
	/**
	 * double或{@link Double}类型
	 * @return {@link Class}<{@link Double}>
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月24日 下午12:26:33
	 * @Title: isDouble
	 * @Description:
	 */
	public Class<Double> isDouble();
	/**
	 * float或{@link Float}类型
	 * @return {@link Class}<{@link Float}>
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月24日 下午12:26:11
	 * @Title: isFloat
	 * @Description:
	 */
	public Class<Float> isFloat();
	/**
	 * byte或{@link Byte}类型
	 * @return {@link Class}<{@link Byte}>
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月24日 下午12:25:46
	 * @Title: isByte
	 * @Description:
	 */
	public Class<Byte> isByte();
	/**
	 * short或{@link Short}类型
	 * @return {@link Class}<{@link Short}>
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月24日 下午12:25:20
	 * @Title: isShort
	 * @Description:
	 */
	public Class<Short> isShort();
	/**
	 * boolean或{@link Boolean}类型
	 * @return {@link Class}<{@link Boolean}>
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月24日 下午12:24:58
	 * @Title: isBoolean
	 * @Description:
	 */
	public Class<Boolean> isBoolean();
	/**
	 * char或{@link Character}类型
	 * @return {@link Class}<{@link Character}>
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月24日 下午12:23:52
	 * @Title: isChar
	 * @Description:
	 */
	public Class<Character> isChar();
	/**
	 * {@link BigDecimal}类型
	 * @return {@link Class}<{@link BigDecimal}>
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月24日 下午12:23:02
	 * @Title: isBigDecimal
	 * @Description:
	 */
	public Class<BigDecimal> isBigDecimal();
	/**
	 * ? 其它类型
	 * @return {@link Class}<?>
	 * @version V1.0.1
	 * @author Haining.Liu
	 * @date 2018年1月24日 下午12:21:49
	 * @Title: isOther
	 * @Description:
	 */
	public Class<?> isOther();
}
