package org.roof.commons;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.roof.struts2.DateJsonValueProcessor;

/**
 * @author hongzc
 * 
 */
public class RoofJsonUtil {
	private static final Logger logger = Logger.getLogger(RoofJsonUtil.class);
	/**
	 * 将JSON字符串解析成JSON对象，要获得对象里面的值，用get方法<br/>
	 * 如：json字符串：<br/>
	 * {"id":"1","name":"张三","list":["aaa","bbb","ccc"],"date":"2014-12-16"}<br/>
	 * 解析之后获得name，那么get("name")
	 * 
	 * @param json
	 * @return
	 */
	public static JSONObject string2Obj(String json) {
		JSONObject jsonobject = JSONObject.fromObject(json);
		return jsonobject;
	}

	/**
	 * 将JavaBean或者集合转换为JSON字符串, 使用默认的日期格式(yyyy-MM-dd)
	 * 
	 * @param data
	 *            需要转换的JavaBean
	 * @return 产生的JSON字符串
	 */
	public static String obj2String(Object data) {
		return obj2String(data, DateJsonValueProcessor.DEFAULT_DATE_PATTERN);
	}

	/**
	 * 将JavaBean或者集合转换为JSON类, 使用指定的日期格式
	 * 
	 * @param data
	 *            需要转换的JavaBean
	 * @param datePattern
	 *            日期格式
	 * @return 产生的JSON字符串
	 */
	public static String obj2String(Object data, String datePattern) {
		boolean isList = data instanceof Collection;
		boolean isArr = data.getClass().isArray();
		if (isList || isArr) {
			return JSONArray.fromObject(data, getJsonConfig(datePattern)).toString();
		}
		return JSONObject.fromObject(data, getJsonConfig(datePattern)).toString();
	}

	private static JsonConfig getJsonConfig(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(datePattern));
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new DateJsonValueProcessor(datePattern));
		return jsonConfig;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] arr = new String[] { "aaa", "bbb", "ccc" };
		Map data = new HashMap();
		data.put("name", "张三");
		data.put("id", "1");
		data.put("date", new Date());
		data.put("list", arr);

		String str = RoofJsonUtil.obj2String(data);
		System.out.println(str);
		System.out.println(RoofJsonUtil.string2Obj(str).keys());
		System.out.println(RoofJsonUtil.string2Obj(str).names());
		System.out.println(RoofJsonUtil.string2Obj(str).get("name"));
	}

}
