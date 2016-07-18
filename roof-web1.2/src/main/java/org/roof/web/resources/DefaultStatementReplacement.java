package org.roof.web.resources;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.roof.security.BaseUserContext;
import org.roof.web.resources.entity.QueryFilterResource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.result.ResultMap;

/**
 * 
 * @author liuxin
 * 
 */
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class DefaultStatementReplacement extends AbstractStatementReplacement
		implements InitializingBean {
	private QueryFilterAssembly queryFilterAssembly;
	private QueryFilterInserter queryFilterInserter;
	private QueryFilterAssembly defaultQueryFilterAssembly;
	private QueryFilterInserter defaultQueryFilterInserter;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (queryFilterAssembly == null) {
			queryFilterAssembly = defaultQueryFilterAssembly;
		}
		if (queryFilterInserter == null) {
			queryFilterInserter = defaultQueryFilterInserter;
		}
	}

	@Override
	public String replaceSql() {
		Collection<QueryFilterResource> queryFilterResources = queryFilterInvocationMetadataSource
				.getQueryFilterResources(this.statementName,
						BaseUserContext.getUserRoles());
		String result = null;
		if (CollectionUtils.isEmpty(queryFilterResources)) {
			result = sql;
		} else {

			String queryFilterStr = queryFilterAssembly
					.assemble(queryFilterResources);
			result = queryFilterInserter.insert(sql, queryFilterStr);
		}
		return result;
	}

	@Override
	public ParameterMap replaceParameterMap() {
		return this.parameterMap;
	}

	@Override
	public ResultMap replaceResultMap() {
		return this.resultMap;
	}

	public void setQueryFilterAssembly(QueryFilterAssembly queryFilterAssembly) {
		this.queryFilterAssembly = queryFilterAssembly;
	}

	public void setQueryFilterInserter(QueryFilterInserter queryFilterInserter) {
		this.queryFilterInserter = queryFilterInserter;
	}

	@Resource(name = "defaultQueryFilterAssembly")
	public void setDefaultQueryFilterAssembly(
			QueryFilterAssembly defaultQueryFilterAssembly) {
		this.defaultQueryFilterAssembly = defaultQueryFilterAssembly;
	}

	@Resource(name = "defaultQueryFilterInsert")
	public void setDefaultQueryFilterInserter(
			QueryFilterInserter defaultQueryFilterInserter) {
		this.defaultQueryFilterInserter = defaultQueryFilterInserter;
	}

}
