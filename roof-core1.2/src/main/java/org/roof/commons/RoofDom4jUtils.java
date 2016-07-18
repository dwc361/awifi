package org.roof.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 操作dom的工具类
 * 
 * @author Hongzc
 * 
 */
public class RoofDom4jUtils {

	/**
	 * 添加一个节点到根节点下面
	 * 
	 * @param sourceXml
	 * @param nodeName
	 * @param nodeVal
	 * @return
	 * @throws DocumentException
	 */
	public static String addNodeInRoot(String sourceXml, String nodeName, String nodeVal) throws DocumentException {
		Map<String, String> valsMap = new HashMap<String, String>();
		valsMap.put(nodeName, nodeVal);
		return addNodeInRoot(sourceXml, valsMap);
	}

	/**
	 * 添加多个节点到根节点下面
	 * 
	 * @param sourceXml
	 * @param valsMap
	 * @return
	 * @throws DocumentException
	 */
	public static String addNodeInRoot(String sourceXml, Map<String, String> valsMap) throws DocumentException {
		Document doc = DocumentHelper.parseText(sourceXml);
		Element root = doc.getRootElement();
		Set<String> keys = valsMap.keySet();
		for (String key : keys) {
			String val = valsMap.get(key);
			Element add = root.addElement(key);
			add.setText(val);
		}
		return doc.asXML();
	}

	/**
	 * 移除根节点下面的一个节点
	 * 
	 * @param sourceXml
	 * @param nodeName
	 * @return
	 * @throws DocumentException
	 */
	public static String removeNodeInRoot(String sourceXml, String nodeName) throws DocumentException {
		List<String> nodeList = new ArrayList<String>();
		nodeList.add(nodeName);
		return removeNodeInRoot(sourceXml, nodeList);
	}

	/**
	 * 移除根节点下面的多个节点
	 * 
	 * @param sourceXml
	 * @param nodeList
	 * @return
	 * @throws DocumentException
	 */
	public static String removeNodeInRoot(String sourceXml, List<String> nodeList) throws DocumentException {
		Document doc = DocumentHelper.parseText(sourceXml);
		Element root = doc.getRootElement();
		Iterator iterator = root.elementIterator();
		while (iterator.hasNext()) {
			Element node = (Element) iterator.next();
			if (nodeList.contains(node.getName())) {
				root.remove(node);
			}
		}
		return doc.asXML();
	}

	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<root FuncName=\"findAcctMsg\">\n"
				+ "\t<validation></validation>\n" + "\t<account_name>QYHZST310000</account_name>\n"
				+ "\t<source>Convenience</source>\n" + "</root>\n";

		try {
//			System.out.println(RoofDom4jUtils.addNodeInRoot(xml, "newNode", "张三"));
//			System.out.println(RoofDom4jUtils.removeNodeInRoot(xml, "source"));
			System.out.println(xml);
			String str = RoofDom4jUtils.removeNodeInRoot(xml, "validation");
			String trim = str.replaceAll("\t", "").replaceAll("\n", "");// 去除所有空格
			System.out.println(trim);
			String md5 = RoofMD5Utils.getMD5String(trim);
			System.out.println(RoofDom4jUtils.addNodeInRoot(str, "validation", md5));

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}
}
