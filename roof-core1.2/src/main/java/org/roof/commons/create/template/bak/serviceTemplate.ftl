package ${packagePath}.service;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import ${packagePath}.entity.${alias?cap_first};
import ${packagePath}.dao.${alias?cap_first}Dao;
import org.roof.struts2.Page;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class ${alias?cap_first}Service {

	private ${alias?cap_first}Dao ${alias}Dao;

	/**
	 * 列表展示
	 */
	public Page listForDisplay(${alias?cap_first} paramObj, Page page) {
		if (paramObj == null) {
			paramObj = new ${alias?cap_first}();
		}
		return ${alias}Dao.listForDisplay(paramObj, page);
	}
	
	/**
	 * 保存数据
	 */
	public ${alias?cap_first} save(${alias?cap_first} paramObj) throws Exception{
		${alias}Dao.save(paramObj);
		return paramObj;
	}
	
	/**
	 * 删除数据
	 */
	public ${alias?cap_first} delete(${alias?cap_first} paramObj) throws Exception{
		${alias}Dao.delete(paramObj);
		return paramObj;
	}
	
	/**
	 * 修改数据
	 */
	public ${alias?cap_first} update(${alias?cap_first} paramObj) throws Exception{
		${alias}Dao.update(paramObj);
		return paramObj;
	}
	
	/**
	 * 修改数据，忽略空值
	 */
	public ${alias?cap_first} updateIgnoreNull(${alias?cap_first} paramObj) throws Exception{
		${alias}Dao.updateIgnoreNull(paramObj);
		return paramObj;
	}
	
	/**
	 * 根据ID延迟加载持久化对象
	 */
	public ${alias?cap_first} load(Serializable id) throws Exception{
		${alias?cap_first} paramObj = (${alias?cap_first}) ${alias}Dao.load(${alias?cap_first}.class, id, false);
		return paramObj;
	}
	
	/**
	 * 加载全部数据
	 */
	@SuppressWarnings("unchecked")
	public List<${alias?cap_first}> loadAll() throws Exception{
		return (List<${alias?cap_first}>) ${alias}Dao.loadAll(${alias?cap_first}.class);
	}

	@Resource
	public void set${alias?cap_first}Dao(${alias?cap_first}Dao ${alias}Dao) {
		this.${alias}Dao = ${alias}Dao;
	}

}
