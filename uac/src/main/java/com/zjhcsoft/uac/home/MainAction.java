package com.zjhcsoft.uac.home;

import org.roof.struts2.RoofActionSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class MainAction extends RoofActionSupport {

	private static final long serialVersionUID = -6255077936641471291L;

	public String index() {
		super.addParameter("name", "4A安全管理系统");
		result = "/cas-web/casGenericSuccess.jsp";
		return JSP;
	}

}
