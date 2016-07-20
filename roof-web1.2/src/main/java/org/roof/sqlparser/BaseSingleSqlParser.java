package org.roof.sqlparser;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author liuxin
 * 
 */
public abstract class BaseSingleSqlParser {

	/** */
	/**
	 * * 原始Sql语句
	 */
	protected String originalSql;
	/** */
	/**
	 * * Sql语句片段
	 */
	protected List<SqlSegment> segments;

	/** */
	/**
	 * * 构造函数，传入原始Sql语句，进行劈分。 * @param originalSql
	 */
	public BaseSingleSqlParser(String originalSql) {
		this.originalSql = originalSql;
		segments = new ArrayList<SqlSegment>();
		initializeSegments();
		splitSql2Segment();
	}

	/** */
	/**
	 * * 初始化segments，强制子类实现 *
	 */
	protected abstract void initializeSegments();

	/** */
	/**
	 * * 将originalSql劈分成一个个片段 *
	 */
	protected void splitSql2Segment() {
		for (SqlSegment sqlSegment : segments) {
			sqlSegment.parse(originalSql);
		}
	}

	/**
	 * 得到解析完毕格式化后的Sql语句
	 * 
	 * @return
	 */
	public String getParsedFormatSql() {
		StringBuffer sb = new StringBuffer();
		for (SqlSegment sqlSegment : segments) {
			sb.append(sqlSegment.getParsedSqlSegment() + "\n");
		}
		String retval = sb.toString().replaceAll("\n+", "\n");
		return retval;
	}

	public String getParsedSql() {
		StringBuffer sb = new StringBuffer();
		for (SqlSegment sqlSegment : segments) {
			sb.append(sqlSegment.getParsedSqlSegment() + " ");
		}
		String retval = sb.toString().replaceAll("\n+", " ");
		return retval;
	}

	public String getOriginalSql() {
		return originalSql;
	}

	public List<SqlSegment> getSegments() {
		return segments;
	}

	public void setOriginalSql(String originalSql) {
		this.originalSql = originalSql;
	}

	public void setSegments(List<SqlSegment> segments) {
		this.segments = segments;
	}

}
