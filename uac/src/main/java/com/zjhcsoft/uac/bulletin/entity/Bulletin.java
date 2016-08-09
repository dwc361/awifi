package com.zjhcsoft.uac.bulletin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 公告
 * 
 */
@Entity
@Table(name = "u_bulletin")
public class Bulletin {
	private Long id;
	private String title;// 标题
	private String content;// 内容
	private Long visitors; // 浏览量
	private Date create_date;// 创建时间

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	@Column(length = 2000)
	public String getContent() {
		return content;
	}

	public Long getVisitors() {
		return visitors;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setVisitors(Long visitors) {
		this.visitors = visitors;
	}

	@Column(columnDefinition = "DATE")
	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

}
