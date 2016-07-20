package org.roof.security.entity;

import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

/**
 * 角色
 * 
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 RoofRole.java 2012-7-5
 */
@Entity
@Table(name = "ROLES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BaseRole implements GrantedAuthority {

	private static final long serialVersionUID = 7539222051677360527L;
	protected Long id;
	protected String name;
	protected List<Resource> resources; // 可以访问的资源

	public BaseRole() {
		super();
	}

	public BaseRole(Long id) {
		super();
		this.id = id;
	}

	public BaseRole(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Id
	@Column(name = "ROLE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ROLE_AUTH_REL", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "PRIVILEGE_ID") })
	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	@Override
	@Transient
	public String getAuthority() {
		return "ROLE_" + id.toString();
	}

	public void setAuthority(String authority) {

	}

	@Column(name = "ROLE_NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseRole other = (BaseRole) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
