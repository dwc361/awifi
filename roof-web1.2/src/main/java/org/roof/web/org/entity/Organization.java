package org.roof.web.org.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 组织架构
 * 
 * @author liuxin
 * 
 */
@Entity
@Table(name = "ORGANIZATION")
@SequenceGenerator(name = "seq_organization", sequenceName = "SEQ_ORGANIZATION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Organization implements Serializable {
	private static final long serialVersionUID = -6499305660200043952L;
	protected Long org_id;// id
	protected String org_name;// 组织名称
	protected Organization parent;// 父节点@PARENT_ID
	protected Integer lvl; // 层级
	protected Integer seq;// 排序
	protected Boolean leaf;// 是否为叶子节点
	protected Boolean usable; // 是否可用(逻辑删除)
	private String alias;// 简称

	public Organization() {
		super();
	}

	public Organization(Long id) {
		super();
		this.org_id = id;
	}

	public Organization(Long currId, Long parId) {
		super();
		this.org_id = currId;
		this.setParent(new Organization(parId));
	}

	@Id
	@Column(name = "ORG_ID")
	@GeneratedValue(generator = "seq_organization", strategy = GenerationType.SEQUENCE)
	public Long getOrg_id() {
		return org_id;
	}

	public String getOrg_name() {
		return org_name;
	}

	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	public Organization getParent() {
		return parent;
	}

	@Column(name = "LVL")
	public Integer getLvl() {
		return lvl;
	}

	@Column(name = "LEAF", columnDefinition = "char(1)")
	public Boolean getLeaf() {
		return leaf;
	}

	@Column(name = "USABLE", columnDefinition = "char(1) default '1'")
	public Boolean getUsable() {
		return usable;
	}

	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}

	public void setLvl(Integer lvl) {
		this.lvl = lvl;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public void setUsable(Boolean usable) {
		this.usable = usable;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
