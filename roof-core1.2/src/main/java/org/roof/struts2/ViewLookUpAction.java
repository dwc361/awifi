package org.roof.struts2;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 直接查找视图Action
 * 
 * @author liuxin 2011-3-25
 * 
 */
@Component
@Scope("prototype")
public class ViewLookUpAction extends RoofActionSupport {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		String proName = WebUtils.getRequest().getContextPath();
		result = StringUtils.replaceOnce(WebUtils.getRequest().getRequestURI(), proName, "");
		return SUCCESS;
	}
}
