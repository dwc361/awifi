package org.roof.excel.support.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.roof.excel.support.IDatabaseReader;
import org.roof.excel.support.IExcelDao;
import org.roof.excel.vo.Column;
import org.roof.excel.vo.XslDb;

/**
 * 
 * @author liuxin 2011-4-12
 * 
 */
public class JdbcDatabaseReader implements IDatabaseReader {
	private int currentRowNum;
	private int rowCount;
	private IExcelDao excelDao;
	private int fetchSize;

	private XslDb xslDb;
	private String querySql;
	private String countSql;
	private String constraints;
	private Object[] args;

	public JdbcDatabaseReader() {
		super();
		excelDao = PropertiesLoader.getDao();
		fetchSize = Integer.parseInt(PropertiesLoader.getProperty("fetchSize"));
	}

	private void init() {
		if (StringUtils.isBlank(querySql)) {
			createQuerySql();
		}
		if (StringUtils.isBlank(countSql)) {
			createCountSql();
		}
		initRowCount();
	}

	private void createCountSql() {
		StringBuffer sql = new StringBuffer("select count(*) ");
		int index = StringUtils.indexOfIgnoreCase(querySql, "FROM");
		if (index >= 0) {
			sql.append(querySql.substring(index));
		} else {
			throw new RuntimeException("[" + querySql + "] 语法错误!");
		}
		// if (constraints != null) {
		// sql.append(" where " + constraints);
		// }
		countSql = sql.toString();
	}

	private void createQuerySql() {
		if (xslDb == null) {
			throw new IllegalArgumentException("没有指定查询sql的情况下xslDb必须设值!");
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		Iterator<Column> iterator = xslDb.getColumns().iterator();
		while (iterator.hasNext()) {
			Column column = iterator.next();
			sql.append(column.getDbcol());
			if (iterator.hasNext()) {
				sql.append(",");
			}
		}
		sql.append(" from " + xslDb.getTableName());
		if (constraints != null) {
			sql.append(" where " + constraints);
		}
		querySql = sql.toString();
	}

	private void initRowCount() {
		rowCount = excelDao.readColumn(countSql.toString(), args);
		// if (rowCount > 60000) {
		// rowCount = 60000;
		// }
	}

	public boolean hasNext() {
		if (currentRowNum == 0) {
			init();
		}
		if (rowCount > currentRowNum) {
			return true;
		}
		return false;
	}

	public List<Map<String, Object>> next() {
		List<Map<String, Object>> resVal = null;
		if (xslDb == null) {
			resVal = excelDao.read(querySql, currentRowNum, fetchSize, args);
		} else {
			resVal = excelDao.read(querySql, currentRowNum, fetchSize, xslDb,
					args);
		}
		currentRowNum += fetchSize;
		return resVal;
	}

	public void remove() {
		throw new UnsupportedOperationException("不支持 remove方法");
	}

	public XslDb getXslDb() {
		return xslDb;
	}

	public String getQuerySql() {
		return querySql;
	}

	public String getCountSql() {
		return countSql;
	}

	public String getConstraints() {
		return constraints;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setXslDb(XslDb xslDb) {
		this.xslDb = xslDb;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

}
