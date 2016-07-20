package org.roof.excel.db;

/**
 * Title: exceldb2<br>
 * Description: <br>
 * Create DateTime: 2010-10-26 下午02:20:14 <br>
 * @author liuxin
 */
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.roof.spring.CurrentSpringContext;

public class ConnectionFactory {

	private static DataSource dataSource;

	private static Properties prop = new Properties();

	private static Logger logger = Logger.getLogger(ConnectionFactory.class);
	static {
		InputStream in = null;
		try {
			in = ConnectionFactory.class.getClassLoader().getResourceAsStream(
					"app-ds.properties");
			prop.load(in);
			Class.forName(prop.getProperty("jdbcDriver"));
		} catch (IOException e) {
			logger.error("app-ds.properties 文件读取失败!", e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				logger.error("InputStream close exception", e);
			}
		}
	}

	public static Connection getConnection() {
		if (dataSource == null) {
//			dataSource = setupDataSource();
			 dataSource = (DataSource)
			 CurrentSpringContext.getBean("dataSource");
		}
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			logger.error("获取连接失败!", e);
		}
		return connection;
	}

	public static DataSource setupDataSource() {
		logger.debug("正在创建数据源....");
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(prop.getProperty("jdbcDriver"));
		ds.setUsername(prop.getProperty("jdbcUser"));
		ds.setPassword(prop.getProperty("jdbcPassword"));
		ds.setUrl(prop.getProperty("jdbcUrl"));
		logger.debug("数据源创建成功!");
		printDataSourceStats(ds);
		return ds;
	}

	public static void printDataSourceStats(DataSource ds) {
		BasicDataSource bds = (BasicDataSource) ds;
		logger.debug("NumActive: " + bds.getNumActive());
		logger.debug("NumIdle: " + bds.getNumIdle());
	}

	public static void shutdownDataSource(DataSource ds) throws SQLException {
		BasicDataSource bds = (BasicDataSource) ds;
		bds.close();
	}

}
