package org.roof.web.resources.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

/**
 * 资源(权限)
 * 
 * @author liuxin
 * 
 */
@Entity
@DiscriminatorValue("privilege")
public class Privilege extends Module {

	/**
	 * 返回类型-json
	 */
	public static final String FORMAT_JSON = "json";
	/**
	 * 返回类型-html
	 */
	public static final String FORMAT_HTML = "html";
	/**
	 * 返回类型-xml
	 */
	public static final String FORMAT_XML = "xml";

	protected String returnExample;// 返回实例
	protected String remark;// 备注
	protected String format; // 支持格式

	protected List<Parameter> parameters;// 请求参数
	protected List<Parameter> returnFields;// 返回参数

	@Column(name = "RETURNEXAMPLE", length = 4000)
	public String getReturnExample() {
		return returnExample;
	}

	@Column(name = "REMARK",  length = 4000)
	public String getRemark() {
		return remark;
	}

	@OneToMany
	@JoinTable(name = "privilege_parameters_rel", joinColumns = { @JoinColumn(name = "PRIVILEGE_ID") }, inverseJoinColumns = { @JoinColumn(name = "PARAMETER_ID") })
	public List<Parameter> getParameters() {
		return parameters;
	}

	@OneToMany
	@JoinTable(name = "privilege_returnfields_rel", joinColumns = { @JoinColumn(name = "PRIVILEGE_ID") }, inverseJoinColumns = { @JoinColumn(name = "PARAMETER_ID") })
	public List<Parameter> getReturnFields() {
		return returnFields;
	}

	public void setReturnExample(String returnExample) {
		this.returnExample = returnExample;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public void setReturnFields(List<Parameter> returnFields) {
		this.returnFields = returnFields;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
