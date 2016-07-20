package ${packagePath}.action;

import java.util.List;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.roof.exceptions.ApplicationException;
import org.roof.struts2.Page;
import org.roof.struts2.Result;
import org.roof.struts2.RoofActionSupport;
import ${packagePath}.entity.${alias?cap_first};
import ${packagePath}.service.${alias?cap_first}Service;
import org.ralasafe.entitle.CustomizedWhere;
import org.roof.webframework.manager.service.UserService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 自动生成
 */
@Component("${alias}Action")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class ${alias?cap_first}Action extends RoofActionSupport {
	private static final long serialVersionUID = 1L;

	private ${alias?cap_first}Service ${alias}Service;

	private UserService userService;// 方便获得当前登录用户

	private ${alias?cap_first} ${alias};

	private int start;

	private String ids;

	/**
	 * 进入主页面
	 * 
	 * @return
	 */
	public String index() {
		result = "/bizmgr/web/${packagePath?substring(packagePath?last_index_of(".")+1)}/${alias}_index.jsp";
		return JSP;
	}

	/**
	 * 查询实例
	 * 
	 * @return
	 */
	public String list() {
		Page page = super.createPage();
		result = ${alias}Service.listForDisplay(${alias}, page);
		return JSON;
		
//		// 经过ralasafe权限过滤
//		CustomizedWhere where = new CustomizedWhere();
//		where.addEqual("type", type);// 添加ralasafe额外条件
//		Map context = new HashMap();
//		context.put("user_id", userService.getLoginUser().getId());// 添加ralasafe过滤条件
//		// 记录总条数 Privilege
//		long totalcount = (long) WebRalasafe.queryCount(
//				ServletActionContext.getRequest(),
//				Privilege.QUERY_CURR, context, where);
//		// 当前页要显示的数据 Privilege
//		Collection dataList = WebRalasafe.query(
//				ServletActionContext.getRequest(),
//				Privilege.QUERY_CURR, context);
//		// 设置返回数据
//		result = dataList;
//		return JSON;
	
	}

	/**
	 * 删除实例
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String delete() throws ApplicationException {
		if (StringUtils.isEmpty(ids)) {
			result = new Result("删除成功!");
			return JSON;
		}
		JSONArray array = JSONArray.fromObject(ids);
		List<${alias?cap_first}> row_ids = (List<${alias?cap_first}>) JSONArray.toCollection(array,
				${alias?cap_first}.class);
		try {
			for (int i = 0; i < row_ids.size(); i++) {
				${alias?cap_first} tempDomain = (${alias?cap_first}) row_ids.get(i);
				${alias}Service.delete(tempDomain);
			}
			result = new Result("删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00002");
		}
		return JSON;
	}

	/**
	 * 保存实例
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public String create() throws ApplicationException {
		try {
			if(${alias} == null){
				${alias} = new ${alias?cap_first}();
			}
			if (${alias}.getId() == null) {
				${alias}Service.save(${alias});
				result = new Result("新增成功!");
			} else {
				${alias}Service.updateIgnoreNull(${alias});
				result = new Result("修改成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(e);
			throw ApplicationException.newInstance("DB00003");
		}
		return JSON;
	}

	@Resource
	public void set${alias?cap_first}Service(${alias?cap_first}Service ${alias}Service) {
		this.${alias}Service = ${alias}Service;
	}
	
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ${alias?cap_first}Service get${alias?cap_first}Service() {
		return ${alias}Service;
	}

	public ${alias?cap_first} get${alias?cap_first}() {
		return ${alias};
	}

	public void set${alias?cap_first}(${alias?cap_first} ${alias}) {
		this.${alias} = ${alias};
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}