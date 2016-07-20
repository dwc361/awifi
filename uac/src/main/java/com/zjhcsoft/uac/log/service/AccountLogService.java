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
import com.zjhcsoft.uac.log.dao.AccountLogDao;
import com.zjhcsoft.uac.log.entity.AccountLog;
import com.zjhcsoft.uac.util.RoofIbatisDatabaseReader;

/**
 * 自动生成
 */
@Component
public class AccountLogService {

	private AccountLogDao accountLogDao;

	/**
	 * 列表展示
	 */
	public Page queryAccountLogPage(AccountLog paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new AccountLog();
		}
		if (paramObj.getOp_time() != null) {
			paramObj.setOp_time(RoofDateUtils.startOfDay(paramObj.getOp_time()));
		}
		if (paramObj.getOp_time_end() != null) {
			paramObj.setOp_time_end(RoofDateUtils.endOfDay(paramObj
					.getOp_time_end()));
		}
		return accountLogDao.queryAccountLogPage(paramObj, page);
	}

	/**
	 * 保存数据
	 */
	public AccountLog save(AccountLog paramObj) throws Exception {
		accountLogDao.save(paramObj);
		return paramObj;
	}

	/**
	 * 删除数据
	 */
	public AccountLog delete(AccountLog paramObj) throws Exception {
		List<AccountLog> list = (List<AccountLog>) accountLogDao
				.findByMappedQuery("AccountLog_exp_select_accountLog_list",
						paramObj);
		for (AccountLog accountLog : list) {
			accountLogDao.delete(accountLog);
		}
		return paramObj;
	}

	/**
	 * 查询数据
	 */
	public List<AccountLog> select(AccountLog paramObj) {
		List<AccountLog> list = (List<AccountLog>) accountLogDao
				.findByMappedQuery("AccountLog_exp_select_accountLog_list",
						paramObj);
		return list;
	}

	/**
	 * 查询数据
	 */
	public AccountLog selectObject(AccountLog paramObj) {
		List<AccountLog> list = this.select(paramObj);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new AccountLog();
		}
	}

	/**
	 * 修改数据
	 */
	public AccountLog update(AccountLog paramObj) throws Exception {
		accountLogDao.update(paramObj);
		return paramObj;
	}

	/**
	 * 修改数据，忽略空值
	 */
	public AccountLog updateIgnoreNull(AccountLog paramObj) throws Exception {
		accountLogDao.updateIgnoreNull(paramObj);
		return paramObj;
	}

	/**
	 * 根据ID延迟加载持久化对象
	 */
	public AccountLog load(Serializable id) throws Exception {
		AccountLog paramObj = (AccountLog) accountLogDao.load(AccountLog.class,
				id, false);
		return paramObj;
	}

	/**
	 * 加载全部数据
	 */
	public List<AccountLog> loadAll() throws Exception {
		return (List<AccountLog>) accountLogDao.loadAll(AccountLog.class);
	}

	public void exportXls(OutputStream os, AccountLog accountLog) {
		RoofIbatisDatabaseReader accountLogExcelExportDatabaseReader = CurrentSpringContext
				.getBean("accountLogExcelExportDatabaseReader",
						RoofIbatisDatabaseReader.class);
		XslDb accountLogXslDB = CurrentSpringContext.getBean("accountLogXslDB",
				XslDb.class);
		PoiExcelWriter poiExcelWriter = new PoiExcelWriter(accountLogXslDB, os);
		accountLogExcelExportDatabaseReader.setParams(accountLog);
		while (accountLogExcelExportDatabaseReader.hasNext()) {
			List<Map<String, Object>> list = accountLogExcelExportDatabaseReader
					.next();
			poiExcelWriter.write(list);
		}
		poiExcelWriter.flush();
	}

	@Resource
	public void setAccountLogDao(AccountLogDao accountLogDao) {
		this.accountLogDao = accountLogDao;
	}

}
