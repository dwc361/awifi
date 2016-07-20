package com.zjhcsoft.uac.ldap.util;

import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.log4j.Logger;
import org.roof.commons.PropertiesUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.support.LdapContextSource;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.zjhcsoft.uac.util.EncrypDES;

public abstract class AbstractPersonService implements PersonServiceI {
	
	private static final Logger _logger = Logger.getLogger(AbstractPersonService.class);
	private Key key;
	
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
	protected DistinguishedName getDn(Person person) {
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
	 * 将摘要信息转换为相应的编码
	 * 
	 * @param code
	 *            编码类型(MD5,SHA,SHA-256,SHA-512)
	 * @param pwd
	 *            摘要信息
	 * @return 相应的编码字符串
	 */
	protected String encodePwd(String code, String pwd) {
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

	protected Key readKey() throws IOException {
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

}
