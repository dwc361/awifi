package com.zjhcsoft.uac.ldap.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.roof.commons.PropertiesUtil;
import org.roof.commons.RoofDateUtils;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.web.dictionary.entity.Dictionary;
import org.springframework.ldap.core.AuthenticationSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Component;

import com.zjhcsoft.uac.account.user.dao.SubUserDao;
import com.zjhcsoft.uac.account.user.dao.UserDao;
import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.ldap.util.LdapUtils;
import com.zjhcsoft.uac.ldap.util.Person;

@Component
public class LdapSynchronousService {
	private LdapUtils ldapUtils;
	private RoofDaoSupport roofDaoSupport;
	private SubUserDao subUserDao;
	private UserDao userDao;
	
	private static final LdapTemplate ldapTemplate1;
	private static final LdapTemplate ldapTemplate2;
	static {
		LdapContextSource lcs1 = new LdapContextSource();
		lcs1.setCacheEnvironmentProperties(false);
		lcs1.setUrl(PropertiesUtil.getPorpertyString("ldap.url"));
		lcs1.setBase(PropertiesUtil.getPorpertyString("ldap.baseDN"));
		lcs1.setAuthenticationSource(new AuthenticationSource() {
			@Override
			public String getCredentials() {
				return PropertiesUtil.getPorpertyString("ldap.password");
			}

			@Override
			public String getPrincipal() {
				return PropertiesUtil.getPorpertyString("ldap.userDn");
			}
		});
		ldapTemplate1 = new LdapTemplate(lcs1);

		LdapContextSource lcs2 = new LdapContextSource();
		lcs2.setCacheEnvironmentProperties(false);
		lcs2.setUrl(PropertiesUtil.getPorpertyString("ldap2.url"));
		lcs2.setBase(PropertiesUtil.getPorpertyString("ldap2.baseDN"));
		lcs2.setAuthenticationSource(new AuthenticationSource() {
			@Override
			public String getCredentials() {
				return PropertiesUtil.getPorpertyString("ldap2.password");
			}

			@Override
			public String getPrincipal() {
				return PropertiesUtil.getPorpertyString("ldap2.userDn");
			}
		});
		ldapTemplate2 = new LdapTemplate(lcs2);
	}

	List<String> result = new ArrayList<String>();

	@SuppressWarnings("unchecked")
	public List<String> DbToLdap() {
		result = new ArrayList<String>();
		Dictionary dictionary = new Dictionary();
		dictionary.setType("LDAP_SYNCHRONOUS");
		List<Dictionary> sqlList = (List<Dictionary>) roofDaoSupport.findByExample(dictionary);
		for (Dictionary dic : sqlList) {
			if ("1".equals(dic.getActive())) {
				dic.setActive("0");
				dic.setDescription(RoofDateUtils.dateToString(new Date()));
				roofDaoSupport.updateIgnoreNull(dic);

				String hql = dic.getText();
				String countSql = "select count(*) " + hql;
				int count = Integer.valueOf(roofDaoSupport.findForList(countSql).get(0).toString());
				int idx = 0;
				while (true) {
					List<User> list = (List<User>) roofDaoSupport.findForList(hql, idx, 20);
					for (User user : list) {
						this.copyMainsToLdap(user, ldapTemplate1);
						this.copyMainsToLdap(user, ldapTemplate2);
					}
					idx += 20;
					if (idx > count) {
						break;
					}
				}
				result.add(hql + "总共同步了" + count + "条主账号记录，请确认!");

				dic.setActive("1");
				roofDaoSupport.updateIgnoreNull(dic);
			}
		}
		return result;
	}
	
	public void DbToLdap(User u) {
		List<User> list = userDao.select(u);
		for (User user : list) {
			this.copyMainsToLdap(user, ldapTemplate1);
			this.copyMainsToLdap(user, ldapTemplate2);
		}
	}

	private void copyMainsToLdap(User user, LdapTemplate ldapTemplate) {
		try {
			Person person = new Person(user);
			Person find = new Person();
			find.setCn(person.getCn());
			find.setParNode(person.getParNode());
			List<Person> mp = ldapUtils.getPersonList(find, ldapTemplate);
			if ((mp.size() == 0) && (user.isEnabled())) {// 主账号不存在 且 启用
				boolean b = ldapUtils.createOnePerson(person, ldapTemplate);
				if (!b) {
					result.add("新增" + user.getUsername() + "发生异常!");
				}
				this.copySubsToLdap(user.getId(), ldapTemplate);// 后同步子账号
			} else if ((mp.size() > 0) && (user.isEnabled())) {// 主账号存在 且 启用
				boolean b = ldapUtils.updateOnePerson(person, ldapTemplate);
				if (!b) {
					result.add("修改" + user.getUsername() + "发生异常!");
				}
				this.copySubsToLdap(user.getId(), ldapTemplate);// 后同步子账号
			} else if ((mp.size() > 0) && (!user.isEnabled())) {// 主账号存在 且 禁用
				this.copySubsToLdap(user.getId(), ldapTemplate);// 先同步子账号
				boolean b = ldapUtils.removeOnePerson(find, ldapTemplate);
				if (!b) {
					result.add("删除" + user.getUsername() + "发生异常!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void copySubsToLdap(Long user_id, LdapTemplate ldapTemplate) {
		List<SubUser> subUsers = subUserDao.loadAllSubs(user_id);
		for (SubUser subu : subUsers) {
			try {
				if (StringUtils.isEmpty(subu.getCn())) {
					subu.setCn(UUID.randomUUID().toString());
					subUserDao.update(subu);
				}
				Person person = new Person(subu);
				Person find = new Person();
				find.setCn(person.getCn());
				find.setParNode(person.getParNode());
				List<Person> sp = ldapUtils.getPersonList(find, ldapTemplate);
				if ((sp.size() == 0) && (subu.isEnabled())) {// 从账号不存在 且 启用
					boolean b = ldapUtils.createOnePerson(person, ldapTemplate);
					if (!b) {
						result.add("新增" + subu.getUsername() + "发生异常!");
					}
				} else if ((sp.size() > 0) && (subu.isEnabled())) {// 从账号存在 且 启用
					boolean b = ldapUtils.updateOnePerson(person, ldapTemplate);
					if (!b) {
						result.add("修改" + subu.getUsername() + "发生异常!");
					}
				} else if ((sp.size() > 0) && (!subu.isEnabled())) {// 存在 且 禁用
					boolean b = ldapUtils.removeOnePerson(find, ldapTemplate);
					if (!b) {
						result.add("删除" + subu.getUsername() + "发生异常!");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Resource
	public void setLdapUtils(LdapUtils ldapUtils) {
		this.ldapUtils = ldapUtils;
	}

	@Resource
	public void setRoofDaoSupport(RoofDaoSupport roofDaoSupport) {
		this.roofDaoSupport = roofDaoSupport;
	}

	@Resource
	public void setSubUserDao(SubUserDao subUserDao) {
		this.subUserDao = subUserDao;
	}

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
