package org.roof.excel.support.impl;

import java.util.List;
import java.util.Map;

import org.roof.excel.support.AbstractIbatisDao;
import org.roof.excel.vo.XslDb;

public class IbatisDao extends AbstractIbatisDao {

	/**
	 * ibatis自带分页效率不高
	 */
	public List<Map<String, Object>> read(String statementName,
			int firstResult, int rowCount, XslDb xslDb, Object... args) {
		List<Map<String, Object>> queryList = null;
		List<Map<String, Object>> resList = null;
		queryList = sqlMapper.queryForList(statementName, args[0], firstResult,
				rowCount);
		resList = mapResult(xslDb, queryList);
		return resList;
	}

	/**
	 * ibatis自带分页效率不高
	 */
	public List<Map<String, Object>> read(String statementName,
			int firstResult, int rowCount, Object... args) {
		List<Map<String, Object>> resList = null;
		if (args == null || args.length < 1) {
			resList = sqlMapper.queryForList(statementName, firstResult,
					rowCount);
		} else {
			resList = sqlMapper.queryForList(statementName, args[0],
					firstResult, rowCount);
		}
		return resList;
	}
}
