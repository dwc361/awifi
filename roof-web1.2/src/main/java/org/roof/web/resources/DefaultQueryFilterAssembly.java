package org.roof.web.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.roof.web.resources.entity.QueryFilterResource;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

/**
 * 
 * @author liuxin
 * 
 */
@Component
public class DefaultQueryFilterAssembly implements QueryFilterAssembly,
		QueryFilterAssemblyStrategy {
	private static String SQL_OR = "or";
	private static String SQL_AND = "and";
	private static String SQL_LEFT_BRACKET = "(";
	private static String SQL_RIGHT_BRACKET = ")";
	private static String SQL_WHITE_SPACE = " ";

	@Override
	public String assemble(Collection<QueryFilterResource> queryFilterResources) {
		if (CollectionUtils.isEmpty(queryFilterResources)) {
			return null;
		}
		List<String> criteria = new ArrayList<String>();
		Iterator<QueryFilterResource> iterator = queryFilterResources
				.iterator();
		while (iterator.hasNext()) {
			QueryFilterResource queryFilterResource = (QueryFilterResource) iterator
					.next();
			if (!hasParent(queryFilterResource, queryFilterResources)) {
				criteria.add(assemble(queryFilterResource, queryFilterResources));
			}
		}
		return assemblePeer(criteria.toArray(new String[] {}));
	}

	private boolean hasParent(QueryFilterResource queryFilterResource,
			Collection<QueryFilterResource> queryFilterResources) {
		for (QueryFilterResource queryFilterResource2 : queryFilterResources) {
			if (queryFilterResource.getParent().getId()
					.equals(queryFilterResource2.getId())) {
				return true;
			}
		}
		return false;
	}

	private String assemble(QueryFilterResource parent,
			Collection<QueryFilterResource> queryFilterResources) {
		List<QueryFilterResource> children = findChildren(parent,
				queryFilterResources);
		String result = null;
		if (CollectionUtils.isEmpty(children)) {
			result = parseExpression(parent);
		} else {
			String[] criteria = new String[children.size()];
			int i = 0;
			for (QueryFilterResource queryFilterResource : children) {
				criteria[i] = assemble(queryFilterResource,
						queryFilterResources);
				i++;
			}
			result = assembleSub(parent.getCriteria(), assemblePeer(criteria));
		}
		return result;
	}

	private String parseExpression(QueryFilterResource queryFilterResource) {
		String result = queryFilterResource.getCriteria();
		if (queryFilterResource.getUseSpel() != null
				&& queryFilterResource.getUseSpel() == 1) {
			ExpressionParser parser = new SpelExpressionParser();
			result = parser
					.parseExpression(result, new TemplateParserContext())
					.getValue(String.class);
		}
		return result;

	}

	private List<QueryFilterResource> findChildren(QueryFilterResource parent,
			Collection<QueryFilterResource> queryFilterResources) {
		List<QueryFilterResource> result = new ArrayList<QueryFilterResource>();
		Iterator<QueryFilterResource> iterator = queryFilterResources
				.iterator();
		while (iterator.hasNext()) {
			QueryFilterResource queryFilterResource = (QueryFilterResource) iterator
					.next();
			if (parent.getId().equals(queryFilterResource.getParent().getId())) {
				result.add(queryFilterResource);
			}
		}
		return result;
	}

	@Override
	public String assemblePeer(String[] criteria) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(SQL_LEFT_BRACKET);
		buffer.append(StringUtils.join(criteria, SQL_WHITE_SPACE + SQL_OR
				+ SQL_WHITE_SPACE));
		buffer.append(SQL_RIGHT_BRACKET);
		return buffer.toString();
	}

	@Override
	public String assembleSub(String criteria, String subCriterias) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(SQL_LEFT_BRACKET);
		buffer.append(criteria + SQL_WHITE_SPACE + SQL_AND + SQL_WHITE_SPACE
				+ subCriterias);
		buffer.append(SQL_RIGHT_BRACKET);
		return buffer.toString();
	}

}
