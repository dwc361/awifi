package com.zjhcsoft.uac.authorization.resource.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.roof.web.dictionary.entity.Dictionary;

/**
 * 系统资源
 * 
 * @author liuxin
 * 
 */
@Entity
@Table(name = "u_system")
public class System extends SysResource {

	private App app;// 所属应用@app_id
	private String ip;// ip地址
	private String port;// 端口号
	private Dictionary typename;
	
	private Dictionary group;// 所属域

	@ManyToOne
	@JoinColumn(name = "app_id")
	public App getApp() {
		return app;
	}

	public String getIp() {
		return ip;
	}

	public String getPort() {
		return port;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	@ManyToOne
	@JoinColumn(name = "Typename_id")
	public Dictionary getTypename() {
		return typename;
	}

	public void setTypename(Dictionary Typename) {
		typename = Typename;
	}
	
	@ManyToOne
	@JoinColumn(name = "Group_id")
	public Dictionary getGroup() {
		return group;
	}

	public void setGroup(Dictionary group) {
		this.group = group;
	}

}
