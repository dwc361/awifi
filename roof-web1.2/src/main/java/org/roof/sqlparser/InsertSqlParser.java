package org.roof.sqlparser;

/**
 * 
 * @author liuxin
 * 
 */
public class InsertSqlParser extends BaseSingleSqlParser {

	public InsertSqlParser(String originalSql) {
		super(originalSql);
	}

	@Override
	protected void initializeSegments() {
		segments.add(new SqlSegment("(insert into)(.+)([(])", "[,]"));
		segments.add(new SqlSegment("([(])(.+)( [)] values )", "[,]"));
		segments.add(new SqlSegment("([)] values [(])(.+)( [)])", "[,]"));
	}

	@Override
	public String getParsedFormatSql() {
		String retval = super.getParsedFormatSql();
		retval = retval + ")";
		return retval;
	}
}
