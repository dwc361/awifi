package org.roof.web.dictionary.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.roof.web.dictionary.DictionaryVo;
import org.roof.web.dictionary.dao.DictionaryDao;
import org.roof.web.dictionary.entity.Dictionary;
import org.springframework.stereotype.Component;

@Component
public class DictionaryService {
	private DictionaryDao dictionaryDao;

	public List<Dictionary> findByType(String type) {
		return dictionaryDao.findByType(type);
	}

	public List<DictionaryVo> read(String type) {
		if (StringUtils.isBlank(type)) {
			type = "DIC";
		}
		List<Dictionary> dictionaries = new ArrayList<Dictionary>();
		dictionaries = dictionaryDao.findByType(type);
		return this.copyDicList(dictionaries);
	}

	private List<DictionaryVo> copyDicList(List<Dictionary> dictionaries) {
		List<DictionaryVo> result = new ArrayList<DictionaryVo>();
		for (Dictionary dictionary : dictionaries) {
			DictionaryVo dictionaryVo = new DictionaryVo();
			copyProperties(dictionary, dictionaryVo);
			result.add(dictionaryVo);
		}
		return result;
	}

	public List<DictionaryVo> readVal(String type, String val) {
		if (StringUtils.isBlank(val)) {
			val = "S_DIC";
		}
		List<Dictionary> dictionaries = new ArrayList<Dictionary>();
		if(StringUtils.isBlank(type)){
			dictionaries = dictionaryDao.query2(type, val, null, "1");
		}else{
			dictionaries = dictionaryDao.query(type, val, null, "1");
		}
		return this.copyDicList(dictionaries);
	}

	private void copyProperties(Dictionary dictionary, DictionaryVo dictionaryVo) {
		dictionaryVo.setId(dictionary.getId().toString());
		dictionaryVo.setName(dictionary.getText());
		dictionaryVo.setType(dictionary.getType());
		dictionaryVo.setVal(dictionary.getVal());
		dictionaryVo.setLeaf(isLeaf(dictionary));
		dictionaryVo.setDescription(dictionary.getDescription());
	}

	public boolean isLeaf(Dictionary dictionary) {
		return dictionaryDao.findChildrenCount(dictionary.getVal()) == 0;
	}

	/**
	 * 创建一个资源
	 * 
	 * @param type
	 *            module 模块, privilege 资源(权限)
	 * @return
	 */
	public Dictionary create(Long parentId, Dictionary dictionary) {
		if (parentId == null || parentId.longValue() == 0L) {
			parentId = 1L;
		}
		Dictionary parent = dictionaryDao.load(Dictionary.class, parentId);
		dictionary.setType(parent.getVal());
		dictionary.setActive("1");
		dictionaryDao.save(dictionary);
		return dictionary;
	}

	/**
	 * 删除一个组织
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		Dictionary org = dictionaryDao.load(Dictionary.class, id);
		dictionaryDao.delete(org);
	}

	@Resource
	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}

}
