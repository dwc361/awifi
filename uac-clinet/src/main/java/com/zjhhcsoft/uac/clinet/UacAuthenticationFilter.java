package com.zjhhcsoft.uac.clinet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.authentication.DefaultGatewayResolverImpl;
import org.jasig.cas.client.authentication.GatewayResolver;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.AssertionImpl;

/**
 * 由于 AuthenticationFilter doFilter方法为final 无法重写<br />
 * 重写了CAS登陆过滤器 增加忽略登陆地址
 * 
 * @see AuthenticationFilter
 * @author liuxin
 *
 */
public class UacAuthenticationFilter extends AbstractCasFilter {

	/**
	 * The URL to the CAS Server login.
	 */
	private String casServerLoginUrl;

	/**
	 * Whether to send the renew request or not.
	 */
	private boolean renew = false;

	/**
	 * Whether to send the gateway request or not.
	 */
	private boolean gateway = false;

	private GatewayResolver gatewayStorage = new DefaultGatewayResolverImpl();

	private final List<Pattern> patterns = new ArrayList<Pattern>();

	private ServerHealth serverHealth;
	private String serverHealthUrl;
	private UserHolder userHolder;

	private static final Logger LOGGER = Logger
			.getLogger(UacAuthenticationFilter.class);

	protected void initInternal(final FilterConfig filterConfig)
			throws ServletException {

		if (serverHealthUrl == null) {
			serverHealthUrl = filterConfig.getInitParameter("serverHealthUrl");
		}
		serverHealth = ServerHealth.newInstance(serverHealthUrl);
		try {
			if (userHolder == null) {
				String userHolderTypeName = filterConfig
						.getInitParameter("userHolder");
				Class<?> userHolderType = null;
				userHolderType = Class.forName(userHolderTypeName);
				userHolder = (UserHolder) userHolderType.newInstance();
			}
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (InstantiationException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			LOGGER.error(e.getMessage(), e);
		}

		String patternsStr = filterConfig.getInitParameter("ignorePatterns");
		initPatterns(patternsStr);

		if (!isIgnoreInitConfiguration()) {
			super.initInternal(filterConfig);
			setCasServerLoginUrl(getPropertyFromInitParams(filterConfig,
					"casServerLoginUrl", null));
			log.trace("Loaded CasServerLoginUrl parameter: "
					+ this.casServerLoginUrl);
			setRenew(parseBoolean(getPropertyFromInitParams(filterConfig,
					"renew", "false")));
			log.trace("Loaded renew parameter: " + this.renew);
			setGateway(parseBoolean(getPropertyFromInitParams(filterConfig,
					"gateway", "false")));
			log.trace("Loaded gateway parameter: " + this.gateway);

			final String gatewayStorageClass = getPropertyFromInitParams(
					filterConfig, "gatewayStorageClass", null);

			if (gatewayStorageClass != null) {
				try {
					this.gatewayStorage = (GatewayResolver) Class.forName(
							gatewayStorageClass).newInstance();
				} catch (final Exception e) {
					log.error(e, e);
					throw new ServletException(e);
				}
			}
		}
	}

	private void initPatterns(String patternsStr) {
		if (patternsStr != null && !patternsStr.trim().equals("")) {
			String[] ps = patternsStr.split(",");
			for (String p : ps) {
				Pattern pat = Pattern.compile(p);
				patterns.add(pat);
			}
		}
	}

	public void init() {
		super.init();
		CommonUtils.assertNotNull(this.casServerLoginUrl,
				"casServerLoginUrl cannot be null.");
	}

	public void doFilter(final ServletRequest servletRequest,
			final ServletResponse servletResponse, final FilterChain filterChain)
			throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		final HttpSession session = request.getSession(false);

		// 绿色通道
		/*if (!serverHealth.running()) {
			filterChain.doFilter(request, response);
			return;
		}*/
		// 忽略登陆
		String url = request.getRequestURL().toString().replaceAll(" ", "")
				.replaceAll("\t", "").replaceAll("\n", "");// 去除所有空格
		if (ignoreUrl(url)) {
			filterChain.doFilter(request, response);
			return;
		}
		// 业务系统是否登陆userHolder.getUsername(request, response)
		if (userHolder.getUser(request, response) != null) {
			filterChain.doFilter(request, response);
			return;
			// session.setAttribute(
			// AbstractCasFilter.CONST_CAS_ASSERTION,
			// new AssertionImpl(userHolder.getUsername(request, response)));
		}
		final Assertion assertion = session != null ? (Assertion) session
				.getAttribute(CONST_CAS_ASSERTION) : null;

		if (assertion != null) {
			filterChain.doFilter(request, response);
			return;
		}

		final String serviceUrl = constructServiceUrl(request, response);
		final String ticket = CommonUtils.safeGetParameter(request,
				getArtifactParameterName());
		final boolean wasGatewayed = this.gatewayStorage.hasGatewayedAlready(
				request, serviceUrl);

		if (CommonUtils.isNotBlank(ticket) || wasGatewayed) {
			filterChain.doFilter(request, response);
			return;
		}

		final String modifiedServiceUrl;

		log.debug("no ticket and no assertion found");
		if (this.gateway) {
			log.debug("setting gateway attribute in session");
			modifiedServiceUrl = this.gatewayStorage.storeGatewayInformation(
					request, serviceUrl);
		} else {
			modifiedServiceUrl = serviceUrl;
		}

		if (log.isDebugEnabled()) {
			log.debug("Constructed service url: " + modifiedServiceUrl);
		}

		final String urlToRedirectTo = CommonUtils.constructRedirectUrl(
				this.casServerLoginUrl, getServiceParameterName(),
				modifiedServiceUrl, this.renew, this.gateway);

		if (log.isDebugEnabled()) {
			log.debug("redirecting to \"" + urlToRedirectTo + "\"");
		}

		response.sendRedirect(urlToRedirectTo);
	}

	/**
	 * https忽略的文件类型
	 * 
	 * @param url
	 * @return
	 */
	private boolean ignoreUrl(String url) {
		url = url.toLowerCase();
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(url);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

	public final void setRenew(final boolean renew) {
		this.renew = renew;
	}

	public final void setGateway(final boolean gateway) {
		this.gateway = gateway;
	}

	public final void setCasServerLoginUrl(final String casServerLoginUrl) {
		this.casServerLoginUrl = casServerLoginUrl;
	}

	public final void setGatewayStorage(final GatewayResolver gatewayStorage) {
		this.gatewayStorage = gatewayStorage;
	}
}
