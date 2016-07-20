package org.roof.safety.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 动转静
 * 
 */
// @Entity
@Table(name = "S_DYNAMIC_TO_STATIC")
public class DynamicToStatic {

	private Long id;

	private String request_url;// 动态页面请求链接，含参数

	private String template_url;// 模板路径

	private String file_name;// 生成文件路径

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public String getFile_name() {
		return file_name;
	}

	public String getRequest_url() {
		return request_url;
	}

	public String getTemplate_url() {
		return template_url;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRequest_url(String request_url) {
		this.request_url = request_url;
	}

	public void setTemplate_url(String template_url) {
		this.template_url = template_url;
	}

}