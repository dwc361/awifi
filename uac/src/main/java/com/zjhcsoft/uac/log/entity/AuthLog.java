package com.zjhcsoft.uac.log.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.user.entity.Staff;

import com.zjhcsoft.uac.authorization.resource.entity.SysResource;

/**
 * 认证审计
 * 
 * @author liuxin
 * 
 */
@Entity
@Table(name = "u_auth_log")
public class AuthLog implements Serializable {
	private static final long serialVersionUID = 4782979478041634495L;
	private Long id;
	private Date auth_time;// 认证时间
	private String ip;// 认证IP
	private SysResource sysResource; // 请求系统@SYSRESOURCE_ID
	private Staff staff;// 认证用户@STAFF_ID
	private Dictionary loginResult;// 登陆结果@login_result_id
	private Dictionary loginFailReason;// 登陆失败原因@login_fail_reason_id
	private String requestUrl; // 请求地址

	private Date auth_time_end;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Column(columnDefinition = "DATE")
	public Date getAuth_time() {
		return auth_time;
	}

	public String getIp() {
		return ip;
	}

	@ManyToOne
	@JoinColumn(name = "STAFF_ID")
	public Staff getStaff() {
		return staff;
	}

	@ManyToOne
	@JoinColumn(name = "LOGIN_RESULT_ID")
	public Dictionary getLoginResult() {
		return loginResult;
	}

	@ManyToOne
	@JoinColumn(name = "LOGIN_FAIL_REASON_ID")
	public Dictionary getLoginFailReason() {
		return loginFailReason;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SYSRESOURCE_ID")
	public SysResource getSysResource() {
		return sysResource;
	}

	@Transient
	public Date getAuth_time_end() {
		return auth_time_end;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAuth_time(Date auth_time) {
		this.auth_time = auth_time;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public void setLoginResult(Dictionary loginResult) {
		this.loginResult = loginResult;
	}

	public void setLoginFailReason(Dictionary loginFailReason) {
		this.loginFailReason = loginFailReason;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public void setSysResource(SysResource sysResource) {
		this.sysResource = sysResource;
	}

	public void setAuth_time_end(Date auth_time_end) {
		this.auth_time_end = auth_time_end;
	}

}
