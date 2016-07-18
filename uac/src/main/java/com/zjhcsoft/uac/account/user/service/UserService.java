package com.zjhcsoft.uac.account.user.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.roof.exceptions.ApplicationException;
import org.roof.exceptions.DaoException;
import org.roof.web.org.dao.OrgDao;
import org.roof.web.org.entity.Organization;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjhcsoft.uac.account.user.dao.SubUserDao;
import com.zjhcsoft.uac.account.user.dao.UserDao;
import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.ldap.util.Person;
import com.zjhcsoft.uac.ldap.util.PersonServiceI;

@Component
@Transactional
public class UserService {

	private UserDao userDao;
	private PersonServiceI ldapUtils;
	private SubUserService subUserService;
	private OrgDao orgDao;
	private SubUserDao subUserDao;

	private static final Logger LOGGER = Logger.getLogger(UserService.class);

	/**
	 * 查询数据
	 */
	public List<User> select(User paramObj) {
		return userDao.select(paramObj);
	}
	
	public boolean hasName(String name) {
		User user = new User();
		user.setName(name);
		long count = userDao.findUserCount(user);
		return count > 0;
	}

	public boolean hasUserName(String username) {
		User user = new User();
		user.setUsername(username);
		long count = userDao.findUserCount(user);
		return count > 0;
	}

	public User findByUsername(String username) {
		try {
			return userDao.findByUsername(username);
		} catch (DaoException e) {
			LOGGER.error(e, e);
		}
		return null;
	}

	public boolean hasIdNumber(String idNumber) {
		User user = new User();
		user.setIdNumber(idNumber);
		long count = userDao.findUserCount(user);
		return count > 0;
	}

	public User findIdNumber(String idNumber) {
		try {
			return userDao.findByIdNumber(idNumber);
		} catch (DaoException e) {
			LOGGER.error(e, e);
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(User user,boolean is_id) throws Exception {
		validate(user,is_id);
		user.setCreate_date(new Date());
		validOrg(user);
		if (StringUtils.isNotEmpty(user.getPassword())) {
			user.setPassword(ldapUtils.encodePwdSHA(user.getPassword()));
			user.setPwdSetTime(new Date());
		}
		Long id = (Long) userDao.save(user);
		copyToLdap(user);

		subUserService.createUacSubUser(user, id);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(User user) throws Exception  {
		this.save(user, true);
	}

	/**
	 * 验证用户是否能被新增
	 * 
	 * @param user
	 * @throws ApplicationException
	 */
	public void validate(User user,boolean is_id) throws ApplicationException {
		if (hasUserName(user.getName())) { // 相同的用户名
			throw ApplicationException.newInstance("BL10001");
		}
		if (is_id && StringUtils.isBlank(user.getIdNumber())) { // 身份证为空
			throw ApplicationException.newInstance("BL10002");
		}
		if (is_id && hasIdNumber(user.getIdNumber())) { // 省份证号已经被使用
			throw ApplicationException.newInstance("BL10003");
		}
	}

	public void copyToLdap(User user) throws Exception {
		Person person = new Person(user);
		if(ldapUtils.getPersonList(person).size() == 0){
			boolean b = ldapUtils.createOnePerson(person);
			if (!b) {
				throw new Exception("用户创建失败");
			}
		}
		subUserService.copyAllToLdap(user.getId());
	}

	private void validOrg(User user) {
		Organization org = (Organization) orgDao.reload(user.getOrg());
		if (org == null) {
			org = new Organization();
			org.setOrg_id(50L);
		}
		user.setOrg(org);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(User user) throws Exception {
		if (StringUtils.isNotEmpty(user.getPassword())) {
			user.setPassword(ldapUtils.encodePwdSHA(user.getPassword()));
			user.setPwdSetTime(new Date());
		}
		user = (User) userDao.updateIgnoreNull(user);
		Person person = new Person(user);
		boolean b = ldapUtils.updateOnePerson(person);
		if (!b) {
			throw new Exception("用户更新失败");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void reset_pwd(User user) throws Exception {
		if (StringUtils.isNotEmpty(user.getPassword())) {
			user.setPassword(ldapUtils.encodePwdSHA(user.getPassword()));
			user.setPwdSetTime(new Date());
		}
		user = (User) userDao.updateIgnoreNull(user);
		user.setUpdate_time(null);
		userDao.update(user);
		Person person = new Person(user);
		boolean b = ldapUtils.updateOnePerson(person);
		if (!b) {
			throw new Exception("用户更新失败");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update_username(User user) throws Exception {
		String username = user.getUsername();
		
		user = userDao.findByIdNumber(user.getIdNumber());
		List<SubUser> sublist =  subUserDao.loadEnable(user.getId());
		this.delete(user);
		
		user.setUsername(username);
		user.setTel(username);
		this.reuse(user);
		
		for(SubUser sub:sublist){
			sub.setModifyTime(new Date());
			sub.setUser(user);
			subUserService.reuse(sub);
		}
	
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(User user) throws Exception {
		deleteSubUsers(user);// 禁用所有子账号
		user.setEnabled(false);
		user = (User) userDao.updateIgnoreNull(user);
		Person person = new Person(user);
		boolean b = ldapUtils.removeOnePerson(person);
		if (!b) {
			throw new Exception("用户停用失败");
		}
	}

	private void deleteSubUsers(User user) throws Exception {
		List<SubUser> subUsers = subUserDao.loadEnable(user.getId());
		for (SubUser subUser : subUsers) {
			subUserService.delete(subUser);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void reuse(User user) throws Exception {
		user.setEnabled(true);
		user = (User) userDao.updateIgnoreNull(user);
		Person person = new Person(user);
		boolean b = ldapUtils.createOnePerson(person);
		if (!b) {
			throw new Exception("用户启用失败");
		}
	}

	/**
	 * 验证旧密码
	 * 
	 * @param user
	 * @return
	 */
	public Boolean validateOldPassword(User user) {
		if (StringUtils.isNotEmpty(user.getPassword())) {
			user.setPassword(ldapUtils.encodePwdSHA(user.getPassword()));
		}
		return (userDao.select(user).size() == 1);
	}

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Resource
	public void setLdapUtils(PersonServiceI ldapUtils) {
		this.ldapUtils = ldapUtils;
	}

	@Resource
	public void setSubUserService(SubUserService subUserService) {
		this.subUserService = subUserService;
	}

	@Resource
	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	@Resource
	public void setSubUserDao(SubUserDao subUserDao) {
		this.subUserDao = subUserDao;
	}
}
