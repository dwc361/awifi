package com.awifi.bigscreen.bigscreen.service.impl;

import java.io.Serializable;
import java.util.List;
import org.roof.roof.dataaccess.api.Page;
import com.awifi.bigscreen.bigscreen.dao.api.IBigscreenDao;
import com.awifi.bigscreen.bigscreen.entity.Bigscreen;
import com.awifi.bigscreen.bigscreen.entity.BigscreenVo;
import com.awifi.bigscreen.bigscreen.service.api.IBigscreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BigscreenService implements IBigscreenService {
	private IBigscreenDao bigscreenDao;

	public Serializable save(Bigscreen bigscreen){
		return bigscreenDao.save(bigscreen);
	}

	public void delete(Bigscreen bigscreen){
		bigscreenDao.delete(bigscreen);
	}
	
	public void deleteByExample(Bigscreen bigscreen){
		bigscreenDao.deleteByExample(bigscreen);
	}

	public void update(Bigscreen bigscreen){
		bigscreenDao.update(bigscreen);
	}
	
	public void updateIgnoreNull(Bigscreen bigscreen){
		bigscreenDao.updateIgnoreNull(bigscreen);
	}
		
	public void updateByExample(Bigscreen bigscreen){
		bigscreenDao.update("updateByExampleBigscreen", bigscreen);
	}

	public BigscreenVo load(Bigscreen bigscreen){
		return (BigscreenVo)bigscreenDao.reload(bigscreen);
	}
	
	public BigscreenVo selectForObject(Bigscreen bigscreen){
		return (BigscreenVo)bigscreenDao.selectForObject("selectBigscreen",bigscreen);
	}
	
	@SuppressWarnings("unchecked")
	public List<BigscreenVo> selectForList(Bigscreen bigscreen){
		return (List<BigscreenVo>)bigscreenDao.selectForList("selectBigscreen",bigscreen);
	}
	
	public Page page(Page page, Bigscreen bigscreen) {
		return bigscreenDao.page(page, bigscreen);
	}

	@Autowired
	public void setIBigscreenDao(
			@Qualifier("bigscreenDao") IBigscreenDao  bigscreenDao) {
		this.bigscreenDao = bigscreenDao;
	}
	

}
