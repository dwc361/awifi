package org.roof.exceptions;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 系统编码的异常
 * 
 * @author liuxin 2011-10-16
 * @version 1.0 EncodedException.java liuxin 2011-10-16
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 3062390883764657324L;

	private String exceptionCode;

	public static final String DEFAULT_EXCEPTION_CODE = "SYS00001";

	private static ResourceBundle messages = null;

	static {
		Locale locale = new Locale("zh", "CN");
		messages = ResourceBundle.getBundle("exceptions", locale);
	}

	/**
	 * 获得异常编码值 编码对应exceptions资源文件中的异常信息
	 * 
	 * @param exceptionCode
	 *            异常编码
	 * @return 异常实例
	 * @throws ApplicationException
	 */
	public static String getErrorMsg(String exceptionCode) {
		return messages.getString(exceptionCode);
	}

	/**
	 * 创建一个异常对象
	 * <p>
	 * 编码对应exceptions资源文件中的异常信息
	 * 
	 * @param exceptionCode
	 *            异常编码
	 * @return 异常实例
	 */
	public static ApplicationException newInstance(String exceptionCode) {
		return newInstance(exceptionCode, null, null);
	}

	/**
	 * 创建一个异常对象
	 * <p>
	 * 编码对应exceptions资源文件中的异常信息, arguments中的值会逐个替换异常文本中{0}的占位符
	 * 
	 * @param exceptionCode
	 *            异常编码
	 * @param arguments
	 *            占位符替换文本
	 * @return 异常实例
	 */
	public static ApplicationException newInstance(String exceptionCode,
			Object[] arguments) {
		return newInstance(exceptionCode, arguments, null);
	}

	/**
	 * 创建一个异常对象
	 * <p>
	 * 编码对应exceptions资源文件中的异常信息
	 * 
	 * @param exceptionCode
	 *            异常编码
	 * @param cause
	 *            导致的异常
	 * @return 异常实例
	 */
	public static ApplicationException newInstance(String exceptionCode,
			Throwable cause) {
		return newInstance(exceptionCode, null, cause);
	}

	/**
	 * 创建一个异常对象
	 * <p>
	 * 编码对应exceptions资源文件中的异常信息, arguments中的值会逐个替换异常文本中{0}的占位符
	 * 
	 * @param exceptionCode
	 *            异常编码
	 * @param arguments
	 *            占位符替换文本
	 * @param cause
	 *            导致的异常
	 * @return
	 */
	public static ApplicationException newInstance(String exceptionCode,
			Object[] arguments, Throwable cause) {
		String message = getErrorMsg(exceptionCode);
		if (message == null) {
			message = getErrorMsg(DEFAULT_EXCEPTION_CODE);
		}
		if (arguments != null && arguments.length != 0) {
			message = MessageFormat.format(message, arguments);
		}
		if (cause == null) {
			return new ApplicationException(exceptionCode, message);
		}
		return new ApplicationException(exceptionCode, message, cause);
	}

	private ApplicationException(String exceptionCode, String message,
			Throwable cause) {
		super("[" + exceptionCode + "]" + message, cause);
		this.exceptionCode = exceptionCode;
	}

	private ApplicationException(String exceptionCode, String message) {
		super("[" + exceptionCode + "]" + message);
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

}
