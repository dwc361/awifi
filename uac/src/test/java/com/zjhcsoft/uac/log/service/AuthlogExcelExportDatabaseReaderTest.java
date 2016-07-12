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
import com.zjhcsoft.uac.log.entity.AuthLog;
import com.zjhcsoft.uac.log.entity.LoginLog;
import com.zjhcsoft.uac.util.RoofIbatisDatabaseReader;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class AuthlogExcelExportDatabaseReaderTest extends
		AbstractJUnit4SpringContextTests {
	private RoofIbatisDatabaseReader authlogExcelExportDatabaseReader;
	private XslDb authlogXslDB;

	@Test
	public void test() {
		CurrentSpringContext.setCurrentContext(this.applicationContext);
		authlogExcelExportDatabaseReader.setParams(new AuthLog());
		while (authlogExcelExportDatabaseReader.hasNext()) {
			List<Map<String, Object>> list = authlogExcelExportDatabaseReader
					.next();
			for (Map<String, Object> map : list) {
				System.out.println(map);
			}
		}
	}

	@Test
	public void testExport() {
		File file = new File("E:/excel/authlog.xls");
		FileOutputStream os = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			PoiExcelWriter poiExcelWriter = new PoiExcelWriter(authlogXslDB, os);
			CurrentSpringContext.setCurrentContext(this.applicationContext);
			authlogExcelExportDatabaseReader.setParams(new AuthLog());
			while (authlogExcelExportDatabaseReader.hasNext()) {
				List<Map<String, Object>> list = authlogExcelExportDatabaseReader
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
	public void setAuthlogExcelExportDatabaseReader(
			RoofIbatisDatabaseReader authlogExcelExportDatabaseReader) {
		this.authlogExcelExportDatabaseReader = authlogExcelExportDatabaseReader;
	}

	@Resource
	public void setAuthlogXslDB(XslDb authlogXslDB) {
		this.authlogXslDB = authlogXslDB;
	}

}
