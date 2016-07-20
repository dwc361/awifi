package org.roof.web.role.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.roof.security.entity.BaseRole;

@Entity
public class Roles extends BaseRole {

	private static final long serialVersionUID = 1L;
	private Date create_date; // 创建日期
	private String description; // 描述

	public Roles() {
	}

	public Roles(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Column(columnDefinition = "DATE")
	public Date getCreate_date() {
		return create_date;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
