package org.roof.web.resources;

import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.result.ResultMap;

/**
 * 
 * @author liuxin
 * 
 */
public interface StatementReplacement {
	String replaceSql();

	ParameterMap replaceParameterMap();

	ResultMap replaceResultMap();

	String getSql();

	ParameterMap getParameterMap();

	ResultMap getResultMap();

	void setSql(String sql);

	void setParameterMap(ParameterMap parameterMap);

	void setResultMap(ResultMap resultMap);

	String getStatementName();

	void setStatementName(String statementName);

}
