package org.roof.guardmonitor.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.roof.guardmonitor.AbstractMonitor;
import org.roof.guardmonitor.MonitorContext;
import org.roof.guardmonitor.Status;
import org.springframework.beans.factory.InitializingBean;

/**
 * 数据库监控
 * 
 * @author liuxin
 *
 */
public class JdbcMonitor extends AbstractMonitor implements InitializingBean {

	private String driverClass;
	private String jdbcUrl;
	private String user;
	private String password;
	private String sql;
	private int timeout = 5;
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	private static final Logger LOGGER = Logger.getLogger(JdbcMonitor.class);

	public void afterPropertiesSet() throws Exception {
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	@Override
	protected void doInTry(MonitorContext monitorContext) throws SQLException {
		DriverManager.setLoginTimeout(timeout);
		conn = DriverManager.getConnection(jdbcUrl, user, password);
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		monitorContext.setStatus(Status.RUNNING);
	}

	@Override
	protected void doFinally() {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	public String getDriverClass() {
		return driverClass;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getSql() {
		return sql;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}
