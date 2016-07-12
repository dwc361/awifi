package com.zjhcsoft.uac.account.password.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class SpecharsMinlengthRegularPasswordValidate extends
		AbstractJUnit4SpringContextTests {
	private RegularPasswordValidate regularPasswordValidate;

	@Test
	public void testValidate() {
		String s = regularPasswordValidate.validate("23s+++ad##&&2232");
		System.out.println(s);
		s = regularPasswordValidate.validate("a1c1b34");
		System.out.println(s);

	}

	@Resource(name = "specharsMinlengthRegularPasswordValidate")
	public void setRegularPasswordValidate(
			RegularPasswordValidate regularPasswordValidate) {
		this.regularPasswordValidate = regularPasswordValidate;
	}

}
