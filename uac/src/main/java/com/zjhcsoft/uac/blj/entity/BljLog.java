package com.zjhcsoft.uac.blj.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 手机验证码
 * 
 */
@Entity
@Table(name = "U_BLJ_LOG")
@SequenceGenerator(name = "SEQ_BLJ_LOG", sequenceName = "SEQ_BLJ_LOG")
public class BljLog {

	private Long id;
	private Long staff_id;// 主建
	private String staff_code;// 主账号
	private Date log_time;// 记录时间
	private String uuid;// uuid
	
	@Id
	@GeneratedValue(generator = "SEQ_BLJ_LOG", strategy = GenerationType.SEQUENCE)
	public Long getId() {
		return id;
	}

	@Column(columnDefinition = "DATE")
	public Date getLog_time() {
		return log_time;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLog_time(Date log_time) {
		this.log_time = log_time;
	}

	public String getStaff_code() {
		return staff_code;
	}

	public void setStaff_code(String staff_code) {
		this.staff_code = staff_code;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(Long staff_id) {
		this.staff_id = staff_id;
	}

}
