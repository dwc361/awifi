package org.roof.security.entity;

import java.util.Collection;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户
 * 
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 RoofUser.java 2012-7-5
 */
@Entity
@Table(name = "STAFF")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SequenceGenerator(name = "seq_s_user", sequenceName = "SEQ_S_USER", initialValue = 10000)
public class BaseUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	protected Long id;

	protected String username;// 用户名（登录工号）
	protected String password;// 密码
	protected boolean accountNonExpired = true; // 是否过期
	protected boolean accountNonLocked = true; // 是否锁定
	protected boolean credentialsNonExpired = true; // 密码是否被锁定
	protected boolean enabled = true; // 是否可用

	protected List<BaseRole> roles;

	@Id
	@Column(name = "STAFF_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_s_user")
	public Long getId() {
		return id;
	}

	@Override
	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}

	@Transient
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
	}

	@Override
	@Column(name = "STAFF_CODE")
	public String getUsername() {
		return username;
	}

	@Override
	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	@Override
	@Column(name = "ACCOUNTNONEXPIRED")
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	@Column(name = "ACCOUNTNONLOCKED")
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	@Column(name = "CREDENTIALSNONEXPIRED")
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	@Column(name = "ENABLED")
	public boolean isEnabled() {
		return enabled;
	}

	@ManyToMany
	@JoinTable(name = "STAFF_ROLE", joinColumns = { @JoinColumn(name = "STAFF_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	public List<BaseRole> getRoles() {
		return roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setRoles(List<BaseRole> roles) {
		this.roles = roles;
	}

	public void setId(Long id) {
		this.id = id;
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
		BaseUser other = (BaseUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
