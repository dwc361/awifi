package org.roof.ckedit;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ckfinder.connector.configuration.ConfigurationFactory;
import com.ckfinder.connector.errors.ConnectorException;
import com.ckfinder.connector.handlers.command.FileUploadCommand;

public class FileUploadFilter implements Filter {
	private static final String CONTENT_LENGTH = "content-length";
	private static final String JSESSIONID = "jsessionid";
	private static final String JSID_PARAM_NAME = "JSESSIONID";
	private static final String USER_SESSION_COOKIE_NAME = "sessionCookieName";
	private static final String USER_SESSION_PATH_PARAMETER_NAME = "sessionParameterName";
	private FilterConfig config = null;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if ("LoadCookies".equalsIgnoreCase(request.getParameter("command"))) {
			request.setAttribute("session.cookie.name", getSessionCookieName());
			request.setAttribute("session.parameter.name",
					getSessionParameterName());
		}

		if ("FILEUPLOAD".equalsIgnoreCase(request.getParameter("command"))) {
			if (((request instanceof HttpServletRequest))
					&& ((response instanceof HttpServletResponse))) {
				HttpServletRequest httpRequest = (HttpServletRequest) request;
				String contentLength = httpRequest.getHeader(CONTENT_LENGTH);
				if ((contentLength != null)
						&& (Integer.parseInt(contentLength) != 0)) {
					HttpServletResponse httpResponse = (HttpServletResponse) response;
					setSessionCookie((HttpServletResponse) response,
							httpRequest);
					FileUploadCommand fileUploadCommand = new FileUploadCommand();
					try {
						fileUploadCommand.runCommand(httpRequest, httpResponse,
								ConfigurationFactory.getInstace()
										.getConfiguration(httpRequest),
								new Object[] {});
					} catch (ConnectorException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	private void setSessionCookie(HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) {
		if (httpRequest.getParameter(getSessionParameterName()) != null) {
			Cookie userCookie = new Cookie(getSessionCookieName(),
					httpRequest.getParameter(getSessionParameterName()));
			httpResponse.addCookie(userCookie);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}

	@Override
	public void destroy() {
	}

	private String getSessionCookieName() {
		return this.config.getInitParameter(USER_SESSION_COOKIE_NAME) == null ? JSID_PARAM_NAME
				: this.config.getInitParameter(USER_SESSION_COOKIE_NAME);
	}

	private String getSessionParameterName() {
		return this.config.getInitParameter(USER_SESSION_PATH_PARAMETER_NAME) == null ? JSESSIONID
				: this.config
						.getInitParameter(USER_SESSION_PATH_PARAMETER_NAME);
	}

}
