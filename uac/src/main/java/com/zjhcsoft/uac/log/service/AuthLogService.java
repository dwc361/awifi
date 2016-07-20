package com.zjhcsoft.uac.log.service;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.roof.commons.RoofDateUtils;
import org.roof.dataaccess.Page;
import org.roof.spring.CurrentSpringContext;
import org.springframework.stereotype.Component;

import com.zjhcsoft.exceldb.entity.XslDb;
import com.zjhcsoft.exceldb.support.impl.PoiExcelWriter;
import com.zjhcsoft.uac.authorization.resource.service.AppService;
import com.zjhcsoft.uac.log.dao.AuthLogDao;
import com.zjhcsoft.uac.log.entity.AuthLog;
import com.zjhcsoft.uac.util.RoofIbatisDatabaseReader;

/**
 * 自动生成
 */
@Component
public class AuthLogService {

	private AuthLogDao authLogDao;
	private AppService appService;

	/**
	 * 列表展示
	 */
	public Page queryAuthLogPage(AuthLog paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new AuthLog();
		}

		if (paramObj.getAuth_time() != null) {
			paramObj.setAuth_time(RoofDateUtils.startOfDay(paramObj
					.getAuth_time()));
		}
		if (paramObj.getAuth_time_end() != null) {
			paramObj.setAuth_time_end(RoofDateUtils.endOfDay(paramObj
					.getAuth_time_end()));
		}
		return authLogDao.queryAuthLogPage(paramObj, page);
	}

	/**
	 * 保存数据
	 */
	public AuthLog save(AuthLog paramObj) throws Exception {
		authLogDao.save(paramObj);
		return paramObj;
	}

	/**
	 * 删除数据
	 */
	public AuthLog delete(AuthLog paramObj) throws Exception {
		List<AuthLog> list = (List<AuthLog>) authLogDao.findByMappedQuery(
				"AuthLog_exp_select_authLog_list", paramObj);
		for (AuthLog authLog : list) {
			authLogDao.delete(authLog);
		}
		return paramObj;
	}

	/**
	 * 查询数据
	 */
	public List<AuthLog> select(AuthLog paramObj) {
		List<AuthLog> list = (List<AuthLog>) authLogDao.findByMappedQuery(
				"AuthLog_exp_select_authLog_list", paramObj);
		return list;
	}

	/**
	 * 查询数据
	 */
	public AuthLog selectObject(AuthLog paramObj) {
		List<AuthLog> list = this.select(paramObj);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new AuthLog();
		}
	}

	/**
	 * 修改数据
	 */
	public AuthLog update(AuthLog paramObj) throws Exception {
		authLogDao.update(paramObj);
		return paramObj;
	}

	/**
	 * 修改数据，忽略空值
	 */
	public AuthLog updateIgnoreNull(AuthLog paramObj) throws Exception {
		authLogDao.updateIgnoreNull(paramObj);
		return paramObj;
	}

	/**
	 * 根据ID延迟加载持久化对象
	 */
	public AuthLog load(Serializable id) throws Exception {
		AuthLog paramObj = (AuthLog) authLogDao.load(AuthLog.class, id, false);
		return paramObj;
	}

	/**
	 * 加载全部数据
	 */
	public List<AuthLog> loadAll() throws Exception {
		return (List<AuthLog>) authLogDao.loadAll(AuthLog.class);
	}

	public void fillRes(AuthLog authLog) {
		authLog.setSysResource(appService.findByService(authLog.getRequestUrl()));

	}
	

public void exportXls(OutputStream os, AuthLog authlog) {
		RoofIbatisDatabaseReader accountLogExcelExportDatabaseReader = CurrentSpringContext
				.getBean("authlogExcelExportDatabaseReader",
						RoofIbatisDatabaseReader.class);
		XslDb accountLogXslDB = CurrentSpringContext.getBean("authlogXslDB",
				XslDb.class);
		PoiExcelWriter poiExcelWriter = new PoiExcelWriter(accountLogXslDB, os);
		accountLogExcelExportDatabaseReader.setParams(authlog);
		while (accountLogExcelExportDatabaseReader.hasNext()) {
			List<Map<String, Object>> list = accountLogExcelExportDatabaseReader
					.next();
			poiExcelWriter.write(list);
		}
		poiExcelWriter.flush();
	}

	@Resource
	public void setAuthLogDao(AuthLogDao authLogDao) {
		this.authLogDao = authLogDao;
	}

	@Resource
	public void setAppService(AppService appService) {
		this.appService = appService;
	}

}
