package com.zjhcsoft.uac.authorization.resource.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.roof.web.dictionary.entity.Dictionary;

/**
 * 数据库
 * 
 * @author liuxin
 * 
 */
@Entity
@Table(name = "U_DB")
public class Db extends System {

	private Dictionary dbType;// 数据库类型@db_type_id
	private String db_name;//数据库名
	
	public String getDb_name() {
		return db_name;
	}

	public void setDb_name(String db_name) {
		this.db_name = db_name;
	}

	@ManyToOne
	@JoinColumn(name = "db_type_id")
	public Dictionary getDbType() {
		return dbType;
	}

	public void setDbType(Dictionary dbType) {
		this.dbType = dbType;
	}

}
