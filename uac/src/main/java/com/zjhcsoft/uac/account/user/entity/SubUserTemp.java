package com.zjhcsoft.uac.account.user.entity;

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

import org.roof.web.dictionary.entity.Dictionary;

import com.zjhcsoft.uac.authorization.resource.entity.App;

@Entity
@Table(name = "u_subuser_temp")
public class SubUserTemp {
	private Long id;
	private App app; // 系统
	private String username; // 用户名
	private String tel;// 电话
	private String scode; // 验证码
	private Date sendTime; // 发送时间

	private Dictionary privilege;// 权限类别@privilege_id
	private Dictionary scope;// 账号性质@scope_id

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "APP_ID")
	public App getApp() {
		return app;
	}

	public String getUsername() {
		return username;
	}

	public String getTel() {
		return tel;
	}

	public String getScode() {
		return scode;
	}

	@Column(columnDefinition = "DATE")
	public Date getSendTime() {
		return sendTime;
	}

	@ManyToOne
	@JoinColumn(name = "PRIVILEGE_ID")
	public Dictionary getPrivilege() {
		return privilege;
	}

	@ManyToOne
	@JoinColumn(name = "SCOPE_ID")
	public Dictionary getScope() {
		return scope;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public void setPrivilege(Dictionary privilege) {
		this.privilege = privilege;
	}

	public void setScope(Dictionary scope) {
		this.scope = scope;
	}

}
