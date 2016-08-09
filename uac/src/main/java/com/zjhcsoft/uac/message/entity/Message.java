package com.zjhcsoft.uac.message.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.roof.web.user.entity.Staff;

/**
 * 个人提醒
 * 
 */
@Entity
@Table(name = "u_message")
public class Message {
	private Long id;
	private String title;// 标题
	private String content; // 内容
	private Date send_date;// 日期
	private String send_type;// 收件in,发件 out
	private Staff fromStaff; // 发件人@FROM_STAFF_ID
	private Staff toStaff; // 收件人@TO_STAFF_ID
	private String sts;// 已读,未读

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Column(length = 2000)
	public String getContent() {
		return content;
	}

	@Column(columnDefinition = "DATE")
	public Date getSend_date() {
		return send_date;
	}

	@ManyToOne
	@JoinColumn(name = "FROM_STAFF_ID")
	public Staff getFromStaff() {
		return fromStaff;
	}

	@ManyToOne
	@JoinColumn(name = "TO_STAFF_ID")
	public Staff getToStaff() {
		return toStaff;
	}

	public String getSts() {
		return sts;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public void setFromStaff(Staff fromStaff) {
		this.fromStaff = fromStaff;
	}

	public void setToStaff(Staff toStaff) {
		this.toStaff = toStaff;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSend_type() {
		return send_type;
	}

	public void setSend_type(String send_type) {
		this.send_type = send_type;
	}

}
