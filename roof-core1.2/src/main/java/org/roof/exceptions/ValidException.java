package org.roof.exceptions;

/**
 * 验证失败异常
 * 
 * @author liuxin
 *
 */
public class ValidException extends Exception {

	private static final long serialVersionUID = -4537285598352515458L;

	public ValidException() {
	}

	public ValidException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidException(String message) {
		super(message);
	}

	public ValidException(Throwable cause) {
		super(cause);
	}

}
