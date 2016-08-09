package org.roof.security.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 资源
 * 
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 Resource.java 2011-11-9
 */
@Entity
@Table(name = "privilege")
@DiscriminatorValue("resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resource {

	protected Long id;
	protected String name;
	protected String pattern;
	protected String description;
	protected List<BaseRole> baseRole;

	@Id
	@Column(name = "PRIVILEGE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Column(name = "PRIVILEGE_NAME")
	public String getName() {
		return name;
	}

	@Column(name = "PATTERN")
	public String getPattern() {
		return pattern;
	}

	@ManyToMany(mappedBy = "resources", fetch = FetchType.EAGER)
	public List<BaseRole> getBaseRole() {
		return baseRole;
	}

	@Column(name = "PRIVILEGE_DESC")
	public String getDescription() {
		return description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBaseRole(List<BaseRole> baseRole) {
		this.baseRole = baseRole;
	}

}