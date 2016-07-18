package com.zjhcsoft.uac.ldap.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.roof.commons.PropertiesUtil;
import org.roof.commons.RoofStringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Component;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.zjhcsoft.uac.util.EncrypDES;


public class LdapUtils extends AbstractPersonService{
	private static final Logger _logger = Logger.getLogger(LdapUtils.class);
	private LdapTemplate ldapTemplate;

	/**
	 * 添加 一条记录
	 * 
	 * @param person
	 */
	public boolean createOnePerson(Person person) {
		return this.createOnePerson(person, ldapTemplate);
	}

	/**
	 * 添加 一条记录
	 * 
	 * @param person
	 */
	public boolean createOnePerson(Person person, LdapTemplate ldapTemplate) {
		try {
			BasicAttribute ba = new BasicAttribute("objectclass");
			ba.add("person"); // 此处的person对应的是core.schema文件中的objectclass：person
			Attributes attr = new BasicAttributes();
			attr.put(ba);
			String[] fields = PersonAttributeMapper.fields;
			for (int i = 0; i < fields.length; i++) {
				try {
					Class[] clazzArr = null;
					Method method = Person.class.getDeclaredMethod("get" + RoofStringUtils.firstUpperCase(fields[i]),
							clazzArr);
					Object[] argsArr = null;
					Object val = method.invoke(person, argsArr);
					if (val != null) {
						attr.put(fields[i], val);
					}
				} catch (Exception e) {
					e.printStackTrace();
					_logger.error(e);
				}
			}
			// bind方法即是添加一条记录。
			ldapTemplate.bind(this.getDn(person), null, attr);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
		}
		return false;
	}

	/**
	 * 根据自定义的属性值查询person列表
	 * 
	 * @param person
	 * @return
	 */
	public List<Person> getPersonList(Person person) {
		return this.getPersonList(person, ldapTemplate);
	}

	/**
	 * 根据自定义的属性值查询person列表
	 * 
	 * @param person
	 * @return
	 */
	public List<Person> getPersonList(Person person, LdapTemplate ldapTemplate) {
		List<Person> list = new ArrayList<Person>();
		try {
			// 查询过滤条件
			AndFilter andFilter = new AndFilter();
			andFilter.and(new EqualsFilter("objectclass", "person"));

			String[] fields = PersonAttributeMapper.fields;
			for (int i = 0; i < fields.length; i++) {
				if ("userPassword".equals(fields[i])) {
					continue;
				}
				if ("parNode".equals(fields[i])) {
					continue;
				}
				try {
					Class[] clazzArr = null;
					Method method = Person.class.getDeclaredMethod("get" + RoofStringUtils.firstUpperCase(fields[i]),
							clazzArr);
					Object[] argsArr = null;
					Object val = method.invoke(person, argsArr);
					if (val != null) {
						andFilter.and(new EqualsFilter(fields[i], (String) val));
					}
				} catch (Exception e) {
					e.printStackTrace();
					_logger.error(e);
				}
			}

			// search是根据过滤条件进行查询，第一个参数是父节点的dn，可以为空，不为空时查询效率更高
			String findRoot = "";
			if (StringUtils.isNotEmpty(person.getParNode())) {
				findRoot = "cn=" + person.getParNode();
			}
			list = ldapTemplate.search(findRoot, andFilter.encode(), SearchControls.ONELEVEL_SCOPE,
					new PersonAttributeMapper());
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
		}
		return list;
	}

	/**
	 * 删除一条记录，根据dn
	 * 
	 * @param cn
	 */
	public boolean removeOnePerson(Person person) {
		return this.removeOnePerson(person, ldapTemplate);
	}

	/**
	 * 删除一条记录，根据dn
	 * 
	 * @param cn
	 */
	public boolean removeOnePerson(Person person, LdapTemplate ldapTemplate) {
		try {
			ldapTemplate.unbind(super.getDn(person));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
		}
		return false;
	}

	/**
	 * 修改操作
	 * 
	 * @param person
	 */
	public boolean updateOnePerson(Person person) {
		return this.updateOnePerson(person, ldapTemplate);
	}

	/**
	 * 修改操作
	 * 
	 * @param person
	 */
	public boolean updateOnePerson(Person person, LdapTemplate ldapTemplate) {
		try {
			if (person == null || StringUtils.isEmpty(person.getCn())) {
				return false;
			}
			int attrId = DirContext.REPLACE_ATTRIBUTE;
			List<ModificationItem> mList = new ArrayList<ModificationItem>();

			String[] fields = PersonAttributeMapper.fields;
			for (int i = 0; i < fields.length; i++) {
				if ("cn".equals(fields[i])) {
					continue;
				}
				if ("parNode".equals(fields[i])) {
					continue;
				}
				try {
					Class[] clazzArr = null;
					Method method = Person.class.getDeclaredMethod("get" + RoofStringUtils.firstUpperCase(fields[i]),
							clazzArr);
					Object[] argsArr = null;
					Object val = method.invoke(person, argsArr);
					if (val != null) {
						mList.add(new ModificationItem(attrId, new BasicAttribute(fields[i], val)));
					}
				} catch (Exception e) {
					e.printStackTrace();
					_logger.error(e);
				}
			}

			if (mList.size() > 0) {
				ModificationItem[] mArray = new ModificationItem[mList.size()];
				for (int i = 0; i < mList.size(); i++) {
					mArray[i] = mList.get(i);
				}
				// modifyAttributes 方法是修改对象的操作，与rebind（）方法需要区别开
				ldapTemplate.modifyAttributes(this.getDn(person), mArray);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
		}
		return false;
	}

	/**
	 * 改变base
	 * 
	 * @param person
	 */
	@Deprecated
	private void changeBase(Person person) {
		String base = PropertiesUtil.getPorpertyString("ldap.baseDN");
		LdapContextSource ctx = (LdapContextSource) ldapTemplate.getContextSource();
		if (person.getParNode() != null) {
			ctx.setBase("cn=" + person.getParNode() + "," + base);
		} else {
			ctx.setBase(base);
		}
	}

	@Resource
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

}
