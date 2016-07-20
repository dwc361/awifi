package org.roof.web.resources;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.commons.lang3.StringUtils;
import org.roof.spring.CurrentSpringContext;

import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.result.ResultMap;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;

/**
 * 
 * @author liuxin
 * 
 */
public class SqlInvocationHandler implements InvocationHandler {

	private Sql sql;
	private String statementName;
	private StatementReplacement statementReplacement;

	public SqlInvocationHandler(String statementName, Sql sql) {
		this.statementName = statementName;
		this.setSql(sql);

	}

	private StatementReplacement getStatementReplacement() {
		return (StatementReplacement) CurrentSpringContext
				.getBean("defaultStatementReplacement");
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if (statementReplacement == null) {
			statementReplacement = getStatementReplacement();
			statementReplacement.setStatementName(this.statementName);
		}
		Object result = method.invoke(sql, args);
		if (StringUtils.equals("getSql", method.getName())) {
			statementReplacement.setSql((String) result);
			return statementReplacement.replaceSql();
		}
		if (StringUtils.equals("getParameterMap", method.getName())) {
			statementReplacement.setParameterMap((ParameterMap) result);
			return statementReplacement.replaceParameterMap();
		}
		if (StringUtils.equals("getResultMap", method.getName())) {
			statementReplacement.setResultMap((ResultMap) result);
			return statementReplacement.replaceResultMap();
		}
		return result;
	}

	public Sql getSql() {
		return sql;
	}

	public void setSql(Sql sql) {
		this.sql = sql;
	}

	public Sql getProxy() {
		return (Sql) Proxy.newProxyInstance(Thread.currentThread()
				.getContextClassLoader(), new Class[] { Sql.class }, this);
	}
}
