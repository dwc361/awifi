package org.roof.excel.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author liuxin
 * 
 */
public final class JdbcUtils {
	private static Logger logger = Logger.getLogger(JdbcUtils.class);

	private JdbcUtils() {
	}

	public static Connection getConnection() throws Exception {
		return ConnectionFactory.getConnection();
	}

	public static void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			logger.error("数据库连接关闭异常!", e);
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				logger.error("数据库连接关闭异常!", e);
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						logger.error("数据库连接关闭异常!", e);
					}
			}
		}
	}
}
