package com.zjhcsoft.uac.authorization.resource.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 应用资源
 * 
 * @author liuxin
 * 
 */
@Entity
@Table(name = "U_APP")
public class App extends SysResource {
	private static final long serialVersionUID = -8133284335795701861L;
	public static final Long SELF_ID = 50L;
	private String path;// 路径
	private String expression; // 表达式
	private Boolean needPassword;// 登陆是否需要密码

	public String getPath() {
		return path;
	}

	public String getExpression() {
		return expression;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Column(name = "NEED_PASSWORD", columnDefinition = "CHAR(1)")
	public Boolean getNeedPassword() {
		return needPassword;
	}

	public void setNeedPassword(Boolean needPassword) {
		this.needPassword = needPassword;
	}

}
