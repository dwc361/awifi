package org.roof.excel.support;

/**
 * 
 * @author liuxin 2011-4-14
 * 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.roof.excel.vo.Column;
import org.roof.excel.vo.XslDb;
import org.roof.spring.CurrentSpringContext;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

public abstract class AbstractIbatisDao implements IExcelDao {

	protected SqlMapClientTemplate sqlMapper;
	protected Logger logger = Logger.getLogger(this.getClass());

	public AbstractIbatisDao() {
		super();
		sqlMapper = (SqlMapClientTemplate) CurrentSpringContext.getCurrentContext().getBean("sqlMapClientTemplate");
	}

	public void create(String statementName, Map<String, Object> parameterMap) {
		sqlMapper.insert(statementName, parameterMap);
	}

	public List<Map<String, Object>> read(String statementName, XslDb xslDb) {
		List<Map<String, Object>> queryList = null;
		List<Map<String, Object>> resList = null;
		queryList = sqlMapper.queryForList(statementName);
		if (queryList.size() == 0) {
			return queryList;
		}
		resList = mapResult(xslDb, queryList);
		return resList;
	}

	protected List<Map<String, Object>> mapResult(XslDb xslDb, List<Map<String, Object>> queryList) {
		List<Map<String, Object>> resList;
		resList = new ArrayList<Map<String, Object>>();
		List<Column> columns = createSortColumn(xslDb.getColumns(), queryList.get(0));
		for (Map<String, Object> map : queryList) {
			Map<String, Object> resMap = new HashMap<String, Object>();
			Set<Entry<String, Object>> entries = map.entrySet();
			for (Entry<String, Object> entry : entries) {
				for (Column c : columns) {
					if (StringUtils.equalsIgnoreCase(c.getDbcol(), entry.getKey())) {
						resMap.put(c.getXslcol(), entry.getValue());
					}
				}
			}
			resList.add(resMap);
		}
		return resList;
	}

	protected List<Column> createSortColumn(List<Column> columns, Map<String, Object> map) {
		List<Column> result = new ArrayList<Column>();
		Set<Entry<String, Object>> entries = map.entrySet();
		for (Entry<String, Object> entry : entries) {
			Column column = findColumnByDbcol(columns, entry.getKey());
			if (column != null) {
				result.add(column);
			}
		}
		return result;
	}

	protected Column findColumnByDbcol(List<Column> columns, String dbCol) {
		for (Column column : columns) {
			if (dbCol.equalsIgnoreCase(column.getDbcol())) {
				return column;
			}
		}
		return null;
		// throw new RuntimeException(dbCol + "在配置文件中没有找到映射");
	}

	public List<Map<String, Object>> read(String statementName, Object... args) {
		List<Map<String, Object>> resList = null;
		if (args == null || args.length < 1) {
			resList = sqlMapper.queryForList(statementName);
		} else {
			resList = sqlMapper.queryForList(statementName, args[0]);
		}
		return resList;
	}

	public int readColumn(String statementName, Object... args) {
		Object o = null;
		if (args == null || args.length < 1) {
			o = sqlMapper.queryForObject(statementName);
		} else {
			o = sqlMapper.queryForObject(statementName, args[0]);
		}
		if (o instanceof Long) {
			long l = (Long) o;
			return (int) l;
		}
		if (o instanceof Integer) {
			return (Integer) o;
		}
		if (o instanceof String) {
			return Integer.parseInt((String) o);
		}
		return 0;
	}

	public SqlMapClientTemplate getSqlMapper() {
		return sqlMapper;
	}

	public void setSqlMapper(SqlMapClientTemplate sqlMapper) {
		this.sqlMapper = sqlMapper;
	}

}