package org.roof.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.roof.security.entity.BaseRole;
import org.roof.security.entity.BaseUser;
import org.roof.struts2.ICommonActionParameters;

/**
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 CurrentUserCommonActionParameters.java 2013-3-20
 */
public class CurrentUserCommonActionParameters implements ICommonActionParameters {
	private static Map<String, Object> map;
	private String sessionKey;

	@Override
	public Map<String, Object> getParameters() {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put(sessionKey, BaseUserContext.getCurrentUser());
		BaseUser baseUser = (BaseUser) map.get(sessionKey);
		if(baseUser == null){
			baseUser = new BaseUser();
		}
		List<BaseRole> roles = baseUser.getRoles();
		if (CollectionUtils.isNotEmpty(roles)) {
			BaseRole role = roles.get(0);
			map.put(sessionKey + "_RoleId", role.getId());
		}
		return map;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

}
