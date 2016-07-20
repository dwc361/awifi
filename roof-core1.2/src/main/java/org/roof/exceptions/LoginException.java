package org.roof.exceptions;

/**
 * 登录异常
 * 
 * @author liuxin 2011-9-9
 * @version 1.0 DaoException.java liuxin 2011-9-9
 */
public class LoginException extends Exception {

	private static final long serialVersionUID = 4640181036015959754L;

	public LoginException() {
		super("BL00001");
	}

	public LoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginException(String message) {
		super(message);
	}

	public LoginException(Throwable cause) {
		super(cause);
	}

}
