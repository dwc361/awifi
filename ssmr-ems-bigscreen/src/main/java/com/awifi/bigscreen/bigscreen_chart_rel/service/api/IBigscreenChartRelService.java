package com.awifi.bigscreen.bigscreen_chart_rel.service.api;

import java.io.Serializable;
import java.util.List;

import org.roof.roof.dataaccess.api.Page;

import com.awifi.bigscreen.bigscreen_chart_rel.entity.BigscreenChartRel;
import com.awifi.bigscreen.bigscreen_chart_rel.entity.BigscreenChartRelVo;

public interface IBigscreenChartRelService {

	/**
	 * 将对象保存，返回该条记录的操作数量，保存成功之后，将主键填充到参数对象中
	 */
	public abstract Serializable save(BigscreenChartRel bigscreenChartRel);

	/**
	 * 按对象中的主键进行删除，如果是DRDS，还需要添加拆分键
	 */
	public abstract void delete(BigscreenChartRel bigscreenChartRel);

	/**
	 * 按对象中的非空属性作为条件，进行删除
	 */
	public abstract void deleteByExample(BigscreenChartRel bigscreenChartRel);

	/**
	 * 按对象中的主键进行所有属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void update(BigscreenChartRel bigscreenChartRel);

	/**
	 * 按对象中的主键进行所有非空属性的修改，如果是DRDS，还需要添加拆分键
	 */
	public abstract void updateIgnoreNull(BigscreenChartRel bigscreenChartRel);

	/**
	 * 按对象中的非空属性作为条件，进行修改
	 */
	public abstract void updateByExample(BigscreenChartRel bigscreenChartRel);

	/**
	 * 按对象中的主键进行数据加载，如果是DRDS，还需要添加拆分键
	 */
	public abstract BigscreenChartRelVo load(BigscreenChartRel bigscreenChartRel);

	/**
	 * 按对象中的非空属性作为条件，进行查询实体
	 */
	public abstract BigscreenChartRelVo selectForObject(BigscreenChartRel bigscreenChartRel);

	/**
	 * 按对象中的非空属性作为条件，进行查询列表
	 */
	public abstract List<BigscreenChartRelVo> selectForList(BigscreenChartRel bigscreenChartRel);

	/**
	 * 按对象中的非空属性作为条件，进行分页查询
	 */
	public abstract Page page(Page page, BigscreenChartRel bigscreenChartRel);

}