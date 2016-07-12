package com.zjhcsoft.uac.log.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.roof.spring.CurrentSpringContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.zjhcsoft.exceldb.entity.XslDb;
import com.zjhcsoft.exceldb.support.impl.PoiExcelWriter;
import com.zjhcsoft.uac.log.entity.AccountLog;
import com.zjhcsoft.uac.util.RoofIbatisDatabaseReader;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class AccountLogExcelExportDatabaseReaderTest extends
		AbstractJUnit4SpringContextTests {
	private RoofIbatisDatabaseReader accountLogExcelExportDatabaseReader;
	private XslDb accountLogXslDB;

	@Test
	public void test() {
		CurrentSpringContext.setCurrentContext(this.applicationContext);
		accountLogExcelExportDatabaseReader.setParams(new AccountLog());
		while (accountLogExcelExportDatabaseReader.hasNext()) {
			List<Map<String, Object>> list = accountLogExcelExportDatabaseReader
					.next();
			for (Map<String, Object> map : list) {
				System.out.println(map);
			}
		}
	}

	@Test
	public void testExport() {
		File file = new File("E:/excel/accountLog.xls");
		FileOutputStream os = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			PoiExcelWriter poiExcelWriter = new PoiExcelWriter(accountLogXslDB,
					os);
			CurrentSpringContext.setCurrentContext(this.applicationContext);
			accountLogExcelExportDatabaseReader.setParams(new AccountLog());
			while (accountLogExcelExportDatabaseReader.hasNext()) {
				List<Map<String, Object>> list = accountLogExcelExportDatabaseReader
						.next();
				poiExcelWriter.write(list);
			}
			poiExcelWriter.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Resource
	public void setAccountLogExcelExportDatabaseReader(
			RoofIbatisDatabaseReader accountLogExcelExportDatabaseReader) {
		this.accountLogExcelExportDatabaseReader = accountLogExcelExportDatabaseReader;
	}

	@Resource
	public void setAccountLogXslDB(XslDb accountLogXslDB) {
		this.accountLogXslDB = accountLogXslDB;
	}

}
