package org.roof.web.resources;

import org.apache.commons.lang3.StringUtils;
import org.roof.sqlparser.BaseSingleSqlParser;
import org.roof.sqlparser.SingleSqlParserFactory;
import org.roof.sqlparser.SqlSegment;
import org.springframework.stereotype.Component;

/**
 * 默认查询过滤插入
 * 
 * @author liuxin
 * 
 */
@Component
public class DefaultQueryFilterInsert implements QueryFilterInserter {
	private static String SQL_WHERE = "( where | on | having )";

	@Override
	public String insert(String sql, String queryFilterStr) {
		sql = sql.trim();
		sql = sql + " ENDOFSQL";
		BaseSingleSqlParser baseSingleSqlParser = SingleSqlParserFactory
				.generateParser(sql);
		for (SqlSegment segment : baseSingleSqlParser.getSegments()) {
			if (StringUtils.contains(segment.getSegmentRegExp(), SQL_WHERE)) {
				segment.getBodyPieces().add("and " + queryFilterStr);
			}
		}
		return baseSingleSqlParser.getParsedSql();
	}

}
