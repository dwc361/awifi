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

@Component
public class LdapUtils {
	private static final Logger _logger = Logger.getLogger(LdapUtils.class);
	private LdapTemplate ldapTemplate;
	private Key key;

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
			ldapTemplate.unbind(this.getDn(person));
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
	 * 校验密码
	 * 
	 * @param ldappw
	 * @param inputpw
	 * @return
	 */
	public boolean verifySHA(String ldappw, String inputpw) {
		try {
			// MessageDigest 提供了消息摘要算法，如 MD5 或 SHA，的功能，这里LDAP使用的是SHA-1
			MessageDigest md = MessageDigest.getInstance("SHA-1");

			// 取出加密字符
			ldappw = ldappw.substring(ldappw.indexOf("}") + 1);

			// 解码BASE64
			byte[] ldappwbyte = Base64.decode(ldappw);
			byte[] shacode;
			byte[] salt;

			// 前20位是SHA-1加密段，20位后是最初加密时的随机明文
			if (ldappwbyte.length <= 20) {
				shacode = ldappwbyte;
				salt = new byte[0];
			} else {
				shacode = new byte[20];
				salt = new byte[ldappwbyte.length - 20];
				System.arraycopy(ldappwbyte, 0, shacode, 0, 20);
				System.arraycopy(ldappwbyte, 20, salt, 0, salt.length);
			}

			// 把用户输入的密码添加到摘要计算信息
			md.update(inputpw.getBytes());
			// 把随机明文添加到摘要计算信息
			md.update(salt);

			// 按SSHA把当前用户密码进行计算
			byte[] inputpwbyte = md.digest();

			// 返回校验结果
			return MessageDigest.isEqual(shacode, inputpwbyte);
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
		}
		return false;
	}

	/**
	 * 用户认证
	 * 
	 * @param usr
	 * @param pwd
	 * @return
	 */
	public boolean authenticate(Person person) {
		boolean flag = false;
		List<Person> list = this.getPersonList(person);
		if (list.size() > 0) {
			Person find = list.get(0);
			if (this.verifySHA(find.getUserPassword(), person.getUserPassword())) {
				return true;
			}
		}
		return flag;
	}

	/**
	 * 得到dn
	 * 
	 * @param cn
	 * @return
	 */
	private DistinguishedName getDn(Person person) {
		// 得到根目录，也就是配置文件中配置的ldap的根目录
		DistinguishedName newContactDN = new DistinguishedName();
		// 添加cn，即使得该条记录的dn为"cn=cn,根目录",例如"cn=abc,dc=testdc,dc=com"
		if (person.getParNode() != null) {
			newContactDN = new DistinguishedName("cn=" + person.getParNode());
		}
		newContactDN.add("cn", person.getCn());
		return newContactDN;
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

	/**
	 * 将摘要信息转换为相应的编码
	 * 
	 * @param code
	 *            编码类型(MD5,SHA,SHA-256,SHA-512)
	 * @param pwd
	 *            摘要信息
	 * @return 相应的编码字符串
	 */
	private String encodePwd(String code, String pwd) {
		MessageDigest md;
		String encode = null;
		try {
			md = MessageDigest.getInstance(code);
			encode = Base64.encode(md.digest(pwd.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			_logger.error(e);
		}
		return encode;
	}

	/**
	 * 将摘要信息转换为SHA编码
	 * 
	 * @param pwd
	 * @return
	 */
	public String encodePwdSHA(String pwd) {
		return "{sha}" + this.encodePwd("SHA", pwd);
	}

	private Key readKey() throws IOException {
		if (key == null) {
			org.springframework.core.io.Resource resource = new ClassPathResource("key.des");
			File file = resource.getFile();
			key = EncrypDES.readKey(file);
		}
		return key;
	}

	/**
	 * 密码加密(可逆算法)
	 * 
	 * @param pwd
	 * @return
	 */
	public String encodeEncryptStringKey(String pwd) {
		String r = "";
		try {
			r = EncrypDES.encrypt(pwd, readKey());
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
		}
		return r;
	}

	/**
	 * 密码解密(可逆算法)
	 * 
	 * @param pwd
	 * @return
	 */
	public String decodeEncryptStringKey(String pwd) {
		String r = "";
		try {
			r = EncrypDES.decrypt(pwd, readKey());
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e);
		}
		return r;
	}

	@Resource
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

}
