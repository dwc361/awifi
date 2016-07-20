package org.roof.sqlparser;

/**
 * 
 * @author liuxin
 * 
 */
public class InsertSelectSqlParser extends BaseSingleSqlParser {
	public InsertSelectSqlParser(String originalSql) {
		super(originalSql);
	}

	@Override
	protected void initializeSegments() {
		segments.add(new SqlSegment("(insert into)(.+)( select )", "[,]"));
		segments.add(new SqlSegment("(select)(.+)(from)", "[,]"));
		segments.add(new SqlSegment(
				"(from)(.+)( where | on | having | group\\s+by | order\\s+by | ENDOFSQL)",
				"(,|\\s+left\\s+join\\s+|\\s+right\\s+join\\s+|\\s+inners+join\\s+)"));
		segments.add(new SqlSegment(
				"( where | on | having )(.+)( group\\s+by | order\\s+by | ENDOFSQL)",
				"(and |or )"));
		segments.add(new SqlSegment("( group\\s+by )(.+)( order\\s+by| ENDOFSQL)",
				"[,]"));
		segments.add(new SqlSegment("( order\\s+by )(.+)( ENDOFSQL)", "[,]"));
	}
}
