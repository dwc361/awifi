package com.zjhcsoft.uac.log.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 服务器日志
 * 
 * @author liuxin
 *
 */
@Entity
@Table(name = "U_SERVER_LOG")
public class ServerLog {
	private Long id;
	private Date log_date;// 日志时间
	private String reason;// 原因

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Column(columnDefinition = "DATE")
	public Date getLog_date() {
		return log_date;
	}

	@Column(length = 1024)
	public String getReason() {
		return reason;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLog_date(Date log_date) {
		this.log_date = log_date;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
