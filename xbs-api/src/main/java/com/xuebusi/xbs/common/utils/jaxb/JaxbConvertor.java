package com.xuebusi.xbs.common.utils.jaxb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class JaxbConvertor {
	private static final Log logger = LogFactory.getLog(JaxbConvertor.class);
	private JAXBContext jc = null;
	private JAXBContext pkgJC = null;
	private String pkgName;

	/**
	 * 用于加载XML的对应实体  {包路径  或是  实体类（.getClass()）}
	 */
	public void setPkgName(String pkgName) {
		if (pkgName == null) {
			logger.warn("package name is null.");
		}

		try {
			this.jc = JAXBContext.newInstance(pkgName);
		} catch (JAXBException arg2) {
			logger.error("JAXBContext initialize failed.JAXBException throw.",
					arg2);
		} catch (Exception arg3) {
			logger.error("JAXBContext initialize occur other exception.", arg3);
		}

	}
	
	/*对象实体在转换处调用 message 为根节点
	jaxbConvert9.init(message9);
	public void init(Object obj) throws JAXBException {
		this.jc = JAXBContext.newInstance(new Class[]{obj.getClass()});
	}*/

	
	/**
	 * 用于XML报文转换成实体  {便于操作}
	 * @param message  传入的XML报文
	 * @param encoding 
	 * @return
	 * @throws Exception
	 */
	public  Object convert2Object(byte[] message, String encoding)
			throws Exception {
		Object obj = null;
		if (message == null) {
			return null;
		} else if (this.jc == null) {
			return null;
		} else {
			Unmarshaller unmarshaller = null;

			try {
				/**
				 * Unmarshaller接口，将XML数据反序列化为Java对象。
				 */
				unmarshaller = this.jc.createUnmarshaller();
			} catch (JAXBException arg23) {
				logger.error("creat unmarshaller exception.", arg23);
				throw arg23;
			}

			if (unmarshaller == null) {
				return null;
			} else {
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
						message);

				try {
					obj = unmarshaller.unmarshal(byteArrayInputStream);
				} catch (JAXBException arg19) {
					throw arg19;
				} catch (Exception arg20) {
					throw arg20;
				} catch (Error arg21) {
					throw arg21;
				} finally {
					try {
						if (byteArrayInputStream != null) {
							byteArrayInputStream.close();
							byteArrayInputStream = null;
						}
					} catch (Exception arg17) {
						logger.error("close input stream exception:", arg17);
					} catch (Error arg18) {
						logger.error("cloase input stream error.", arg18);
					}

				}

				return obj;
			}
		}
	}

	
	/**
	 * 将实体转换成XML数据 {便于传输}
	 * @param entity    传入的bean 对象
	 * @param encoding	编码字符集
	 * @return
	 * @throws Exception
	 */
	public byte[] convert2Message(Object entity, String encoding)
			throws Exception {
		return this.convert2Message(entity, encoding, this.jc);
	}

	private byte[] convert2Message(Object entity, String encoding,
			JAXBContext jaxb) throws Exception {
		Object xml = null;
		if (entity == null) {
			return null;
		} else {
			Marshaller marshaller = null;
			if (jaxb == null) {
				return null;
			} else {
				try {
					/**
					 * Marshaller接口，将Java对象序列化为XML数据。
					 */
					marshaller = jaxb.createMarshaller();
					marshaller.setListener(new MarshallerListener());
				} catch (JAXBException arg24) {
					logger.error("creat unmarshaller exception.", arg24);
					throw arg24;
				}

				if (marshaller == null) {
					return null;
				} else {
					ByteArrayOutputStream byteArrayOutputStream = null;

					byte[] xml1;
					try {
						byteArrayOutputStream = new ByteArrayOutputStream();
						marshaller.marshal(entity, byteArrayOutputStream);
						xml1 = byteArrayOutputStream.toByteArray();
					} catch (JAXBException arg20) {
						throw arg20;
					} catch (Exception arg21) {
						throw arg21;
					} catch (Error arg22) {
						throw arg22;
					} finally {
						try {
							if (byteArrayOutputStream != null) {
								byteArrayOutputStream.close();
								byteArrayOutputStream = null;
							}
						} catch (Exception arg18) {
							logger.error("close input stream exception:", arg18);
						} catch (Error arg19) {
							logger.error("cloase input stream error.", arg19);
						}

					}

					return xml1;
				}
			}
		}
	}
}