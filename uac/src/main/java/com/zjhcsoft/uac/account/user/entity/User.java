package com.zjhcsoft.uac.account.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.user.entity.Staff;

/**
 * 主账号
 * 
 * @author liuxin
 * 
 */
@Entity
public class User extends Staff {
	private static final long serialVersionUID = 1949978267856048183L;
	private Date pwdSetTime;// 密码设置时间
	private String certCoding;// 证书编码
	private Dictionary scope;// 账号性质@scope_id
	private Date pwdDisableTime; // 账号失效时间
	private Dictionary category;// 用户类别@category_id
	private String idNumber;// 身份证号
	private Dictionary gender; // 性别@gender_id
	private String tel;// 电话
	private String mail;// 邮箱
	private String repassword;

	private String identify; // 标识(对端系统主键)
	
	private Date modifyTime;//最后一次修改时间

	@Column(columnDefinition = "DATE")
	public Date getPwdSetTime() {
		return pwdSetTime;
	}

	public String getCertCoding() {
		return certCoding;
	}

	@ManyToOne
	@JoinColumn(name = "SCOPE_ID")
	public Dictionary getScope() {
		return scope;
	}

	@Column(columnDefinition = "DATE")
	public Date getPwdDisableTime() {
		return pwdDisableTime;
	}

	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	public Dictionary getCategory() {
		return category;
	}

	public String getIdNumber() {
		return idNumber;
	}

	@ManyToOne
	@JoinColumn(name = "GENDER_ID")
	public Dictionary getGender() {
		return gender;
	}

	public String getTel() {
		return tel;
	}

	public String getMail() {
		return mail;
	}

	@Transient
	public String getRepassword() {
		return repassword;
	}

	public String getIdentify() {
		return identify;
	}

	public void setPwdSetTime(Date pwdSetTime) {
		this.pwdSetTime = pwdSetTime;
	}

	public void setCertCoding(String certCoding) {
		this.certCoding = certCoding;
	}

	public void setScope(Dictionary scope) {
		this.scope = scope;
	}

	public void setPwdDisableTime(Date pwdDisableTime) {
		this.pwdDisableTime = pwdDisableTime;
	}

	public void setCategory(Dictionary category) {
		this.category = category;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public void setGender(Dictionary gender) {
		this.gender = gender;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	
	@Column(columnDefinition = "DATE")
	public Date getModifyTime() {
		return modifyTime;
	}
	
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	@Transient
	public boolean getEnabled(){
		return super.enabled;
	}

	@Override
	public String toString() {
		return "User [idNumber=" + idNumber + ", tel=" + tel + ", username="
				+ username + ", password=" + password + ", orgName="
				+ (getOrg() == null ? null : getOrg().getOrg_name()) + "]";
	}

}
