package com.zjhcsoft.uac.account.user.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.roof.struts2.Result;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.zjhcsoft.uac.ldap.util.Person;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class PasswordTimelinessLoginCheckUnitTest extends
		AbstractJUnit4SpringContextTests {

	private PasswordTimelinessLoginCheckUnit passwordTimelinessLoginCheckUnit;

	@Test
	public void testCheck() {

	}

	@Test
	public void testCheck2() {
		Person person = new Person();
		person.setPwdSetTime("2014-5-1");
		Result result = passwordTimelinessLoginCheckUnit.check(person);
		System.out.println(result.getState() + " " + result.getMessage());
	}

	@Test
	public void testCheck3() {
		Person person = new Person();
		person.setPwdSetTime("2013-12-20");
		Result result = passwordTimelinessLoginCheckUnit.check(person);
		System.out.println(result.getState() + " " + result.getMessage());

		person = new Person();
		person.setPwdSetTime("2014-5-1");
		result = passwordTimelinessLoginCheckUnit.check(person);
		System.out.println(result.getState() + " " + result.getMessage());
	}

	@Test
	public void testCheck4() {
		Person person = new Person();
		person.setPwdSetTime("2014-5-26");
		Result result = passwordTimelinessLoginCheckUnit.check(person);
		System.out.println(result.getState() + " " + result.getMessage());
	}

	@Resource
	public void setPasswordTimelinessLoginCheckUnit(
			PasswordTimelinessLoginCheckUnit passwordTimelinessLoginCheckUnit) {
		this.passwordTimelinessLoginCheckUnit = passwordTimelinessLoginCheckUnit;
	}

}
