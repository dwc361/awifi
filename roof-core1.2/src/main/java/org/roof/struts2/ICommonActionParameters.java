package org.roof.struts2;

import java.util.Map;

/**
 * Action valueStack中需要加入的通用数据
 * 
 * @see BasePathCommonActionParam
 * 
 * @author liuxin 2011-3-28
 * 
 */
public interface ICommonActionParameters {

	Map<String, Object> getParameters();

}
