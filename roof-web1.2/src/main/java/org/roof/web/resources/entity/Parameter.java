package org.roof.web.resources.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 参数
 * 
 * @author liuxin
 * 
 */
@Entity
@Table(name = "S_PARAMETER")
@SequenceGenerator(name = "seq_s_parameter", sequenceName = "SEQ_S_PARAMETER")
public class Parameter {
	protected String id;
	protected String name;
	protected Boolean required; // 必须
	protected String type;// 类型
	protected String explain; // 说明

	@Id
	@GeneratedValue(generator = "seq_s_parameter", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID")
	public String getId() {
		return id;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	@Column(name = "REQUIRED", columnDefinition = "char(1)")
	public Boolean getRequired() {
		return required;
	}

	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	@Column(name = "EXPLAIN", length = 4000)
	public String getExplain() {
		return explain;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

}
