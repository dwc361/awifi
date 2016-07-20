package ${packagePath}.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 自动生成
 */
@Entity
@Table(name = "${tableName}")
@SequenceGenerator(name = "SEQ_${tableName}", sequenceName = "SEQ_${tableName}", initialValue = 1)
public class ${alias?cap_first} {
	<#list fields as field>
	
	<#if (primaryKey?contains(field.fieldName))>
	@SSPrimaryKey
	</#if>
	${field.stateMent}
	</#list>
	<#list fields as field>
	
	public ${field.fieldType} get${field.fieldName?cap_first}() {
		return ${field.fieldName};
	}

	public void set${field.fieldName?cap_first}(${field.fieldType} ${field.fieldName}) {
		this.${field.fieldName} = ${field.fieldName};
	}
	</#list>

}
