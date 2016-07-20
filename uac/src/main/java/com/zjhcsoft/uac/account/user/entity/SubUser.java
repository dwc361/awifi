package com.zjhcsoft.uac.account.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.user.entity.Staff;

import com.zjhcsoft.uac.authorization.resource.entity.SysResource;

/**
 * 子账号
 * 
 * @author liuxin
 * 
 */
@Entity
public class SubUser extends Staff {

	private static final long serialVersionUID = -1599933237373407631L;

	private Dictionary privilege;// 权限类别@privilege_id
	private Dictionary scope;// 账号性质@scope_id
	private Date pwdDisableTime; // 账号失效时间
	private SysResource sysResource;// 所属资源@resource_id

	private String identify; // 标识(对端系统主键)
	private User user; // 所属主账号
	private String repassword;
	private String cn;
	
	/**
	 * 添加人：yxg
	 * 添加时间：2014-08-26
	 * 添加原因：增加子账号修改时间
	 */
	private Date modifyTime;//最后一次修改时间

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

	@Column(columnDefinition = "DATE")
	public Date getModifyTime() {
		return modifyTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RESOURCE_ID")
	public SysResource getSysResource() {
		return sysResource;
	}

	
	@Column(columnDefinition = "DATE")
	public Date getPwdDisableTime() {
		return pwdDisableTime;
	}
	
	public String getIdentify() {
		return identify;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setSysResource(SysResource sysResource) {
		this.sysResource = sysResource;
	}

	public void setPrivilege(Dictionary privilege) {
		this.privilege = privilege;
	}

	public void setScope(Dictionary scope) {
		this.scope = scope;
	}

	public void setPwdDisableTime(Date pwdDisableTime) {
		this.pwdDisableTime = pwdDisableTime;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}
	
	@Transient
	public boolean getEnabled(){
		return super.enabled;
	}

}
