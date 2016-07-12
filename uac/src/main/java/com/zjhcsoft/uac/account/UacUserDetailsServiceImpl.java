package com.zjhcsoft.uac.account;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.security.provisioning.UserDetailsManager;

import com.zjhcsoft.uac.authorization.resource.entity.App;

/**
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 UserDetailsServiceImpl.java 2012-7-4
 */
public class UacUserDetailsServiceImpl implements UserDetailsService,
		UserDetailsManager, GroupManager {

	private static final Logger logger = Logger
			.getLogger(UacUserDetailsServiceImpl.class);

	private HibernateTemplate hibernateTemplate;

	private static final String DEFUALT_USERS_BY_USERNAME_QUERY = "from SubUser where username = ? and sysResource.id = ?";

	private String usersByUsernameQuery = DEFUALT_USERS_BY_USERNAME_QUERY;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserDetails user = null;
		@SuppressWarnings("unchecked")
		List<UserDetails> users = hibernateTemplate.find(usersByUsernameQuery,
				username, App.SELF_ID);
		if (users == null || users.size() == 0) {
			if (logger.isInfoEnabled()) {
				logger.info("loadUserByUsername(String) - 没有找到用户名 [" + username
						+ "]");
			}
			throw new UsernameNotFoundException("没有找到用户名[" + username + "]");
		}
		user = users.get(0);
		return user;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public List<String> findAllGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findUsersInGroup(String groupName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createGroup(String groupName, List<GrantedAuthority> authorities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteGroup(String groupName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renameGroup(String oldName, String newName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUserToGroup(String username, String group) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeUserFromGroup(String username, String groupName) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<GrantedAuthority> findGroupAuthorities(String groupName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addGroupAuthority(String groupName, GrantedAuthority authority) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeGroupAuthority(String groupName,
			GrantedAuthority authority) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createUser(UserDetails user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUser(UserDetails user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean userExists(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getUsersByUsernameQuery() {
		return usersByUsernameQuery;
	}

	public void setUsersByUsernameQuery(String usersByUsernameQuery) {
		this.usersByUsernameQuery = usersByUsernameQuery;
	}

}
