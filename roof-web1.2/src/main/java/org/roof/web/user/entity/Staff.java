package org.roof.web.user.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.roof.log.SysLoginLog;
import org.roof.security.entity.BaseUser;
import org.roof.web.org.entity.Organization;

/**
 * 用户
 * 
 * @author liuxin
 * 
 */
@Entity
public class Staff extends BaseUser {
	private static final long serialVersionUID = 1L;
	private String name;// 用户名称（中文）
	private Date create_date;// 创建时间
	private Date update_time; // 信息修改时间
	private Organization org;// 组织架构@ORG_ID
	private SysLoginLog sysLoginLog;// 最新成功的登录日志@SYS_LOGIN_LOG_ID
	private Long login_count;// 成功登录次数
	private List<Long> orgIds;

	public Staff() {
		super();
	}

	public Staff(Long id) {
		this.id = id;
	}

	@Column(name = "STAFF_NAME")
	public String getName() {
		return name;
	}

	@Column(name = "CREATE_DATE", columnDefinition = "DATE")
	public Date getCreate_date() {
		return create_date;
	}

	@ManyToOne
	@JoinColumn(name = "ORG_ID")
	public Organization getOrg() {
		return org;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	@Transient
	public List<Long> getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(List<Long> orgIds) {
		this.orgIds = orgIds;
	}

	@Column(columnDefinition = "DATE")
	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	@Transient
	public Long getStaff_id() {
		return id;
	}

	public void setStaff_id(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "SYS_LOGIN_LOG_ID")
	public SysLoginLog getSysLoginLog() {
		return sysLoginLog;
	}

	public void setSysLoginLog(SysLoginLog sysLoginLog) {
		this.sysLoginLog = sysLoginLog;
	}

	public Long getLogin_count() {
		return login_count;
	}

	public void setLogin_count(Long login_count) {
		this.login_count = login_count;
	}
}
