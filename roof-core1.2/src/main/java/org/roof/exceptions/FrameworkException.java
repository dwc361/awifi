package org.roof.exceptions;

/**
 * 框架异常
 * 
 * @author liuxin 2011-9-10
 * @version 1.0 FrameworkException.java liuxin 2011-9-10
 */
public class FrameworkException extends Exception {

	private static final long serialVersionUID = -8138598992362468705L;

	public FrameworkException() {
		super();
	}

	public FrameworkException(String message, Throwable cause) {
		super(message, cause);
	}

	public FrameworkException(String message) {
		super(message);
	}

	public FrameworkException(Throwable cause) {
		super(cause);
	}

}
