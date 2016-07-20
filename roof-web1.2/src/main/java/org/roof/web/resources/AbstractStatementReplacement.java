package org.roof.web.resources;

import javax.annotation.Resource;

import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.result.ResultMap;

/**
 * 
 * @author liuxin
 * 
 */
public abstract class AbstractStatementReplacement implements
		StatementReplacement {
	protected String statementName;
	protected String sql;
	protected ParameterMap parameterMap;
	protected ResultMap resultMap;

	protected QueryFilterInvocationMetadataSource queryFilterInvocationMetadataSource;

	public abstract String replaceSql();

	public abstract ParameterMap replaceParameterMap();

	public abstract ResultMap replaceResultMap();

	@Override
	public String getSql() {
		return sql;
	}

	@Override
	public ParameterMap getParameterMap() {
		return parameterMap;
	}

	@Override
	public ResultMap getResultMap() {
		return resultMap;
	}

	@Override
	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public void setParameterMap(ParameterMap parameterMap) {
		this.parameterMap = parameterMap;
	}

	@Override
	public void setResultMap(ResultMap resultMap) {
		this.resultMap = resultMap;
	}

	@Override
	public String getStatementName() {
		return statementName;
	}

	@Override
	public void setStatementName(String statementName) {
		this.statementName = statementName;
	}

	@Resource(name = "defaultQueryFilterInvocationMetadataSource")
	public void setQueryFilterInvocationMetadataSource(
			QueryFilterInvocationMetadataSource queryFilterInvocationMetadataSource) {
		this.queryFilterInvocationMetadataSource = queryFilterInvocationMetadataSource;
	}

}
