package com.neusoft.sdd.util.wx;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.neusoft.sdd.base.model.wx.pojo.AcceptMsgInfo;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月22日
 * @since Neusoft 001
 */
public class XmlUtils
{
    
    public static void main(String[] args)
    {
        String xml = "<xml><ToUserName><![CDATA[wxbec5ed1a233b5d3d]]></ToUserName><FromUserName><![CDATA[Magic_yuan]]></FromUserName><CreateTime>1432266486</CreateTime></xml>";
        try
        {
            System.out.println(parseXml(xml, AcceptMsgInfo.class));
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        
        
//        int time = 1432266486;
//        
//        System.out.println(new Date(1432266486).toLocaleString());
        
    }
    
    
    /**
     * [简要描述]:解析XML并封装为指定对象<br/>
     * [详细描述]:<br/>
     *
     * @author Magic_yuan
     * @param xml
     * @param t 泛型
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @exception 
     */
    public static <T> T parseXml(String xml, Class<T> t) throws InstantiationException, IllegalAccessException
    {
        Map<String, String> result = new HashMap<String, String>();
        
        T obj = t.newInstance();
        
        try
        {

            Document document = DocumentHelper.parseText(xml);

            // 拿到根节点
            Element rootElement = document.getRootElement();

            listNodes(rootElement, result);
            
            Field[] fields = obj.getClass().getDeclaredFields();
            
            for (Field tempField : fields)
            {
                tempField.setAccessible(true);
                
                tempField.set(obj, result.get(tempField.getName().toLowerCase()));
            }
            
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return obj;
    }

    /**
     * 遍历当前节点元素下面的所有(元素的)子节点
     * 
     * @param node
     */
    @SuppressWarnings("unchecked")
	public static void listNodes(Element node, Map<String, String> result)
    {
        System.out.println("当前节点的名称：：" + node.getName());
        // 获取当前节点的所有属性节点
		List<Attribute> list = node.attributes();
        // 遍历属性节点
        for (Attribute attr : list)
        {
            System.out.println(attr.getText() + "-----" + attr.getName() + "---" + attr.getValue());
        }

        if (!(node.getTextTrim().equals("")))
        {
            System.out.println("文本内容：：：：" + node.getText());
        }

        result.put( node.getName().toLowerCase(), node.getTextTrim());
        // 当前节点下面子节点迭代器
        Iterator<Element> it = node.elementIterator();
        // 遍历
        while (it.hasNext())
        {
            // 获取某个子节点对象
            Element e = it.next();
            // 对子节点进行遍历
            listNodes(e, result);
        }
    }
}
