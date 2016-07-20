package org.roof.web.resources;

/**
 * 过滤条件组装策略
 * 
 * @author liuxin
 * 
 */
public interface QueryFilterAssemblyStrategy {
	/**
	 * 组合同级条件
	 * 
	 * @param criteria
	 *            条件数组
	 * @return 组合后的条件
	 */
	String assemblePeer(String[] criteria);

	/**
	 * 组合父子级条件
	 * 
	 * @param criteria
	 *            父级条件
	 * @param subCriterias
	 *            子集条件
	 * @return 组合后的条件
	 */
	String assembleSub(String criteria, String subCriterias);

}
