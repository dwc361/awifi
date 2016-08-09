package com.zjhcsoft.uac.authorization.resource.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.roof.web.dictionary.entity.Dictionary;

/**
 * 主机
 *
 */
@Entity
@Table(name = "u_host")
public class Host extends System {
	private String host_name;
	private String host_type;
	private String serve_name;
	//private String serve_type;
	private Dictionary serve_type;// 账号状态@state_id

	public String getHost_type() {
		return host_type;
	}

	public void setHost_type(String host_type) {
		this.host_type = host_type;
	}

	public String getHost_name() {
		return host_name;
	}

	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}

	public String getServe_name() {
		return serve_name;
	}

	public void setServe_name(String serve_name) {
		this.serve_name = serve_name;
	}


	public void setServe_type(Dictionary serve_type) {
		this.serve_type = serve_type;
	}
	
	@ManyToOne
	@JoinColumn(name = "serve_type_id")
	public Dictionary getServe_type() {
		return serve_type;
	}
}
