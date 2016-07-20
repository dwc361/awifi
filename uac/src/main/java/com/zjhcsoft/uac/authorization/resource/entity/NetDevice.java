package com.zjhcsoft.uac.authorization.resource.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.roof.web.dictionary.entity.Dictionary;

/**
 * 网络设备
 * 
 * @author liuxin
 * 
 */
@Entity
@Table(name = "U_NET_DEVICE")
public class NetDevice extends System {
	private String serve_name;
	private Dictionary serve_type;// 账号状态@serve_type_id
	public String getServe_name() {
		return serve_name;
	}
	public void setServe_name(String serve_name) {
		this.serve_name = serve_name;
	}
	@ManyToOne
	@JoinColumn(name = "serve_type_id")
	public Dictionary getServe_type() {
		return serve_type;
	}
	public void setServe_type(Dictionary serve_type) {
		this.serve_type = serve_type;
	}

}
