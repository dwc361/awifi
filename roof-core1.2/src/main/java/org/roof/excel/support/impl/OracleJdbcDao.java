package org.roof.excel.support.impl;

import org.roof.excel.support.AbstractJdbcDao;

/**
 * 
 * @author liuxin 2011-4-12
 * 
 */
public class OracleJdbcDao extends AbstractJdbcDao {

	@Override
	protected String createPageSql(String sql, int firstResult, int rowCount) {
		int lastResult = rowCount + firstResult;
		sql = "SELECT * FROM (SELECT A.*, ROWNUM " + ROWNUM + " FROM (" + sql + ") A WHERE ROWNUM <= " + lastResult
				+ ")WHERE " + ROWNUM + " > " + firstResult;
		return sql;
	}
}
