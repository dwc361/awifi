package org.roof.excel.support.impl;

import org.roof.excel.support.AbstractJdbcDao;

/**
 * 
 * @author liuxin 2011-4-14
 * 
 */
public class MysqlJdbcDao extends AbstractJdbcDao {

	@Override
	protected String createPageSql(String sql, int firstResult, int rowCount) {
		sql = sql + "LIMIT " + firstResult + "," + rowCount + ";";
		return sql;
	}
}
