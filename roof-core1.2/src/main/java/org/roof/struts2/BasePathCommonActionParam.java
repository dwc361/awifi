package org.roof.struts2;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 见basePath 加入每个Action的ValueStack中
 * 
 * @see ICommonActionParameters
 * 
 * @author liuxin 2011-3-28
 * 
 */
@Component
public class BasePathCommonActionParam implements ICommonActionParameters {
	private static Map<String, Object> map;

	@Override
	public Map<String, Object> getParameters() {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("basePath", WebUtils.getRequest().getContextPath());
		return map;
	}
}
