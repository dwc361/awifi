package com.zjhcsoft.uac.account.user.service;

import java.io.File;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.zjhcsoft.exceldb.entity.XslDb;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class UserImportorTest extends AbstractJUnit4SpringContextTests {
	private UserImportor userImportor;
	private AccountImportor accountImportor;
	private XslDb xslDb;

	//@Test
	public void testExc() {
		userImportor.exc(new File("E:\\excel\\user.xls"), xslDb);
	}
	
	@Test
	public void testimportExc() {
		accountImportor.exc(new File("E:\\excel\\edakf.xls"), xslDb,202L);
	}

	//@Test
	public void testimporttmpExc() {
		//accountImportor.tmpexc(new File("E:\\excel\\.xls"), xslDb,66150L);
	}

	@Resource
	public void setUserImportor(UserImportor userImportor) {
		this.userImportor = userImportor;
	}
	
	@Resource
	public void setAccountImportor(AccountImportor accountImportor) {
		this.accountImportor = accountImportor;
	}

	@Resource(name = "userXsDB")
	public void setXslDb(XslDb xslDb) {
		this.xslDb = xslDb;
	}

}
