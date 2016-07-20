package org.roof.web.cache.action;

import javax.annotation.Resource;

import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author liuxin
 *
 */
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class CacheAction extends RoofActionSupport {

	private static final long serialVersionUID = 1L;
	private CacheManager cacheManager;

	public String list() {
		this.addParameter("cacheNames", cacheManager.getCacheNames());
		result = "/roof-web/web/cache/cache_list.jsp";
		return JSP;
	}

	public String delete() {
		String cacheName = this.getParamByName("cacheName", String.class);
		Cache cache = cacheManager.getCache(cacheName);
		cache.clear();
		result = new Result("清除成功！");
		return JSON;
	}

	@Resource(name = "cacheManager")
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

}
