package org.roof.web.menu.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.roof.web.resources.entity.Module;

@Entity
@Table(name = "S_MENU")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name = "seq_s_menu", sequenceName = "SEQ_S_MENU")
public class Menu {
	private Long id;
	private String name;
	private Integer lvl;
	private Boolean leaf;
	private Menu parent;
	private List<Menu> children;
	private Module module;
	private String target;
	private MenuType menuType;
	private String script;
	private String icon;
	private Integer seq;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_s_menu")
	public Long getId() {
		return id;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	@Column(name = "LVL")
	public Integer getLvl() {
		return lvl;
	}

	@Column(name = "LEAF", columnDefinition = "char(1)")
	public Boolean getLeaf() {
		return leaf;
	}

	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	public Menu getParent() {
		return parent;
	}

	@OneToMany(mappedBy = "parent")
	public List<Menu> getChildren() {
		return children;
	}

	@ManyToOne
	@JoinColumn(name = "PRIVILEGE_ID")
	public Module getModule() {
		return module;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLvl(Integer lvl) {
		this.lvl = lvl;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Enumerated(EnumType.STRING)
	public MenuType getMenuType() {
		return menuType;
	}

	public void setMenuType(MenuType menuType) {
		this.menuType = menuType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
