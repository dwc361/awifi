package com.zjhcsoft.uac.log.entity;

import java.io.Serializable;
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
import javax.persistence.Transient;

import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.user.entity.Staff;

import com.zjhcsoft.uac.ldap.util.Person;

/**
 * 登录审计
 * 
 * @author liuxin
 * 
 */
@Entity
@Table(name = "u_login_log")
public class LoginLog implements Serializable {

	private static final long serialVersionUID = 3810665296380292513L;
	private Long id;
	private Date login_time;// 登陆时间
	private Dictionary loginType; // 登陆认证方式(LOGIN_TYPE)@login_type_id
	private String ip;// 登陆IP
	private Staff user;// 登陆账号@USER_ID
	private Dictionary loginResult;// 登陆结果(LOGIN_RESULT)@login_result_id
	private Dictionary loginFailReason;// 登陆失败原因(LOGIN_FAIL_REASON)@login_fail_reason_id
	private String loginFailDetails;// 登陆失败详情
	private Person person;

	private Date login_time_end;// 登陆时间

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Column(columnDefinition = "DATE")
	public Date getLogin_time() {
		return login_time;
	}

	@ManyToOne
	@JoinColumn(name = "LOGIN_TYPE_ID")
	public Dictionary getLoginType() {
		return loginType;
	}

	public String getIp() {
		return ip;
	}

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public Staff getUser() {
		return user;
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

	@Transient
	public Person getPerson() {
		return person;
	}

	@Transient
	public Date getLogin_time_end() {
		return login_time_end;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}

	public void setLoginType(Dictionary loginType) {
		this.loginType = loginType;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setUser(Staff user) {
		this.user = user;
	}

	public void setLoginResult(Dictionary loginResult) {
		this.loginResult = loginResult;
	}

	public void setLoginFailReason(Dictionary loginFailReason) {
		this.loginFailReason = loginFailReason;
	}

	public String getLoginFailDetails() {
		return loginFailDetails;
	}

	public void setLoginFailDetails(String loginFailDetails) {
		this.loginFailDetails = loginFailDetails;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setLogin_time_end(Date login_time_end) {
		this.login_time_end = login_time_end;
	}

}
