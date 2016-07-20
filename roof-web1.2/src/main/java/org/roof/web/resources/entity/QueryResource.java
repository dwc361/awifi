package org.roof.web.resources.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 查询资源<br>
 * {@link #getIdentify()} 对应SqlMap中的查询ID
 * 
 * @author liuxin
 * 
 */
@Entity
@DiscriminatorValue("query")
public class QueryResource extends Module {

}
