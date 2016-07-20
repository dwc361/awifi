package org.roof.web.resources.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.roof.security.entity.Resource;

/**
 * 模块
 * 
 * @author liuxin
 * 
 */
@Entity
@DiscriminatorValue("module")
public class Module extends Resource {

	protected String identify; // 标识
	protected String path;// 路径
	protected Integer seq;// 顺序
	protected Integer lvl;// 等级
	protected Resource parent; // 父节点
	protected Boolean leaf;// 是否为叶子节点

	@Column(name = "IDENTIFY")
	public String getIdentify() {
		return identify;
	}

	@Column(name = "PATH")
	public String getPath() {
		return path;
	}

	@Column(name = "SEQ")
	public Integer getSeq() {
		return seq;
	}

	@Column(name = "LVL")
	public Integer getLvl() {
		return lvl;
	}

	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	public Resource getParent() {
		return parent;
	}

	@Column(name = "LEAF")
	public Boolean getLeaf() {
		return leaf;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public void setLvl(Integer lvl) {
		this.lvl = lvl;
	}

	public void setParent(Resource parent) {
		this.parent = parent;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

}
