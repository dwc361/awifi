package com.zjhcsoft.uac.account.password.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class CharMinlengthPasswordValidateTest extends
		AbstractJUnit4SpringContextTests {
	private RegularPasswordValidate regularPasswordValidate;

	@Test
	public void testValidate() {
		String s = regularPasswordValidate.validate("a1a1a34aas");
		System.out.println(s);
		s = regularPasswordValidate.validate("a1c1b34");
		System.out.println(s);

	}

	@Resource(name = "charMinlengthRegularPasswordValidate")
	public void setRegularPasswordValidate(
			RegularPasswordValidate regularPasswordValidate) {
		this.regularPasswordValidate = regularPasswordValidate;
	}

}
