package org.roof.excel.support.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.roof.excel.support.IExcelDao;
import org.roof.excel.vo.Property;

import com.zjhcsoft.xdm.XmlEncoder;

public class PropertiesLoader {

	private static Logger logger = Logger.getLogger(PropertiesLoader.class);

	private static Collection<Property> properties;

	public static String getProperty(String name) {
		if (properties == null) {
			load();
		}
		for (Property property : properties) {
			if (StringUtils.equalsIgnoreCase(name, property.getName())) {
				return property.getValue();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static void load() {
		InputStream in = null;
		SAXReader reader = new SAXReader();
		try {
			String dir = System.getProperty("user.dir") + "/etc";
			File file = new File(dir + "/" + "properties.xml");
			if (!file.exists()) {
				in = PropertiesLoader.class.getClassLoader().getResourceAsStream("properties.xml");
				logger.debug("加载资源文件:" + file.getPath());
			} else {
				in = new FileInputStream(file);
				logger.debug("加载资源文件:classpath:properties.xml");
			}
			Document document = reader.read(in);
			List<Node> nodes = document.selectNodes("Properties/Property");
			XmlEncoder xmlEncoder = new XmlEncoder();
			properties = (Collection<Property>) xmlEncoder.encodeCollection(nodes, List.class, Property.class);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static IExcelDao getDao() {
		String daoClass = getProperty("daoClass");
		return getDao(daoClass);
	}

	public static IExcelDao getDao(String daoClass) {
		if (daoClass == null || daoClass.trim().length() == 0) {
			logger.debug("使用默认Dao:com.zjhcsoft.exceldb.support.impl.OracleJdbcDao");
			return new OracleJdbcDao();
		}
		IExcelDao result = null;
		try {
			logger.debug(daoClass);
			result = (IExcelDao) Class.forName(daoClass).newInstance();
			logger.debug("创建" + daoClass + "成功!");
		} catch (InstantiationException e) {
			logger.error("类实例创建失败!", e);
		} catch (IllegalAccessException e) {
			logger.error("类实例创建失败!", e);
		} catch (ClassNotFoundException e) {
			logger.error("类实例创建失败!", e);
		}
		return result;
	}
}
