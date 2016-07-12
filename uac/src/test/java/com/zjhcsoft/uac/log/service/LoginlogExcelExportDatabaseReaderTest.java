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
import com.zjhcsoft.uac.log.entity.LoginLog;
import com.zjhcsoft.uac.util.RoofIbatisDatabaseReader;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class LoginlogExcelExportDatabaseReaderTest extends
		AbstractJUnit4SpringContextTests {
	private RoofIbatisDatabaseReader loginlogExcelExportDatabaseReader;
	private XslDb loginlogXslDB;

	@Test
	public void test() {
		CurrentSpringContext.setCurrentContext(this.applicationContext);
		loginlogExcelExportDatabaseReader.setParams(new LoginLog());
		while (loginlogExcelExportDatabaseReader.hasNext()) {
			List<Map<String, Object>> list = loginlogExcelExportDatabaseReader
					.next();
			for (Map<String, Object> map : list) {
				System.out.println(map);
			}
		}
	}

	@Test
	public void testExport() {
		File file = new File("E:/excel/loginlog.xls");
		FileOutputStream os = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			PoiExcelWriter poiExcelWriter = new PoiExcelWriter(loginlogXslDB,
					os);
			CurrentSpringContext.setCurrentContext(this.applicationContext);
			loginlogExcelExportDatabaseReader.setParams(new LoginLog());
			while (loginlogExcelExportDatabaseReader.hasNext()) {
				List<Map<String, Object>> list = loginlogExcelExportDatabaseReader
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
	public void setLoginlogExcelExportDatabaseReader(
			RoofIbatisDatabaseReader loginlogExcelExportDatabaseReader) {
		this.loginlogExcelExportDatabaseReader = loginlogExcelExportDatabaseReader;
	}

	@Resource
	public void setLoginlogXslDB(XslDb loginlogXslDB) {
		this.loginlogXslDB = loginlogXslDB;
	}

}
