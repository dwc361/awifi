package org.roof.safety.service;

import org.roof.commons.create.AutoCreateUtils;
import org.roof.spring.CurrentSpringContext;

public class CreateStaticPage {

	/**
	 * 生成静态资源<br>
	 */
	public void genStaticPage() {
		AutoCreateUtils autoUtils = (AutoCreateUtils) CurrentSpringContext
				.getBean("autoCreateUtils");
//		autoUtils.processWrite(templatePath, root, exportPath);
	}

}
