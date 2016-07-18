package org.roof.security.service;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @deprecated
 * @see org.springframework.security.access.vote.AffirmativeBased
 * @see org.springframework.security.access.vote.ConsensusBased
 * @see org.springframework.security.access.vote.UnanimousBased
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 BaseAccessDecisionManager.java 2012-7-5
 */
@Deprecated
public class BaseAccessDecisionManager implements AccessDecisionManager {

	private final Logger logger = Logger
			.getLogger(BaseAccessDecisionManager.class);

	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		logger.info("访问资源: [" + object + "]");
		while (ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			String needRole = ((SecurityConfig) ca).getAttribute();
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needRole.equals(ga.getAuthority())) { // ga is user's role.
															// ROLE_ANONYMOUS
					return;
				}
			}
		}
		logger.info("当前用户没有访问 " + object + "的权限");
		throw new AccessDeniedException("当前用户没有访问 " + object + "的权限");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
