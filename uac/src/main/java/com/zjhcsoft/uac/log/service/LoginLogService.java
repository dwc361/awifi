package com.zjhcsoft.uac.log.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.roof.commons.FileUtils;
import org.roof.commons.RoofDateUtils;
import org.roof.dataaccess.Page;
import org.roof.spring.CurrentSpringContext;
import org.roof.struts2.WebUtils;
import org.roof.upload.FileInfo;
import org.roof.upload.FileService;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.zjhcsoft.exceldb.entity.XslDb;
import com.zjhcsoft.exceldb.support.impl.PoiExcelWriter;
import com.zjhcsoft.uac.log.dao.LoginLogDao;
import com.zjhcsoft.uac.log.entity.LoginLog;
import com.zjhcsoft.uac.util.RoofIbatisDatabaseReader;

/**
 * 自动生成
 */
@Component
public class LoginLogService {
	private TaskExecutor taskExecutor;

	private LoginLogDao loginLogDao;
	private FileService fileService;

	private static final Logger LOGGER = Logger
			.getLogger(LoginLogService.class);

	/**
	 * 列表展示
	 */
	public Page queryLoginLogPage(LoginLog paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new LoginLog();
		}

		if (paramObj.getLogin_time() != null) {
			paramObj.setLogin_time(RoofDateUtils.startOfDay(paramObj
					.getLogin_time()));
		}
		if (paramObj.getLogin_time_end() != null) {
			paramObj.setLogin_time_end(RoofDateUtils.endOfDay(paramObj
					.getLogin_time_end()));
		}
		return loginLogDao.queryLoginLogPage(paramObj, page);
	}

	/**
	 * 保存数据
	 */
	public LoginLog save(LoginLog paramObj) throws Exception {
		loginLogDao.save(paramObj);
		return paramObj;
	}

	/**
	 * 删除数据
	 */
	public LoginLog delete(LoginLog paramObj) throws Exception {
		List<LoginLog> list = (List<LoginLog>) loginLogDao.findByMappedQuery(
				"LoginLog_exp_select_loginLog_list", paramObj);
		for (LoginLog loginLog : list) {
			loginLogDao.delete(loginLog);
		}
		return paramObj;
	}

	/**
	 * 查询数据
	 */
	public List<LoginLog> select(LoginLog paramObj) {
		List<LoginLog> list = (List<LoginLog>) loginLogDao.findByMappedQuery(
				"LoginLog_exp_select_loginLog_list", paramObj);
		return list;
	}

	/**
	 * 查询数据
	 */
	public LoginLog selectObject(LoginLog paramObj) {
		List<LoginLog> list = this.select(paramObj);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new LoginLog();
		}
	}

	/**
	 * 修改数据
	 */
	public LoginLog update(LoginLog paramObj) throws Exception {
		loginLogDao.update(paramObj);
		return paramObj;
	}

	/**
	 * 修改数据，忽略空值
	 */
	public LoginLog updateIgnoreNull(LoginLog paramObj) throws Exception {
		loginLogDao.updateIgnoreNull(paramObj);
		return paramObj;
	}

	/**
	 * 根据ID延迟加载持久化对象
	 */
	public LoginLog load(Serializable id) throws Exception {
		LoginLog paramObj = (LoginLog) loginLogDao.load(LoginLog.class, id,
				false);
		return paramObj;
	}

	/**
	 * 加载全部数据
	 */
	public List<LoginLog> loadAll() throws Exception {
		return (List<LoginLog>) loginLogDao.loadAll(LoginLog.class);
	}

	public void exportByTask(LoginLog loginLog) {
		FileInfo fileInfo = FileService.initFileInfo("excel", "登陆日志"
				+ new Date().getTime() + ".xls", false, false);
		String root = FileUtils.formatePath(WebUtils.getServletContext()
				.getRealPath("/"));
		File file = new File(root + fileInfo.getRealPath());
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		FileOutputStream os = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			taskExecutor.execute(new exportRunable(os, loginLog));
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void exportXls(OutputStream os, LoginLog loginLog) {
		RoofIbatisDatabaseReader loginlogExcelExportDatabaseReader = CurrentSpringContext
				.getBean("loginlogExcelExportDatabaseReader",
						RoofIbatisDatabaseReader.class);
		XslDb loginlogXslDB = CurrentSpringContext.getBean("loginlogXslDB",
				XslDb.class);
		PoiExcelWriter poiExcelWriter = new PoiExcelWriter(loginlogXslDB, os);
		loginlogExcelExportDatabaseReader.setParams(loginLog);
		while (loginlogExcelExportDatabaseReader.hasNext()) {
			List<Map<String, Object>> list = loginlogExcelExportDatabaseReader
					.next();
			poiExcelWriter.write(list);
		}
		poiExcelWriter.flush();
	}

	@Resource
	public void setLoginLogDao(LoginLogDao loginLogDao) {
		this.loginLogDao = loginLogDao;
	}

	@Resource(name = "taskExecutor2")
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	@Resource
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	private class exportRunable implements Runnable {
		private OutputStream os;
		private LoginLog loginLog;

		public exportRunable(OutputStream os, LoginLog loginLog) {
			this.loginLog = loginLog;
			this.os = os;
		}

		@Override
		public void run() {

			try {
				exportXls(os, loginLog);
			} catch (Exception e) {
				LOGGER.error(e);
			} finally {
				try {
					os.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}

	}

}
