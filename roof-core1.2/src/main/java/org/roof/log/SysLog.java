package org.roof.log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 日志
 * 
 * @author liuxin 2011-9-15
 * @version 1.0 SysLog.java liuxin 2011-9-15
 */
@Entity
@Table(name = "SYS_LOG")
public class SysLog {

	private Long id;
	private Date log_time;
	private String log_level;
	private String location;
	private String message;

	private String detail_message;
	private String action;
	private String query_string;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Column(name = "LOG_TIME", columnDefinition = "DATE")
	public Date getLog_time() {
		return log_time;
	}

	@Column(name = "LOG_LEVEL")
	public String getLog_level() {
		return log_level;
	}

	@Column(name = "LOCATION")
	public String getLocation() {
		return location;
	}

	@Column(name = "MESSAGE", columnDefinition = "TEXT")
	public String getMessage() {
		return message;
	}

	@Column(name = "DETAIL_MESSAGE", columnDefinition = "TEXT")
	public String getDetail_message() {
		return detail_message;
	}

	@Column(name = "QUERY_STRING", columnDefinition = "TEXT")
	public String getQuery_string() {
		return query_string;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLog_time(Date log_time) {
		this.log_time = log_time;
	}

	public void setLog_level(String log_level) {
		this.log_level = log_level;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDetail_message(String detail_message) {
		this.detail_message = detail_message;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public void setQuery_string(String query_string) {
		this.query_string = query_string;
	}

}
