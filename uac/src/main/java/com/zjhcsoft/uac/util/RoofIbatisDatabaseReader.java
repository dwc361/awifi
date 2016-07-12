package com.zjhcsoft.uac.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.roof.dataaccess.RoofDaoSupport;

import com.zjhcsoft.exceldb.entity.Column;
import com.zjhcsoft.exceldb.entity.XslDb;
import com.zjhcsoft.exceldb.support.IDatabaseReader;

public class RoofIbatisDatabaseReader implements IDatabaseReader {
	private RoofDaoSupport roofDaoSupport;

	private Long currentRowNum = 0L;
	private Long rowCount;
	private Long fetchSize = 2000L;

	private String querySql;
	private String countSql;
	private Object params;
	private XslDb xslDb;

	private void initRowCount() {
		if (rowCount == null) {
			rowCount = (Long) roofDaoSupport.selectForObject(countSql, params);
		}
	}

	@Override
	public boolean hasNext() {
		if (rowCount == null) {
			initRowCount();
		}
		if (rowCount > currentRowNum) {
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> next() {
		PageQuery pageQuery = new PageQuery(currentRowNum, fetchSize, querySql,
				countSql);
		Page page = pageQuery.select(params);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> resVal = (List<Map<String, Object>>) page
				.getDataList();
		if (xslDb != null) {
			resVal = mapResult(xslDb, resVal);
		}
		currentRowNum += fetchSize;
		return resVal;
	}

	protected List<Map<String, Object>> mapResult(XslDb xslDb,
			List<Map<String, Object>> queryList) {
		List<Map<String, Object>> resList;
		resList = new ArrayList<Map<String, Object>>();
		List<Column> columns = createSortColumn(xslDb.getColumns(),
				queryList.get(0));
		for (Map<String, Object> map : queryList) {
			Map<String, Object> resMap = new HashMap<String, Object>();
			Set<Entry<String, Object>> entries = map.entrySet();
			for (Entry<String, Object> entry : entries) {
				for (Column c : columns) {
					if (StringUtils.equalsIgnoreCase(c.getDbcol(),
							entry.getKey())) {
						resMap.put(c.getXslcol(), entry.getValue());
					}
				}
			}
			resList.add(resMap);
		}
		return resList;
	}

	protected List<Column> createSortColumn(List<Column> columns,
			Map<String, Object> map) {
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

	@Override
	public void remove() {
		throw new UnsupportedOperationException("不支持 remove方法");
	}

	public RoofDaoSupport getRoofDaoSupport() {
		return roofDaoSupport;
	}

	public Long getCurrentRowNum() {
		return currentRowNum;
	}

	public Long getRowCount() {
		return rowCount;
	}

	public Long getFetchSize() {
		return fetchSize;
	}

	public String getQuerySql() {
		return querySql;
	}

	public String getCountSql() {
		return countSql;
	}

	public Object getParams() {
		return params;
	}

	public void setRoofDaoSupport(RoofDaoSupport roofDaoSupport) {
		this.roofDaoSupport = roofDaoSupport;
	}

	public void setCurrentRowNum(Long currentRowNum) {
		this.currentRowNum = currentRowNum;
	}

	public void setRowCount(Long rowCount) {
		this.rowCount = rowCount;
	}

	public void setFetchSize(Long fetchSize) {
		this.fetchSize = fetchSize;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	public XslDb getXslDb() {
		return xslDb;
	}

	public void setXslDb(XslDb xslDb) {
		this.xslDb = xslDb;
	}

}
