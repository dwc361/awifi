package org.roof.dataaccess;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务类的父类，拥有基本的数据库操作方法
 */
@Component("roofServiceSupport")
@Transactional
public class RoofServiceSupport {

	protected RoofDaoSupport roofDaoSupport;

	@Resource(name = "roofDaoSupport")
	public void setRoofDaoSupport(RoofDaoSupport roofDaoSupport) {
		this.roofDaoSupport = roofDaoSupport;
	}

	public Object load(Class<?> entityClass, Serializable id) {
		return roofDaoSupport.load(entityClass, id);
	}

	/***************************************************************************
	 * 保存实体对象 时间： 2011-1-10
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 **************************************************************************/
	public Serializable save(Object object) throws Exception {
		return roofDaoSupport.save(object);
	}

	/***************************************************************************
	 * 删除实体对象，以参数中的主键为条件 时间： 2011-1-10
	 * 
	 * @param object
	 * @throws Exception
	 **************************************************************************/
	public void delete(Object object) throws Exception {
		roofDaoSupport.delete(object);
	}

	/***************************************************************************
	 * 删除实体对象，传入多个对象的主键，批量删除 时间： 2011-1-10
	 * 
	 * @param object
	 * @param ids
	 * @throws Exception
	 **************************************************************************/
	public void deleteByIds(Class<?> clazz, long[] ids) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			Object object = this.load(clazz, ids[i]);
			this.delete(object);
		}
	}

	/***************************************************************************
	 * 删除参数中符合条件的所有数据 时间： 2011-1-10
	 * 
	 * @param object
	 * @throws Exception
	 **************************************************************************/
	public void deleteByExample(Object object) throws Exception {
		roofDaoSupport.deleteByExample(object);
	}

	/***************************************************************************
	 * 修改实体对象，以参数中的主键为条件 时间： 2011-1-10
	 * 
	 * @param object
	 * @throws Exception
	 **************************************************************************/
	public void update(Object object) {
		roofDaoSupport.update(object);
	}

	/***************************************************************************
	 * 修改实体对象，以参数中的主键为条件，忽略非空的属性 时间： 2011-1-10
	 * 
	 * @param object
	 * @param primaryKey
	 * @throws Exception
	 **************************************************************************/
	public void updateIgnoreNull(Object object, Serializable primaryKey)
			throws Exception {
		roofDaoSupport.updateIgnoreNull(object, primaryKey);
	}

	public void updateIgnoreNull(Object object) throws Exception {
		roofDaoSupport.updateIgnoreNull(object);
	}

	/***************************************************************************
	 * 按参数中非空的值作为查询条件，返回null或者唯一对象，多个则抛出异常 时间： 2011-1-10
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 **************************************************************************/
	public Object findByExampleSingle(Object object) throws Exception {
		return roofDaoSupport.findByExampleSingle(object);
	}

	/***************************************************************************
	 * 以参数非空值作为条件，返回所有记录 时间： 2011-1-10
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 **************************************************************************/
	public List<?> findByExample(Object object) throws Exception {
		return roofDaoSupport.findByExample(object);
	}

	/***************************************************************************
	 * 查找当前类中的所有数据 时间： 2011-1-10
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 **************************************************************************/
	public List<?> loadAll(Class<?> clazz) throws Exception {
		return roofDaoSupport.loadAll(clazz);
	}
}
