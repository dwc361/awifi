package org.roof.web;

import java.util.HashMap;
import java.util.Map;

import org.roof.dataaccess.Page;

public class PageUtils {
	/**
	 * 创建Page工具栏 <br/>
	 * 返回Map中包含<br/>
	 * pageStart : 分页开始页码 <br/>
	 * pageEnd : 分页结束页码
	 * 
	 * @param page
	 * @return
	 */
	public static Map<String, Long> createPagePar(Page page) {
		Map<String, Long> result = new HashMap<String, Long>();
		long pageStart = 1;
		if (page.getCurrentPage() > 6L) {
			pageStart = page.getCurrentPage() - 5L;
		}
		long pageEnd = pageStart + 10L;
		if (pageEnd > page.getPageCount()) {
			pageEnd = page.getPageCount();
		}
		result.put("pageStart", pageStart);
		result.put("pageEnd", pageEnd);
		return result;
	}

}
