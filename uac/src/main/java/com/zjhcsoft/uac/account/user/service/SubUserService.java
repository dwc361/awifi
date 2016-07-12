package com.zjhcsoft.uac.account.user.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.proxy.HibernateProxy;
import org.roof.commons.RoofStringUtils;
import org.roof.dataaccess.Page;
import org.roof.exceptions.ApplicationException;
import org.roof.exceptions.DaoException;
import org.roof.security.entity.BaseRole;
import org.roof.web.dictionary.dao.DictionaryDao;
import org.roof.web.role.entity.Roles;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjhcsoft.uac.account.user.dao.SubUserDao;
import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.SubUserTemp;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.authorization.resource.entity.App;
import com.zjhcsoft.uac.authorization.resource.entity.SysResource;
import com.zjhcsoft.uac.authorization.resource.entity.System;
import com.zjhcsoft.uac.blj.service.BljService;
import com.zjhcsoft.uac.cxf.SmsService;
import com.zjhcsoft.uac.ldap.util.LdapUtils;
import com.zjhcsoft.uac.ldap.util.Person;

/**
 * 自动生成
 */
@Component
@Transactional
public class SubUserService {
	public static final long NORMAL_USER_ID = 51L;// 普通用户ID

	private SubUserDao subUserDao;
	private LdapUtils ldapUtils;
	private SmsService smsService;
	private DictionaryDao dictionaryDao;

	private BljService bljService;

