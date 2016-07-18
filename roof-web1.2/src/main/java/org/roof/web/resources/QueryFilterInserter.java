package org.roof.web.resources;

/**
 * 查询条件插入
 * 
 * @author liuxin
 * 
 */
public interface QueryFilterInserter {
	/**
	 * SQL语句中插入过滤条件
	 * 
	 * @param queryFilterStr
	 *            过滤条件语句
	 * @return 修改后的SQL
	 */
	String insert(String sql, String queryFilterStr);

}
