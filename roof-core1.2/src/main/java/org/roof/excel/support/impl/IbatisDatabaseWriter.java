package org.roof.excel.support.impl;

import java.util.Map;

import org.roof.excel.support.IDatabaseWriter;
import org.roof.excel.support.IExcelDao;

/**
 * 
 * @author liuxin 2011-4-19
 * 
 */
public class IbatisDatabaseWriter implements IDatabaseWriter {

	private IExcelDao excelDao;

	public void write(Map<String, Object> parameterMap, String statementName) throws Exception {
		excelDao = PropertiesLoader.getDao();
		excelDao.create(statementName, parameterMap);
	}

}
