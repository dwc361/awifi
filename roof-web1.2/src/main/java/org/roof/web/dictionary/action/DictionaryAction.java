package org.roof.web.dictionary.action;

import javax.annotation.Resource;

import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import org.roof.web.dictionary.dao.DictionaryDao;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.DictionaryService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("dictionaryAction")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class DictionaryAction extends RoofActionSupport {

	private static final long serialVersionUID = 1L;
	private DictionaryDao dictionaryDao;
	private DictionaryService dictionaryService;
	private Dictionary dictionary;

	public String index() {
		result = "/roof-web/web/dictionary/dictionary_index.jsp";
		return JSP;
	}

	public String read() {
		String type = this.getParamByName("type", String.class);
		result = dictionaryService.read(type);
		return JSON;
	}

	public String readVal() {
		String type = this.getParamByName("type", String.class);
		String val = this.getParamByName("val", String.class);
		result = dictionaryService.readVal(type, val);
		return JSON;
	}

	public String detail() {
		Long id = this.getParamByName("id", Long.class);
		dictionary = dictionaryDao.load(Dictionary.class, id);
		result = "/roof-web/web/dictionary/dictionary_detail.jsp";
		return JSP;
	}

	public String create() {
		Long parentId = this.getParamByName("parentId", Long.class);
		if (dictionary != null) {
			dictionaryService.create(parentId, dictionary);
			result = new Result("保存成功!");
		} else {
			result = new Result("数据传输失败!");
		}
		return JSON;
	}

	public String create_page() {
		Long parentId = this.getParamByName("parentId", Long.class);
		if (parentId == null || parentId.longValue() == 0L) {
			parentId = 1L;
		}
		Dictionary dictionary = dictionaryDao.load(Dictionary.class, parentId);
		this.addParameter("dictionary", dictionary);
		result = "/roof-web/web/dictionary/dictionary_create_page.jsp";
		return JSP;
	}

	public String delete() {
		Long id = this.getParamByName("id", Long.class);
		dictionaryService.delete(id);
		result = new Result("删除成功!");
		return JSON;
	}

	public String update() {
		if (dictionary != null) {
			dictionaryDao.updateIgnoreNull(dictionary);
			result = new Result("保存成功!");
		} else {
			result = new Result("数据传输失败!");
		}
		return JSON;
	}

	public String query() {
		String type = this.getParamByName("type", String.class);
		String val = this.getParamByName("val", String.class);
		String text = this.getParamByName("text", String.class);
		result = dictionaryDao.query(type, val, text, null);
		return JSON;
	}

	@Resource
	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}

	@Resource
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public Dictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

}
