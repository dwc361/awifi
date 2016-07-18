package org.roof.web.create.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.roof.commons.create.AutoCreateUtils;
import org.roof.struts2.RoofActionSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class CreateAction extends RoofActionSupport {
	AutoCreateUtils autoCreateUtils;

	public String goCreateCodePage() {
		result = "/roof-web/web/create.jsp";
		return JSP;
	}

	public String createCode() {
		autoCreateUtils.setTemplatePrefix(super.getParamByName("templatePrefix").toString());
		String packagePath = super.getParamByName("packagePath").toString();
		String actionName = super.getParamByName("actionName").toString();
		String userDir = super.getParamByName("userDir").toString();
		if (!"".equals(actionName)) {
			autoCreateUtils.setActionName(actionName);
		}
		String tableName = super.getParamByName("tableName").toString();
		List<String> sourcelist = new ArrayList<String>();
		sourcelist.add(tableName);// 添加需要生成的表名

		autoCreateUtils.createCode(userDir, packagePath, sourcelist);

		super.addParameter("tip", "结果请看控制台输出信息");
		super.addParameter("userDir", userDir);
		super.addParameter("templatePrefix", autoCreateUtils.getTemplatePrefix());
		super.addParameter("packagePath", packagePath);
		super.addParameter("actionName", actionName);
		super.addParameter("tableName", tableName);
		result = "/roof-web/web/create.jsp";
		return JSP;
	}

	@Resource
	public void setAutoCreateUtils(AutoCreateUtils autoCreateUtils) {
		this.autoCreateUtils = autoCreateUtils;
	}
}