	/**
	 * 列表展示
	 */
	public Page querySubUserPage(SubUser paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new SubUser();
		}
		return subUserDao.querySubUserPage(paramObj, page);
	}

	/**
	 * 保存数据
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public SubUser save(SubUser paramObj) throws Exception {
		paramObj.setCn(UUID.randomUUID().toString());
		paramObj.setModifyTime(new Date());
		if (StringUtils.isNotEmpty(paramObj.getPassword())) {
			paramObj.setPassword(ldapUtils.encodeEncryptStringKey(paramObj.getPassword()));
		}
		fillParam(paramObj);
		if (App.SELF_ID.equals(paramObj.getSysResource().getId())) {
			paramObj.setRoles(createRole());
		}
		boolean flag = !hasSubUser(paramObj.getSysResource().getId(),paramObj.getUsername());
		subUserDao.save(paramObj);
		if (!ldapUtils.createOnePerson(new Person(paramObj))) {
			throw new Exception("子账号添加失败");
		}
		if(is_blj(paramObj)){
			if(flag){
				bljService.SubUseradd(paramObj);
			}
			bljService.Accessadd(paramObj);
		}
		return paramObj;
	}
	
	//public SubUser save(SubUser paramObj,boolean is_)

	/**
	 * 创建UAC用户
	 * 
	 * @param user
	 *            主账号
	 * @param id
	 *            主账号id
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public void createUacSubUser(User user, Long id)
			throws ApplicationException, Exception {
		SubUser subUser = new SubUser();
		subUser.setPrivilege(dictionaryDao.load("USER_PRIVILEG",
				"USER_PRIVILEG_USER"));
		subUser.setScope(dictionaryDao.load("USER_SCOPE", "USER_SCOPE_PAN"));
		subUser.setCreate_date(new Date());
		subUser.setSysResource(subUserDao.load(App.class, App.SELF_ID));
		subUser.setUser(user);
		subUser.setUsername(user.getUsername() + "#" + id);
		subUser.setName(user.getName());
		List<BaseRole> roleses = createRole();
		subUser.setRoles(roleses);
		save(subUser);
	}

	private List<BaseRole> createRole() {
		// 添加普通用户角色
		Roles roles = new Roles();
		roles.setId(NORMAL_USER_ID);
		List<BaseRole> roleses = new ArrayList<BaseRole>();
		roleses.add(roles);
		return roleses;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void fillParam(SubUser paramObj) {
		User u = subUserDao.load(User.class, paramObj.getUser().getId());
		paramObj.setUser(u);
		SysResource sysResource = (SysResource) subUserDao.load(SysResource.class, paramObj.getSysResource().getId(), false);
		if (sysResource == null) {
			sysResource = new SysResource();
			sysResource.setId(paramObj.getSysResource().getId());
		}
		paramObj.setSysResource(sysResource);
	}

	/**
	 * 查询数据
	 */
	public List<SubUser> select(SubUser paramObj) {
		List<SubUser> list = (List<SubUser>) subUserDao.findByMappedQuery(
				"SubUser_exp_select_subUser_list", paramObj);
		return list;
	}
	
	public Long selectSubUserByPar(String sub_code,String par_code) throws DaoException {
		return subUserDao.selectSubUserByPar(sub_code, par_code);
	}
	
	/**
	 * 查询数据
	 */
	public List<SubUser> selectTure(SubUser paramObj) {
		List<SubUser> list = (List<SubUser>) subUserDao.findByMappedQuery(
				"SubUser_exp_select_subUser_list_true", paramObj);
		return list;
	}
	
	

	/**
	 * 查询数据
	 */
	public SubUser selectObject(SubUser paramObj) {
		List<SubUser> list = this.select(paramObj);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new SubUser();
		}
	}

	/**
	 * 修改数据
	 */
	public SubUser update(SubUser paramObj) throws Exception {
		if (StringUtils.isNotEmpty(paramObj.getPassword())) {
			paramObj.setPassword(ldapUtils.encodeEncryptStringKey(paramObj.getPassword()));
		}
		subUserDao.update(paramObj);
		return paramObj;
	}

	/**
	 * 修改数据，忽略空值
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public SubUser updateIgnoreNull(SubUser paramObj) throws Exception {
		paramObj.setModifyTime(new Date());
		fillParam(paramObj);
		if (StringUtils.isNotEmpty(paramObj.getPassword())) {
			paramObj.setPassword(ldapUtils.encodeEncryptStringKey(paramObj.getPassword()));
		}
		paramObj = (SubUser) subUserDao.updateIgnoreNull(paramObj);
		
		if (!ldapUtils.updateOnePerson(new Person(paramObj))) {
			throw new Exception("子账号修改失败");
		}
		if(is_blj(paramObj)){
			bljService.SubUserupdate(paramObj);
		}
		return paramObj;
	}
	
	
	public boolean is_blj(SubUser subUser) {
		boolean mark = false;
		if(subUser.getSysResource() == null){
			return mark;
		}else if (subUser.getSysResource() instanceof System){
			mark = true;
		}else if(subUser.getSysResource() != null && subUser.getSysResource() instanceof HibernateProxy){
			 Object  realEntity= ((HibernateProxy)subUser.getSysResource()).getHibernateLazyInitializer().getImplementation();  
		      if (realEntity instanceof System) {  
		    	  mark = true;
		     }  
		} 
		return mark;
	}

	public void copyAllToLdap(Long user_id) throws Exception {
		List<SubUser> subUsers = subUserDao.loadEnable(user_id);
		for (SubUser subUser : subUsers) {
			copyToLdap(subUser);
		}
	}

	public void copyToLdap(SubUser paramObj) throws Exception {
		if (StringUtils.isEmpty(paramObj.getCn())) {
			paramObj.setCn(UUID.randomUUID().toString());
			subUserDao.update(paramObj);
		}
		if (!ldapUtils.createOnePerson(new Person(paramObj))) {
			throw new Exception("子账号修改失败");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(SubUser paramObj) throws Exception {
		paramObj = (SubUser) subUserDao.reload(paramObj);
		paramObj.setEnabled(false);
		paramObj.setModifyTime(new Date());
		boolean flag = !hasSubUser(paramObj.getSysResource().getId(),paramObj.getUsername());
		subUserDao.updateIgnoreNull(paramObj);
		if (!ldapUtils.removeOnePerson(new Person(paramObj))) {
			throw new Exception("子账号禁用失败");
		}
		if(is_blj(paramObj)){
			bljService.Accessdelete(paramObj);
			if(flag){
				bljService.SubUserdelete(paramObj);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void reuse(SubUser paramObj) throws Exception {
		paramObj = (SubUser) subUserDao.reload(paramObj);
		boolean flag = !hasSubUser(paramObj.getSysResource().getId(),paramObj.getUsername());
		paramObj.setEnabled(true);
		paramObj.setModifyTime(new Date());
		paramObj = (SubUser) subUserDao.updateIgnoreNull(paramObj);
		fillParam(paramObj);
		if (!ldapUtils.createOnePerson(new Person(paramObj))) {
			throw new Exception("子账号禁用失败");
		}
		if(is_blj(paramObj)){
			if(flag){
				bljService.SubUseradd(paramObj);
			}
			bljService.Accessadd(paramObj);
		}
	}

	/**
	 * 根据ID延迟加载持久化对象
	 */
	public SubUser load(Serializable id) {
		SubUser paramObj = (SubUser) subUserDao.load(SubUser.class, id);
		return paramObj;
	}

	/**
	 * 加载全部数据
	 */
	public List<SubUser> loadAll() throws Exception {
		return (List<SubUser>) subUserDao.loadAll(SubUser.class);
	}


	public void genScode(Long sysResource_id, String subUsername,User u) throws Exception {
		if (StringUtils.isBlank(subUsername)) {
			throw new Exception("用户名不能为空!");
		}
		if (subUserDao.countBindingSubUser(sysResource_id, subUsername) > 0L) {
			throw new Exception("该用户已经被绑定!");
		}
		String scode = RoofStringUtils.randomString(6);
		SubUserTemp subUserTemp = null;
		try {
			subUserTemp = subUserDao.findSubUserTemp(sysResource_id,
					subUsername);
		} catch (DaoException e) {
			throw ApplicationException.newInstance("DB00001",
					new String[] { "用户验证码" });
		}
		if (subUserTemp == null) {
			throw new Exception("用户名未找到");
		}
		if (subUserTemp.getSendTime() != null
				&& (new Date().getTime() - subUserTemp.getSendTime().getTime()) < (1000 * 60)) {
			throw new Exception("短信已经发送,请稍候再试");
		}
		subUserTemp.setSendTime(new Date());
		subUserTemp.setScode(scode);
		if("".equals(u.getTel())){
			throw new Exception("手机号码不能为空");
		}
		if(smsService.send(subUserTemp.getId().toString(), u.getUsername(), u.getTel(), scode)){
			subUserDao.update(subUserTemp);
		}else{
			throw new Exception("短信发送失败");
		}
		
	}
	
	public void VerifyScode(Long sysResource_id, String subUsername,String scode,User u) throws Exception {
		if (StringUtils.isBlank(subUsername)) {
			throw new Exception("用户名不能为空!");
		}
		if (subUserDao.countBindingSubUser(sysResource_id, subUsername) > 0L) {
			throw new Exception("该用户已经被绑定!");
		}
		SubUserTemp subUserTemp = null;
		try {
			subUserTemp = subUserDao.findSubUserTemp(sysResource_id, subUsername);
		} catch (DaoException e) {
			throw ApplicationException.newInstance("DB00001", new String[] { "用户验证码" });
		}
		if (subUserTemp == null) {
			throw new Exception("用户名未找到");
		}
		if (subUserTemp.getSendTime() != null
				&& (new Date().getTime() - subUserTemp.getSendTime().getTime()) < (1000 * 60)) {
			throw new Exception("验证码有效期已过");
		}
		if (subUserTemp.getScode() != null
				&& subUserTemp.getScode().equals(scode)) {
			SubUser subuser = new SubUser();
			subuser.setUsername(subUserTemp.getUsername());
			subuser.setSysResource(subUserTemp.getApp());
			subuser.setPrivilege(subUserTemp.getPrivilege());
			subuser.setScope(subUserTemp.getScope());
			subuser.setCreate_date(new Date());
			subuser.setUser(u);
			this.save(subuser);
		}else{
			throw new Exception("验证码错误");
		}
	}
	
	public void binding(Long sysResource_id, String subUsername) throws Exception {
		if (StringUtils.isBlank(subUsername)) {
			throw new Exception("用户名不能为空!");
		}
		if (subUserDao.countBindingSubUser(sysResource_id, subUsername) > 0L) {
			throw new Exception("该用户已经被绑定!");
		}
		
		SubUserTemp subUserTemp = null;
		try {
			subUserTemp = subUserDao.findSubUserTemp(sysResource_id, subUsername);
		} catch (DaoException e) {
			throw ApplicationException.newInstance("DB00001", new String[] { "用户验证码" });
		}
		if (subUserTemp == null) {
			throw new Exception("用户名未找到");
		}
		if (subUserTemp.getSendTime() != null
				&& (new Date().getTime() - subUserTemp.getSendTime().getTime()) < (1000 * 60)) {
			throw new Exception("验证码有效期已过，重新获取");
		}
		
	}
	
	
	public boolean hasSubUser(Long sysResource_id, String subUsername,Long userid) {
		long count = subUserDao.findUserCount(sysResource_id,subUsername,userid);
		return count > 0;
	}
	
	public boolean hasSubUser(Long sysResource_id, String subUsername) {
		long count = subUserDao.findUserCount(sysResource_id,subUsername);
		return count > 0;
	}


	@Resource
	public void setSubUserDao(SubUserDao subUserDao) {
		this.subUserDao = subUserDao;
	}

	@Resource
	public void setLdapUtils(LdapUtils ldapUtils) {
		this.ldapUtils = ldapUtils;
	}

	@Resource
	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}
	
	@Resource
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}
	
	@Resource
	public void setBljService(BljService bljService) {
		this.bljService = bljService;
	}
}
