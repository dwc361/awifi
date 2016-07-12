package com.zjhcsoft.uac.cxf;

import javax.jws.WebService;

@WebService
public interface CxfService {
	/**
	 * 从ITSM同步登录用户信息到4A系统
	 * 
	 * @param clientXML
	 * @return
	 */
	public String synchronousUser(String clientXML);

	/**
	 * 用户信息基础数据查询接口
	 * 
	 * @return
	 */
	public String queryBaseData();

	/**
	 * 系统资源查询接口
	 * 
	 * @param clientXML
	 * @return
	 */
	public String queryResourceData(String clientXML);
}