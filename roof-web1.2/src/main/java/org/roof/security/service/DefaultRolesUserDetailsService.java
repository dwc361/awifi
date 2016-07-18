package org.roof.security.service;

import java.util.ArrayList;
import java.util.Collection;

import org.roof.dataaccess.RoofDaoSupport;
import org.roof.security.entity.BaseRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 加载用户并且加上默认的角色
 * 
 * @author liuxin
 *
 */
public class DefaultRolesUserDetailsService extends UserDetailsServiceImpl {
	private Long[] roleIds;
	private Collection<BaseRole> roles;
	private RoofDaoSupport roofDaoSupport;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = super.loadUserByUsername(username);
		loadDefaultRoles();
		@SuppressWarnings("unchecked")
		Collection<BaseRole> authorities = (Collection<BaseRole>) userDetails.getAuthorities();
		for (BaseRole grantedAuthority : roles) {
			if (!exists(authorities, grantedAuthority.getId())) {
				authorities.add(grantedAuthority);
			}
		}
		return userDetails;
	}

	private boolean exists(Collection<BaseRole> authorities, Long roleId) {
		for (BaseRole authority : authorities) {
			if (authority.getId().longValue() == roleId.longValue()) {
				return true;
			}
		}
		return false;
	}

	private void loadDefaultRoles() {
		if (roles == null) {
			roles = new ArrayList<BaseRole>();
			for (Long roleId : roleIds) {
				roles.add(roofDaoSupport.load(BaseRole.class, roleId));
			}
		}

	}

	public void setRoofDaoSupport(RoofDaoSupport roofDaoSupport) {
		this.roofDaoSupport = roofDaoSupport;
	}

	public RoofDaoSupport getRoofDaoSupport() {
		return roofDaoSupport;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

}
