package com.awifi.bigscreen.bigscreen_chart_rel.service.impl;

import java.io.Serializable;
import java.util.List;

import org.roof.roof.dataaccess.api.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.awifi.bigscreen.bigscreen_chart_rel.dao.api.IBigscreenChartRelDao;
import com.awifi.bigscreen.bigscreen_chart_rel.entity.BigscreenChartRel;
import com.awifi.bigscreen.bigscreen_chart_rel.entity.BigscreenChartRelVo;
import com.awifi.bigscreen.bigscreen_chart_rel.service.api.IBigscreenChartRelService;

@Service
public class BigscreenChartRelService implements IBigscreenChartRelService {
	private IBigscreenChartRelDao bigscreenChartRelDao;

	public Serializable save(BigscreenChartRel bigscreenChartRel){
		return bigscreenChartRelDao.save(bigscreenChartRel);
	}

	public void delete(BigscreenChartRel bigscreenChartRel){
		bigscreenChartRelDao.delete(bigscreenChartRel);
	}
	
	public void deleteByExample(BigscreenChartRel bigscreenChartRel){
		bigscreenChartRelDao.deleteByExample(bigscreenChartRel);
	}

	public void update(BigscreenChartRel bigscreenChartRel){
		bigscreenChartRelDao.update(bigscreenChartRel);
	}
	
	public void updateIgnoreNull(BigscreenChartRel bigscreenChartRel){
		bigscreenChartRelDao.updateIgnoreNull(bigscreenChartRel);
	}
		
	public void updateByExample(BigscreenChartRel bigscreenChartRel){
		bigscreenChartRelDao.update("updateByExampleBigscreenChartRel", bigscreenChartRel);
	}

	public BigscreenChartRelVo load(BigscreenChartRel bigscreenChartRel){
		return (BigscreenChartRelVo)bigscreenChartRelDao.reload(bigscreenChartRel);
	}
	
	public BigscreenChartRelVo selectForObject(BigscreenChartRel bigscreenChartRel){
		return (BigscreenChartRelVo)bigscreenChartRelDao.selectForObject("selectBigscreenChartRel",bigscreenChartRel);
	}
	
	public List<BigscreenChartRelVo> selectForList(BigscreenChartRel bigscreenChartRel){
		return (List<BigscreenChartRelVo>)bigscreenChartRelDao.selectForList("selectBigscreenChartRel",bigscreenChartRel);
	}
	
	public Page page(Page page, BigscreenChartRel bigscreenChartRel) {
		return bigscreenChartRelDao.page(page, bigscreenChartRel);
	}

	@Autowired
	public void setIBigscreenChartRelDao(
			@Qualifier("bigscreenChartRelDao") IBigscreenChartRelDao  bigscreenChartRelDao) {
		this.bigscreenChartRelDao = bigscreenChartRelDao;
	}
	

}
