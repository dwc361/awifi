package org.aiwifi.bigscreen.bigscreen.service.api;

import java.util.List;
import java.io.Serializable;

import org.roof.roof.dataaccess.api.Page;
import org.aiwifi.bigscreen.bigscreen.entity.Bigscreen;
import org.aiwifi.bigscreen.bigscreen.entity.BigscreenVo;

public interface IBigscreenService {

	/**
	 * 将对象保存，返回该条记录的操作数量，保存成功之后，将主键填充到参数对象中
	 */
	public abstract Serializable save(Bigscreen bigscreen);

	/**
	 * 按对象中的主键进行删除，如果是DRDS，还需要添加拆分键
	 */
	public abstract void delete(Bigscreen bigscreen);
	
	/**
	 * 按对象中的非空属性作为条件，进行删除
	 */
	public abstract void deleteByExample(Bigscreen bigscreen);

	/**
	 * 按对象中的主键进行所有属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void update(Bigscreen bigscreen);
	
	/**
	 * 按对象中的主键进行所有非空属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void updateIgnoreNull(Bigscreen bigscreen);
	
	/**
	 * 按对象中的非空属性作为条件，进行修改
	 */
	public abstract void updateByExample(Bigscreen bigscreen);

	/**
	 * 按对象中的主键进行数据加载，如果是DRDS，还需要添加拆分键
	 */
	public abstract BigscreenVo load(Bigscreen bigscreen);
	
	/**
	 * 按对象中的非空属性作为条件，进行查询实体
	 */
	public abstract BigscreenVo selectForObject(Bigscreen bigscreen);
	
	/**
	 * 按对象中的非空属性作为条件，进行查询列表
	 */
	public abstract List<BigscreenVo> selectForList(Bigscreen bigscreen);
	
	/**
	 * 按对象中的非空属性作为条件，进行分页查询
	 */
	public abstract Page page(Page page, Bigscreen bigscreen);

}