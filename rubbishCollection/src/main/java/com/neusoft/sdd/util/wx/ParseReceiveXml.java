/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :ParseReceiveXml.java
 * 创建人  :Administrator
 * 创建时间:2015年5月19日
 */

package com.neusoft.sdd.util.wx;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.neusoft.sdd.base.model.wx.pojo.ReceiveXmlInfo;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月19日
 * @since Neusoft 001
 */
public class ParseReceiveXml {
	/**
	 * 解析微信xml消息
	 * 
	 * @param strXml
	 * @return
	 */
	public static ReceiveXmlInfo getMsgEntity(InputStream inputStream) {
		ReceiveXmlInfo msg = null;
		try {
			// 读取输入流
			SAXReader reader = new SAXReader();

			Document document = reader.read(inputStream);
			document.asXML();
			// 获得文档的根节点
			Element root = document.getRootElement();
			// 遍历根节点下所有子节点
			Iterator<?> iter = root.elementIterator();

			// 遍历所有结点
			msg = new ReceiveXmlInfo();
			// 利用反射机制，调用set方法
			// 获取该实体的元类型
			Class<?> c = Class.forName("com.neusoft.sdd.base.model.wx.pojo.ReceiveXmlInfo");
			msg = (ReceiveXmlInfo) c.newInstance();// 创建这个实体的对象

			while (iter.hasNext()) {
				Element ele = (Element) iter.next();
				// 获取set方法中的参数字段（实体类的属性）
				Field field = c.getDeclaredField(ele.getName());

				// 获取set方法，field.getType())获取它的参数数据类型
				Method method = c.getDeclaredMethod("set" + ele.getName(),
						field.getType());
				// 调用set方法
				method.invoke(msg, ele.getText());
			}
		} catch (Exception e) {
			System.out.println("xml 格式异常: ");
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return msg;
	}

	public static String readInputStreamToXmlString(InputStream inputStream) {
		// 读取输入流
		SAXReader reader = new SAXReader();

		Document document = null;
		try {
			document = reader.read(inputStream);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return null != document ? document.asXML() : "";
	}

}
