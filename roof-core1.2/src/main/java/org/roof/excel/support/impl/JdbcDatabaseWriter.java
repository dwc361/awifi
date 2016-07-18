package org.roof.excel.support.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.roof.commons.RoofDateUtils;
import org.roof.excel.support.IDatabaseWriter;
import org.roof.excel.support.IExcelDao;

/**
 * 
 * @author liuxin 2011-4-12
 * 
 */
public class JdbcDatabaseWriter implements IDatabaseWriter {
	private IExcelDao excelDao;

	public void write(Map<String, Object> map, String tableName) throws Exception {
		if (excelDao == null) {
			excelDao = PropertiesLoader.getDao(null);
		}
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		sql.append("insert into " + tableName + "(");
		Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			sql.append(entry.getKey());
			if (entry.getValue() instanceof String) {
				sql2.append("'" + entry.getValue() + "'");
			} else if (entry.getValue() instanceof Date) {
				String str = RoofDateUtils.dateToString((Date) entry.getValue(), "yyyy-MM-dd");
				sql2.append("to_date('" + str + "','YYYY-MM-DD')");
			} else {
				sql2.append(entry.getValue());
			}
			if (iterator.hasNext()) {
				sql.append(",");
				sql2.append(",");
			}
		}
		sql.append(")values(");
		sql.append(sql2 + ")");
		excelDao.create(sql.toString(), null);
	}

	public void setExcelDao(IExcelDao excelDao) {
		this.excelDao = excelDao;
	}

}
