package org.aiwifi.bigscreen.templates.service.impl;

import java.io.Serializable;
import java.util.List;
import org.roof.roof.dataaccess.api.Page;
import org.aiwifi.bigscreen.templates.dao.api.ITemplatesDao;
import org.aiwifi.bigscreen.templates.entity.Templates;
import org.aiwifi.bigscreen.templates.entity.TemplatesVo;
import org.aiwifi.bigscreen.templates.service.api.ITemplatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TemplatesService implements ITemplatesService {
	private ITemplatesDao templatesDao;

	public Serializable save(Templates templates){
		return templatesDao.save(templates);
	}

	public void delete(Templates templates){
		templatesDao.delete(templates);
	}
	
	public void deleteByExample(Templates templates){
		templatesDao.deleteByExample(templates);
	}

	public void update(Templates templates){
		templatesDao.update(templates);
	}
	
	public void updateIgnoreNull(Templates templates){
		templatesDao.updateIgnoreNull(templates);
	}
		
	public void updateByExample(Templates templates){
		templatesDao.update("updateByExampleTemplates", templates);
	}

	public TemplatesVo load(Templates templates){
		return (TemplatesVo)templatesDao.reload(templates);
	}
	
	public TemplatesVo selectForObject(Templates templates){
		return (TemplatesVo)templatesDao.selectForObject("selectTemplates",templates);
	}
	
	public List<TemplatesVo> selectForList(Templates templates){
		return (List<TemplatesVo>)templatesDao.selectForList("selectTemplates",templates);
	}
	
	public Page page(Page page, Templates templates) {
		return templatesDao.page(page, templates);
	}

	@Autowired
	public void setITemplatesDao(
			@Qualifier("templatesDao") ITemplatesDao  templatesDao) {
		this.templatesDao = templatesDao;
	}
	

}
