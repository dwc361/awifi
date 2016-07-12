package com.zjhcsoft.uac.authorization.resource.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.roof.web.dictionary.entity.Dictionary;

import com.zjhcsoft.uac.account.user.entity.SubUser;

/**
 * 资源
 * 
 * @author liuxin
 * 
 */
@Entity
@Table(name = "U_SYSRESOURCE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SequenceGenerator(name = "seq_sysresource", sequenceName = "SEQ_SYSRESOURCE")
public class SysResource implements Serializable {
	private static final long serialVersionUID = -5801764298560033266L;
	private Long id;
	private String name; // 资源名称
	private String describe;// 描述
	private Dictionary state;// 账号状态@state_id
	private String identify; // 标识(对端系统主键)
	private Date modifytime;// 最后修改时间
	private Dictionary region; // 所属域@region_id
	private List<SubUser> subUsers;// 从账号列表
	
	private String resourcetype;//资源类型
	
	public SysResource() {
		super();
	}

	public SysResource(Long id) {
		super();
		this.id = id;
	}

	@Id
	@GeneratedValue(generator = "seq_sysresource", strategy = GenerationType.SEQUENCE)
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescribe() {
		return describe;
	}
	
	public String getResourcetype() {
		return resourcetype;
	}

	@ManyToOne
	@JoinColumn(name = "state_id")
	public Dictionary getState() {
		return state;
	}

	@ManyToOne
	@JoinColumn(name = "region_id")
	public Dictionary getRegion() {
		return region;
	}

	public String getIdentify() {
		return identify;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setResourcetype(String resourcetype) {
		this.resourcetype = resourcetype;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public void setState(Dictionary state) {
		this.state = state;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	@Column(columnDefinition = "DATE")
	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	@ManyToMany
	@JoinTable(name = "SUB_USER_SYS_RESOURCE", joinColumns = { @JoinColumn(name = "SUB_USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "SYSRESOURCE_ID") })
	public List<SubUser> getSubUsers() {
		return subUsers;
	}

	public void setSubUsers(List<SubUser> subUsers) {
		this.subUsers = subUsers;
	}

	public void setRegion(Dictionary region) {
		this.region = region;
	}
}