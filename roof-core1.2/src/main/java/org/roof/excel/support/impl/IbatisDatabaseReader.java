package org.roof.excel.support.impl;

import java.util.List;
import java.util.Map;

import org.roof.excel.support.IDatabaseReader;
import org.roof.excel.support.IExcelDao;
import org.roof.excel.vo.XslDb;

/**
 * 
 * @author liuxin 2011-4-13
 * 
 */
public class IbatisDatabaseReader implements IDatabaseReader {
	private int currentRowNum;
	private int rowCount;
	private IExcelDao excelDao;
	private int fetchSize;

	private XslDb xslDb;
	private String querySql;
	private String countSql;
	private Object[] args;

	public IbatisDatabaseReader() {
		super();
		excelDao = PropertiesLoader.getDao();
		fetchSize = Integer.parseInt(PropertiesLoader.getProperty("fetchSize"));
	}

	public IbatisDatabaseReader(IExcelDao excelDao, XslDb xslDb, String querySql, String countSql, Object[] args) {
		this();
		this.excelDao = excelDao;
		this.xslDb = xslDb;
		this.querySql = querySql;
		this.countSql = countSql;
		this.args = args;
		init();
	}

	public IbatisDatabaseReader(XslDb xslDb, String querySql, String countSql, Object[] args) {
		this();
		this.xslDb = xslDb;
		this.querySql = querySql;
		this.countSql = countSql;
		this.args = args;
		init();
	}

	public IbatisDatabaseReader(String querySql, String countSql, Object[] args) {
		this();
		this.querySql = querySql;
		this.countSql = countSql;
		this.args = args;
		init();
	}

	private void init() {
		initRowCount();
	}

	private void initRowCount() {
		rowCount = excelDao.readColumn(countSql.toString(), args);
		// if (rowCount > 60000) {
		// rowCount = 60000;
		// }
	}

	public boolean hasNext() {
		if (rowCount > currentRowNum) {
			return true;
		}
		return false;
	}

	public List<Map<String, Object>> next() {
		List<Map<String, Object>> resVal = null;
		resVal = excelDao.read(querySql, currentRowNum, fetchSize, xslDb, args);
		if (xslDb == null) {
		} else {
		}
		currentRowNum += fetchSize;
		return resVal;
	}

	public void remove() {
		throw new UnsupportedOperationException("不支持 remove方法");
	}

}
