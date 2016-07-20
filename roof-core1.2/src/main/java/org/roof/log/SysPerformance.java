package org.roof.log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 业务逻辑执行时间记录
 * 
 * @author liuxin 2011-9-19
 * @version 1.0 SysPerformance.java liuxin 2011-9-19
 */
@Entity
@Table(name = "SYS_PERFORMANCE")
public class SysPerformance {
	private Long id;
	private Date log_time;
	private String action;
	private String query_string;
	private Long execute_time;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Column(name = "LOG_TIME", columnDefinition = "DATE")
	public Date getLog_time() {
		return log_time;
	}

	@Column(name = "ACTION")
	public String getAction() {
		return action;
	}

	@Column(name = "QUERY_STRING", columnDefinition = "TEXT")
	public String getQuery_string() {
		return query_string;
	}

	@Column(name = "EXECUTE_TIME")
	public Long getExecute_time() {
		return execute_time;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLog_time(Date log_time) {
		this.log_time = log_time;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setQuery_string(String query_string) {
		this.query_string = query_string;
	}

	public void setExecute_time(Long execute_time) {
		this.execute_time = execute_time;
	}

}
