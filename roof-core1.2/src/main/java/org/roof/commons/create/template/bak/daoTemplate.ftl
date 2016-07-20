package ${packagePath}.dao;

import org.roof.dataaccess.RoofDaoSupport;
import ${packagePath}.entity.${alias?cap_first};
import org.roof.struts2.Page;
import org.roof.struts2.PageQuery;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component
public class ${alias?cap_first}Dao extends RoofDaoSupport {

	/**
	 * 分页信息
	 */
	public Page listForDisplay(${alias?cap_first} paramObj, Page page) {
		PageQuery pageQuery = new PageQuery(page, "select_${alias}_list",
				"select_${alias}_count");
		return pageQuery.select(paramObj);
	}
}
