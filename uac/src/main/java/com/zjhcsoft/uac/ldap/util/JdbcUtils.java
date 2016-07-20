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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Component;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.zjhcsoft.uac.account.user.entity.SubUser;
import com.zjhcsoft.uac.account.user.entity.User;
import com.zjhcsoft.uac.account.user.service.SubUserService;
import com.zjhcsoft.uac.account.user.service.UserService;
import com.zjhcsoft.uac.util.EncrypDES;

@Component
public class JdbcUtils extends AbstractPersonService {
	private static final Logger _logger = Logger.getLogger(JdbcUtils.class);

	
	private SubUserService subUserService;
	private UserService userService;
	

	/**
	 * 添加 一条记录
	 * 
	 * @param person
	 */
	public boolean createOnePerson(Person person) {
		return true;
	}

	

	/**
	 * 根据自定义的属性值查询person列表
	 * 
	 * @param person
	 * @return
	 */
	public List<Person> getPersonList(Person person) {
		List<Person> persons = new ArrayList<Person>();
		if(StringUtils.isNotEmpty(person.getParNode())){
			User s = new User();
			s.setUsername(person.getParNode());
			SubUser subUser = new SubUser();
			subUser.setUser(s);
			List<SubUser> subUsers =  subUserService.select(subUser);
			for(SubUser sub :subUsers){
				if(sub.getEnabled()){
					persons.add(new Person(sub));
				}
			}
			return persons;
		}else{
			User user = userService.findByUsername(person.getCn());
			persons.add(new Person(user));
			return persons;
		}
		
	}

	
	/**
	 * 删除一条记录，根据dn
	 * 
	 * @param cn
	 */
	public boolean removeOnePerson(Person person) {
		return true;
	}



	/**
	 * 修改操作
	 * 
	 * @param person
	 */
	public boolean updateOnePerson(Person person) {
		return true;
	}


	@Resource
	public void setSubUserService(SubUserService subUserService) {
		this.subUserService = subUserService;
	}


	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
	
	
	

}
