package org.roof.excel.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.roof.excel.db.DaoTemplate;
import org.roof.excel.db.RowMapper;
import org.roof.excel.vo.Column;
import org.roof.excel.vo.XslDb;

/**
 * Title: exceldb2<br>
 * Description: <br>
 * Create DateTime: 2010-10-26 下午02:42:24 <br>
 * 
 * @author liuxin
 */
public abstract class AbstractJdbcDao implements IExcelDao {

	private DaoTemplate<List<Map<String, Object>>> daoTemplate;

	public static final String ROWNUM = "ROWNUM_";

	public AbstractJdbcDao() {
		daoTemplate = new DaoTemplate<List<Map<String, Object>>>();
	}

	public void create(String sql, Map<String, Object> parameterMap) throws Exception {
		daoTemplate.update(sql, new Object[] {});
	}

	public List<Map<String, Object>> read(String sql, int firstResult,
			int rowCount, final XslDb xslDb, Object... args) {
		sql = createPageSql(sql, firstResult, rowCount);
		return read(sql, xslDb);
	}

	protected abstract String createPageSql(String sql, int firstResult,
			int rowCount);

	public List<Map<String, Object>> read(String sql, final XslDb xslDb) {
		final List<Map<String, Object>> resVal = new ArrayList<Map<String, Object>>();

		daoTemplate.readForList(sql,
				new RowMapper<List<Map<String, Object>>>() {

					public List<Map<String, Object>> mapRow(ResultSet rs)
							throws SQLException {
						do {
							Map<String, Object> map = new HashMap<String, Object>();
							Iterator<Column> iterator = xslDb.getColumns()
									.iterator();
							while (iterator.hasNext()) {
								Column column = iterator.next();
								Object o = rs.getObject(column.getDbcol());
								map.put(column.getXslcol(), o);
							}
							resVal.add(map);
						} while (rs.next());
						return resVal;
					}
				}, new Object[] {});
		return resVal;
	}

	public int readColumn(String sql, Object... args) {
		return daoTemplate.readForInt(sql, args);
	}

	public List<Map<String, Object>> read(String sql, int firstResult,
			int rowCount, Object... args) {
		sql = createPageSql(sql, firstResult, rowCount);
		return read(sql, args);
	}

	public List<Map<String, Object>> read(String sql, Object... args) {
		return daoTemplate.readForMap(sql, args);
	}

}
