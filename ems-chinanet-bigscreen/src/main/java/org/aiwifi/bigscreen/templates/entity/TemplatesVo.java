package org.aiwifi.bigscreen.templates.entity;

import java.util.List;

/**
 * @author 模版生成 <br/>
 *         表名： e_templates <br/>
 *         描述：e_templates <br/>
 */
public class TemplatesVo extends Templates {

	private List<TemplatesVo> templatesList;

	public TemplatesVo() {
		super();
	}

	public TemplatesVo(Long id) {
		super();
		this.id = id;
	}

	public List<TemplatesVo> getTemplatesList() {
		return templatesList;
	}

	public void setTemplatesList(List<TemplatesVo> templatesList) {
		this.templatesList = templatesList;
	}

}
