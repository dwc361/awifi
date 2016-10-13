package com.awifi.bigscreen.word_fileinfo.impl.service.impl;

import com.awifi.bigscreen.word_fileinfo.api.dao.api.IWordFileinfoDao;
import com.awifi.bigscreen.word_fileinfo.entity.WordFileinfo;
import com.awifi.bigscreen.word_fileinfo.entity.WordFileinfoVo;
import com.awifi.bigscreen.word_fileinfo.api.service.api.IWordFileinfoService;
import org.roof.roof.dataaccess.api.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class WordFileinfoService implements IWordFileinfoService {
	private IWordFileinfoDao wordFileinfoDao;

	public Serializable save(WordFileinfo wordFileinfo){
		return wordFileinfoDao.save(wordFileinfo);
	}

	public void delete(WordFileinfo wordFileinfo){
		wordFileinfoDao.delete(wordFileinfo);
	}
	
	public void deleteByExample(WordFileinfo wordFileinfo){
		wordFileinfoDao.deleteByExample(wordFileinfo);
	}

	public void update(WordFileinfo wordFileinfo){
		wordFileinfoDao.update(wordFileinfo);
	}
	
	public void updateIgnoreNull(WordFileinfo wordFileinfo){
		wordFileinfoDao.updateIgnoreNull(wordFileinfo);
	}
		
	public void updateByExample(WordFileinfo wordFileinfo){
		wordFileinfoDao.update("updateByExampleWordFileinfo", wordFileinfo);
	}

	public WordFileinfoVo load(WordFileinfo wordFileinfo){
		return (WordFileinfoVo)wordFileinfoDao.reload(wordFileinfo);
	}
	
	public WordFileinfoVo selectForObject(WordFileinfo wordFileinfo){
		return (WordFileinfoVo)wordFileinfoDao.selectForObject("selectWordFileinfo",wordFileinfo);
	}
	
	public List<WordFileinfoVo> selectForList(WordFileinfo wordFileinfo){
		return (List<WordFileinfoVo>)wordFileinfoDao.selectForList("selectWordFileinfo",wordFileinfo);
	}
	
	public Page page(Page page, WordFileinfo wordFileinfo) {
		return wordFileinfoDao.page(page, wordFileinfo);
	}

	@Autowired
	public void setIWordFileinfoDao(
			@Qualifier("wordFileinfoDao") IWordFileinfoDao  wordFileinfoDao) {
		this.wordFileinfoDao = wordFileinfoDao;
	}

}
