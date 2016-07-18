package org.roof.web.dictionary.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 字典表
 * 
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 Dictionary.java 2011-11-4
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "S_DICTIONARY")
@SequenceGenerator(name = "seq_s_dictionary", sequenceName = "SEQ_S_DICTIONARY", initialValue = 100000)
public class Dictionary implements Serializable {

	private static final long serialVersionUID = 8373619199358728370L;
	private Long id;
	private String type; // 类型(组)
	private String val; // 值
	private String text; // 文本
	private Long seq;// 排序
	private String active;// 是否激活
	private String description; // 描述

	public Dictionary() {
	}

	public Dictionary(Long id) {
		super();
		this.id = id;
	}

	public Dictionary(Long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_s_dictionary")
	public Long getId() {
		return id;
	}

	@Column(nullable = false)
	public String getType() {
		return type;
	}

	@Column(nullable = false)
	public String getText() {
		return text;
	}

	@Column(nullable = false)
	public String getVal() {
		return val;
	}

	public String getActive() {
		return active;
	}

	public String getDescription() {
		return description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

}
