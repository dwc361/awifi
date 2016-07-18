package org.roof.struts2;

import java.util.HashMap;
import java.util.Map;

import org.roof.commons.PropertiesUtil;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * 系统常量注入
 *
 */
@Component
public class SysConstsCommonActionParam implements ICommonActionParameters {
	private static Map<String, Object> map;

	@Override
	public Map<String, Object> getParameters() {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		try {
			map.put("currVisitUrl", WebUtils.getRequest().getRequestURL());
			map.put("currSysName", PropertiesUtil.getPorperty("currSysName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
