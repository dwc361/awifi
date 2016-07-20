package com.zjhcsoft.uac.log.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.roof.dataaccess.RoofDaoSupport;

import com.zjhcsoft.uac.log.entity.AuthLog;

import org.roof.dataaccess.Page;
import org.roof.dataaccess.PageQuery;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class AuthLogDao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page queryAuthLogPage(AuthLog paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page,
				"AuthLog_exp_select_authLog_list",
				"AuthLog_exp_select_authLog_count");
		return pageQuery.findByMappedQuery(paramObj);
	}

	public Long continuousErrorTimes(Date start, Date end, String username) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("end", end);
		params.put("username", username);
		return (Long) executeByMappedQuery(
				"com.zjhcsoft.uac.log.dao.AuthLogDao.continuousErrorTimes",
				params);
	}
}
