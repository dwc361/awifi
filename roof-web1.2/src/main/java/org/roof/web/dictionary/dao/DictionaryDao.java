package org.roof.web.dictionary.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roof.commons.Assert;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.exceptions.ApplicationException;
import org.roof.exceptions.DaoException;
import org.roof.web.dictionary.entity.Dictionary;
import org.springframework.stereotype.Component;

/**
 * 字典表 Dao
 * 
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 DictionaryDao.java 2011-11-4
 */
@Component
public class DictionaryDao extends RoofDaoSupport {

	public List<Dictionary> findByType(String type) {
		Dictionary dictionary = new Dictionary();
		dictionary.setType(type);
		dictionary.setActive("1");
		@SuppressWarnings("unchecked")
		List<Dictionary> dictionaries = (List<Dictionary>) super.findByMappedQuery(
				"org.roof.web.dictionary.dao.DictionaryDao.query", dictionary);
		return dictionaries;
	}

	public Long findChildrenCount(String type) {
		String hql = "select count(*) from Dictionary where type = ?";
		return (Long) this.executeForObject(hql, type);
	}

	/**
	 * 获得字典表对象
	 * 
	 * @param type
	 *            类型
	 * @param val
	 *            值
	 * @return 值对象
	 * @throws ApplicationException
	 *             当存在着相同的(类型+值)的时候抛出
	 */
	public Dictionary load(String type, String val) throws ApplicationException {
		Assert.hasText(val);
		Assert.hasText(type);
		Dictionary dictionary = new Dictionary();
		dictionary.setVal(val);
		dictionary.setType(type);
		try {
			dictionary = (Dictionary) super.findByExampleSingle(dictionary);
		} catch (DaoException e) {
			throw ApplicationException.newInstance("DB00001", new Object[] { "S_DICTIONARY" }, e);
		}
		return dictionary;
	}

	public Dictionary loadByTypeText(String type, String text) throws ApplicationException {
		Assert.hasText(text);
		Assert.hasText(type);
		Dictionary dictionary = new Dictionary();
		dictionary.setText(text);
		dictionary.setType(type);
		try {
			dictionary = (Dictionary) super.findByExampleSingle(dictionary);
		} catch (DaoException e) {
			throw ApplicationException.newInstance("DB00001", new Object[] { "S_DICTIONARY" }, e);
		}
		return dictionary;
	}

	public List<Dictionary> query(String type, String val, String text, String active) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("val", val);
		map.put("text", text);
		map.put("active", active);
		@SuppressWarnings("unchecked")
		List<Dictionary> result = (List<Dictionary>) findByMappedQuery(
				"org.roof.web.dictionary.dao.DictionaryDao.query", map);
		return result;
	}

	public List<Dictionary> query2(String type, String val, String text, String active) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("val", val);
		map.put("text", text);
		map.put("active", active);
		@SuppressWarnings("unchecked")
		List<Dictionary> result = (List<Dictionary>) findByMappedQuery(
				"org.roof.web.dictionary.dao.DictionaryDao.query2", map);
		return result;
	}
}