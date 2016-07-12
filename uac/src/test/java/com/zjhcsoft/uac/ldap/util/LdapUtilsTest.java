package com.zjhcsoft.uac.ldap.util;

import org.junit.Test;

public class LdapUtilsTest {
	private LdapUtils ldapUtils = new LdapUtils();

	@Test
	public void test() {
		String s = ldapUtils.encodePwdSHA("123456abc");
		System.out.println(s);
	}

}
