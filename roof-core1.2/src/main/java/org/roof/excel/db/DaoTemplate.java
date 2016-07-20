package org.roof.excel.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author liuxin
 * 
 */
public class DaoTemplate<T> {

	private Logger logger = Logger.getLogger(this.getClass());

	public int update(String sql, Object[] args) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		int resVal = 0;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					if (args[i] instanceof Date) {
						Date date = (Date) args[i];
						args[i] = new java.sql.Timestamp(date.getTime());
					}
					ps.setObject(i + 1, args[i]);

				}
			}
			resVal = ps.executeUpdate();
			logger.debug("执行sql:[" + sql + "]");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtils.free(null, ps, conn);
		}
		return resVal;
	}

	public T readForObject(String sql, RowMapper<T> rowMapper, Object[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		T resVal = null;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					if (args[i] instanceof Date) {
						Date date = (Date) args[i];
						args[i] = new java.sql.Timestamp(date.getTime());
					}
					ps.setObject(i + 1, args[i]);
				}
			}
			rs = ps.executeQuery();
			logger.debug("执行sql:[" + sql + "]");
			if (rs.next()) {
				resVal = rowMapper.mapRow(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
		return resVal;
	}

	public List<T> readForList(String sql, RowMapper<T> rowMapper, Object[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<T> resVal = new ArrayList<T>();
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					if (args[i] instanceof Date) {
						Date date = (Date) args[i];
						args[i] = new java.sql.Timestamp(date.getTime());
					}
					ps.setObject(i + 1, args[i]);
				}
			}
			rs = ps.executeQuery();
			logger.debug("执行sql:[" + sql + "]");
			while (rs.next()) {
				resVal.add(rowMapper.mapRow(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
		return resVal;
	}

	public int readForInt(String sql, Object[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resVal = 0;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					if (args[i] instanceof Date) {
						Date date = (Date) args[i];
						args[i] = new java.sql.Timestamp(date.getTime());
					}
					ps.setObject(i + 1, args[i]);
				}
			}
			rs = ps.executeQuery();
			logger.debug("执行sql:[" + sql + "]");
			if (rs.next()) {
				resVal = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
		return resVal;
	}

	public List<Map<String, Object>> readForMap(String sql, Object[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, Object>> resVal = new ArrayList<Map<String, Object>>();
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					if (args[i] instanceof Date) {
						Date date = (Date) args[i];
						args[i] = new java.sql.Timestamp(date.getTime());
					}
					ps.setObject(i + 1, args[i]);
				}
			}
			rs = ps.executeQuery();
			logger.debug("执行sql:[" + sql + "]");
			int n = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= n; i++) {
					map.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
				}
				resVal.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
		return resVal;
	}
}
