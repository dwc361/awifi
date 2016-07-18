package org.roof.web.resources;

import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;

/**
 * 
 * @author liuxin
 * 
 */
@Component
public class SqlReplacement implements InitializingBean {

	private static final Logger LOGGER = Logger.getLogger(SqlReplacement.class);

	private SqlMapClientTemplate sqlMapClientTemplate;
	private SqlMapClientImpl sqlmap;

	public void replaceAll() {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("正在加载查询权限....");
		}
		@SuppressWarnings("unchecked")
		Iterator<String> statementNames = sqlmap.delegate
				.getMappedStatementNames();
		while (statementNames.hasNext()) {
			String name = (String) statementNames.next();
			replace(name);
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("加载查询权限加载完成");
		}
	}

	public void replace(String name) {
		SqlMapClientImpl sqlmap = getMapClientImpl();
		MappedStatement mappedStatement = sqlmap.delegate
				.getMappedStatement(name);
		Sql sql = mappedStatement.getSql();
		SqlInvocationHandler h = new SqlInvocationHandler(name, sql);
		Sql sqlProxy = h.getProxy();
		mappedStatement.setSql(sqlProxy);
	}

	public SqlMapClientImpl getMapClientImpl() {
		return (SqlMapClientImpl) sqlMapClientTemplate.getSqlMapClient();
	}

	@Resource
	public void setSqlMapClientTemplate(
			SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		sqlmap = getMapClientImpl();
//		replaceAll();
	}

}
