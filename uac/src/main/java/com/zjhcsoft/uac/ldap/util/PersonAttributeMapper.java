package com.zjhcsoft.uac.ldap.util;

import java.lang.reflect.Method;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.roof.commons.RoofStringUtils;
import org.springframework.ldap.core.AttributesMapper;

/**
 * 这个类的作用是将ldap中的属性转化为实体类的属性值， 在查询信息的时候会用到
 */
public class PersonAttributeMapper implements AttributesMapper {
	public static String[] fields = new String[] { "cn", "sn", "userPassword", "parNode", "description", "url",
			"pwdSetTime", "pwdDisableTime", "telephoneNumber" };

	@Override
	public Object mapFromAttributes(Attributes attr) throws NamingException {
		Person person = new Person();
		for (int i = 0; i < fields.length; i++) {
			try {
				if (attr.get(fields[i]) != null) {
					if ("userPassword".equals(fields[i])) {
						byte[] s = (byte[]) attr.get("userPassword").get();
						person.setUserPassword(new String(s));
						continue;
					}
					Class[] clazzArr = { java.lang.String.class };
					Method method = Person.class.getDeclaredMethod("set" + RoofStringUtils.firstUpperCase(fields[i]),
							clazzArr);
					Object[] argsArr = { (String) attr.get(fields[i]).get() };
					method.invoke(person, argsArr);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return person;
	}

}