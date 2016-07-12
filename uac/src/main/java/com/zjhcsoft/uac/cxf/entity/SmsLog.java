package com.zjhcsoft.uac.cxf.entity;

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
@Table(name = "U_SMS_LOG")
@SequenceGenerator(name = "SEQ_SMS_LOG", sequenceName = "SEQ_SMS_LOG")
public class SmsLog {

	private Long id;
	private Long staff_id;// 用户ID
	private String staff_code;// 主账号
	private String tel;// 手机号码
	private Date log_time;// 记录时间
	private String message;// 内容
	private boolean sts;// 是否发送成功
	private boolean used;// 是否使用

	@Id
	@GeneratedValue(generator = "SEQ_SMS_LOG", strategy = GenerationType.SEQUENCE)
	public Long getId() {
		return id;
	}

	@Column(columnDefinition = "DATE")
	public Date getLog_time() {
		return log_time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSts() {
		return sts;
	}

	public void setSts(boolean sts) {
		this.sts = sts;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLog_time(Date log_time) {
		this.log_time = log_time;
	}

	public Long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(Long staff_id) {
		this.staff_id = staff_id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getStaff_code() {
		return staff_code;
	}

	public void setStaff_code(String staff_code) {
		this.staff_code = staff_code;
	}

}
