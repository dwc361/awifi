package org.roof.web.resources.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 查询过滤资源
 * 
 * @author liuxin
 * 
 */
@Entity
@DiscriminatorValue("query_filter")
public class QueryFilterResource extends QueryResource {
	private Integer useSpel = 0;// 条件中是否使用springel
	private String criteria; // 条件

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public Integer getUseSpel() {
		return useSpel;
	}

	public void setUseSpel(Integer useSpel) {
		this.useSpel = useSpel;
	}

}
