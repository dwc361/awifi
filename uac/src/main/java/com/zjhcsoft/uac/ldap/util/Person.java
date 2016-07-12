package com.zjhcsoft.uac.ldap.util;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.proxy.HibernateProxy;
import org.roof.commons.RoofDateUtils;

import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.authorization.resource.entity.App;

public class Person {
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	private String cn; // 必填属性 登录账号
	private String sn; // 必填属性

	private String telephoneNumber; // 手机号码，可选属性
	private String userPassword; // 用户密码，可选属性
	private String parNode; // 上级节点
	private String description;// 备注
	private String url;// 存放系统的正则表达式
	private String pwdSetTime;// 密码设置时间
	private String pwdDisableTime;// 账号失效时间
	private Boolean needPassword;// 登陆是否需要密码
	private String appName;// 客户端应用名称

	public Person() {
	}

	public Person(String cn) {
		this.cn = cn;
	}

	public Person(String cn, String sn) {
		this.cn = cn;
		this.sn = sn;
	}

	public Person(String cn, String sn, String userPassword) {
		this.cn = cn;
		this.sn = sn;
		this.userPassword = userPassword;
	}

	public Person(User user) {
		this.cn = user.getUsername();
		this.sn = user.getName();
		this.userPassword = user.getPassword();
		String pwdDisableTime = RoofDateUtils.dateToString(user.getPwdDisableTime(), YYYY_MM_DD);
		this.pwdDisableTime = StringUtils.isEmpty(pwdDisableTime) ? " " : pwdDisableTime;
		String pwdSetTime = RoofDateUtils.dateToString(user.getPwdSetTime(), YYYY_MM_DD);
		this.pwdSetTime = StringUtils.isEmpty(pwdSetTime) ? " " : pwdSetTime;
		this.telephoneNumber = ObjectUtils.toString(user.getTel());
	}

	public Person(SubUser subUser) {
		this.cn = subUser.getCn();
		this.sn = subUser.getUsername();
		this.userPassword = subUser.getPassword();
		String pwdDisableTime = RoofDateUtils.dateToString(subUser.getPwdDisableTime(), YYYY_MM_DD);
		this.pwdDisableTime = StringUtils.isEmpty(pwdDisableTime) ? " " : pwdDisableTime;
		this.setParNode(subUser.getUser().getUsername());

		if (subUser.getSysResource() != null && subUser.getSysResource() instanceof App) {
			App app = (App) subUser.getSysResource();
			this.url = app.getExpression();
		}else if(subUser.getSysResource() != null && subUser.getSysResource() instanceof HibernateProxy){
			 Object  realEntity= ((HibernateProxy)subUser.getSysResource()).getHibernateLazyInitializer().getImplementation();  
		      if (realEntity instanceof App) {  
		    	this.url = ((App) realEntity).getExpression();
		     }  
		} else {
			this.url = " ";
		}
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParNode() {
		return parNode;
	}

	public void setParNode(String parNode) {
		this.parNode = parNode;
	}

	public String toString() {
		return "cn=" + cn + ",sn=" + sn + ",description=" + description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPwdSetTime() {
		return pwdSetTime;
	}

	public void setPwdSetTime(String pwdSetTime) {
		this.pwdSetTime = pwdSetTime;
	}

	public String getPwdDisableTime() {
		return pwdDisableTime;
	}

	public void setPwdDisableTime(String pwdDisableTime) {
		this.pwdDisableTime = pwdDisableTime;
	}

	public Boolean getNeedPassword() {
		return needPassword;
	}

	public void setNeedPassword(Boolean needPassword) {
		this.needPassword = needPassword;
	}

	public static String getYyyyMmDd() {
		return YYYY_MM_DD;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	
}
