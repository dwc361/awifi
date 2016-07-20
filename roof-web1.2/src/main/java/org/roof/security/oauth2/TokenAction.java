package org.roof.security.oauth2;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequestManager;
import org.springframework.security.oauth2.provider.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.DefaultAuthorizationRequest;
import org.springframework.security.oauth2.provider.DefaultAuthorizationRequestManager;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 
 * @author liuxin
 * @see TokenEndpoint
 */
public class TokenAction extends RoofActionSupport implements InitializingBean {

	private static final long serialVersionUID = 1L;
	private Map<String, TokenGranter> tokenGranters;

	private ClientDetailsService clientDetailsService;

	private AuthorizationRequestManager authorizationRequestManager;

	private AuthorizationRequestManager defaultAuthorizationRequestManager;
	private ClientRegistrationService clientRegistrationService;

	/**
	 * 获取token
	 * 
	 * @param grant_type
	 *            授权类型<br />
	 *            password : 用户名密码授权
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @see TokenEndpoint#getAccessToken(Principal, String, java.util.Map);
	 * @return
	 */
	public String accessToken() {
		String grantType = this.getParamByName("grant_type", String.class);
		String clientId = this.getParamByName("username", String.class);
		Map<String, String> parameters = getParameters();
		if (clientId != null) {
			parameters.put("client_id", clientId);
		}
		if (!StringUtils.hasText(grantType)) {
			throw new InvalidRequestException("Missing grant type");
		}

		try {
			getAuthorizationRequestManager().validateParameters(parameters,
					getClientDetailsService().loadClientByClientId(clientId));
		} catch (NoSuchClientException e) {// 提取单独注册
			ClientDetails clientDetails = new BaseClientDetails(clientId, null, null, grantType, null);
			clientRegistrationService.addClientDetails(clientDetails);
			getAuthorizationRequestManager().validateParameters(parameters,
					getClientDetailsService().loadClientByClientId(clientId));
		}

		DefaultAuthorizationRequest authorizationRequest = new DefaultAuthorizationRequest(
				getAuthorizationRequestManager().createAuthorizationRequest(parameters));
		if (isAuthCodeRequest(parameters) || isRefreshTokenRequest(parameters)) {
			// The scope was requested or determined during the authorization
			// step
			if (!authorizationRequest.getScope().isEmpty()) {
				logger.debug("Clearing scope of incoming auth code request");
				authorizationRequest.setScope(Collections.<String> emptySet());
			}
		}
		if (isRefreshTokenRequest(parameters)) {
			// A refresh token has its own default scopes, so we should ignore
			// any added by the factory here.
			authorizationRequest.setScope(OAuth2Utils.parseParameterList(parameters.get("scope")));
		}
		OAuth2AccessToken token = null;
		try {
			token = getTokenGranter(grantType).grant(grantType, authorizationRequest);
		} catch (Exception e) {
			Result r = new Result(Result.ERROR, "系统编码或者授权密码错误或者授权类型不合法", null);
			r.setExceptionCode("21008");
			result = r;
			return JSON;
		}
		result = token;
		return JSON;
	}

	private TokenGranter getTokenGranter(String grantType) {
		return tokenGranters.get(grantType);
	}

	private Map<String, String> getParameters() {
		Map<String, String> result = new HashMap<String, String>();
		for (Entry<String, Object> entry : getParams().entrySet()) {
			result.put(entry.getKey(), entry.getValue().toString());
		}
		return result;
	}

	/**
	 * @param principal
	 *            the currently authentication principal
	 * @return a client id if there is one in the principal
	 */
	protected String getClientId(Principal principal) {
		Authentication client = (Authentication) principal;
		if (!client.isAuthenticated()) {
			throw new InsufficientAuthenticationException("The client is not authenticated.");
		}
		String clientId = client.getName();
		if (client instanceof OAuth2Authentication) {
			// Might be a client and user combined authentication
			clientId = ((OAuth2Authentication) client).getAuthorizationRequest().getClientId();
		}
		return clientId;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.state(tokenGranters != null, "TokenGranter must be provided");
		Assert.state(clientDetailsService != null, "ClientDetailsService must be provided");
		defaultAuthorizationRequestManager = new DefaultAuthorizationRequestManager(getClientDetailsService());
		if (authorizationRequestManager == null) {
			authorizationRequestManager = defaultAuthorizationRequestManager;
		}
	}

	private boolean isRefreshTokenRequest(Map<String, String> parameters) {
		return "refresh_token".equals(parameters.get("grant_type")) && parameters.get("refresh_token") != null;
	}

	private boolean isAuthCodeRequest(Map<String, String> parameters) {
		return "authorization_code".equals(parameters.get("grant_type")) && parameters.get("code") != null;
	}

	public ClientDetailsService getClientDetailsService() {
		return clientDetailsService;
	}

	public AuthorizationRequestManager getDefaultAuthorizationRequestManager() {
		return defaultAuthorizationRequestManager;
	}

	public void setClientDetailsService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}

	public void setDefaultAuthorizationRequestManager(AuthorizationRequestManager defaultAuthorizationRequestManager) {
		this.defaultAuthorizationRequestManager = defaultAuthorizationRequestManager;
	}

	public AuthorizationRequestManager getAuthorizationRequestManager() {
		return authorizationRequestManager;
	}

	public void setAuthorizationRequestManager(AuthorizationRequestManager authorizationRequestManager) {
		this.authorizationRequestManager = authorizationRequestManager;
	}

	public Map<String, TokenGranter> getTokenGranters() {
		return tokenGranters;
	}

	public void setTokenGranters(Map<String, TokenGranter> tokenGranters) {
		this.tokenGranters = tokenGranters;
	}

	public ClientRegistrationService getClientRegistrationService() {
		return clientRegistrationService;
	}

	public void setClientRegistrationService(ClientRegistrationService clientRegistrationService) {
		this.clientRegistrationService = clientRegistrationService;
	}

}
