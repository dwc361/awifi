package org.roof.sqlparser;

/**
 * 
 * @author liuxin
 * 
 */
public class NoSqlParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSqlParserException(String sql) {
		super(sql);
	}

}
