package org.roof.log;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统登录日志
 */
@Entity
@Table(name = "sys_login_log")
public class SysLoginLog implements Serializable {

	private static final long serialVersionUID = -5576983729862601318L;

	private Long id;// 主键

	private String staff_name;// 用户名称

	private String username;// 用户工号

	private String ip;// 登录IP

	private Date log_time;// 登录时间

	private String message;// 登录信息

	private Long sts;// 登录状态 1成功，0失败

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Column(columnDefinition = "Date")
	public Date getLog_time() {
		return log_time;
	}

	@Column(name = "MESSAGE", length = 2000)
	public String getMessage() {
		return message;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLog_time(Date log_time) {
		this.log_time = log_time;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public Long getSts() {
		return sts;
	}

	public void setSts(Long sts) {
		this.sts = sts;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
