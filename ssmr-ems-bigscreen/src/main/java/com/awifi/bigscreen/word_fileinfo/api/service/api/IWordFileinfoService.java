package com.awifi.bigscreen.word_fileinfo.api.service.api;

import java.util.List;
import java.io.Serializable;

import org.roof.roof.dataaccess.api.Page;
import com.awifi.bigscreen.word_fileinfo.entity.WordFileinfo;
import com.awifi.bigscreen.word_fileinfo.entity.WordFileinfoVo;

public interface IWordFileinfoService {

	/**
	 * 将对象保存，返回该条记录的操作数量，保存成功之后，将主键填充到参数对象中
	 */
	public abstract Serializable save(WordFileinfo wordFileinfo);

	/**
	 * 按对象中的主键进行删除，如果是DRDS，还需要添加拆分键
	 */
	public abstract void delete(WordFileinfo wordFileinfo);
	
	/**
	 * 按对象中的非空属性作为条件，进行删除
	 */
	public abstract void deleteByExample(WordFileinfo wordFileinfo);

	/**
	 * 按对象中的主键进行所有属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void update(WordFileinfo wordFileinfo);
	
	/**
	 * 按对象中的主键进行所有非空属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void updateIgnoreNull(WordFileinfo wordFileinfo);
	
	/**
	 * 按对象中的非空属性作为条件，进行修改
	 */
	public abstract void updateByExample(WordFileinfo wordFileinfo);

	/**
	 * 按对象中的主键进行数据加载，如果是DRDS，还需要添加拆分键
	 */
	public abstract WordFileinfoVo load(WordFileinfo wordFileinfo);
	
	/**
	 * 按对象中的非空属性作为条件，进行查询实体
	 */
	public abstract WordFileinfoVo selectForObject(WordFileinfo wordFileinfo);
	
	/**
	 * 按对象中的非空属性作为条件，进行查询列表
	 */
	public abstract List<WordFileinfoVo> selectForList(WordFileinfo wordFileinfo);
	
	/**
	 * 按对象中的非空属性作为条件，进行分页查询
	 */
	public abstract Page page(Page page, WordFileinfo wordFileinfo);

}