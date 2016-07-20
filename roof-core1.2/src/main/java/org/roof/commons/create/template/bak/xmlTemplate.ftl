<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE sqlMap PUBLIC "-//iBATIS.com//DTD SQL Map 2.0//EN" "http://www.ibatis.com/dtd/sql-map-2.dtd">
<sqlMap namespace="${alias}_exp">
	<select id="select_${alias}_list" parameterClass="${packagePath}.entity.${alias?cap_first}"
		resultClass="${packagePath}.entity.${alias?cap_first}">
		SELECT * FROM
		(
		SELECT A.*, ROWNUM RN_
		FROM (
		SELECT * FROM ${tableName}
		WHERE 1=1
		<#list fields as field>
		<#assign isForeign = false />
		<#list relations as relation>
		<#if (relation.foreignCol == field.fieldName)>
		<#assign isForeign = true />
		<isNotEmpty prepend="and" property="${relation.alias}.${relation.primaryCol}"> 
		<![CDATA[ 
		${field.fieldName} in (select ${relation.primaryCol} from ${relation.primaryTable} where ${relation.primaryCol} = #${relation.alias}.${relation.primaryCol}#)
		]]> 
		</isNotEmpty>
		</#if>
		</#list>
		<#if (!(isForeign))>
		<isNotEmpty prepend="and" property="${field.fieldName}"> 
		<![CDATA[ 
		${field.fieldName} 
		<#if (field.dbType == "NUMBER")>
		= #${field.fieldName}#
		<#else>
		like '%' || #${field.fieldName}# || '%'
		</#if>
		]]> 
		</isNotEmpty>
		</#if>
		</#list>
		<![CDATA[
		order by <#list primaryKey as key>${key}<#if (key_index != (primaryKey?size-1))>, </#if></#list> desc 
		]]>
		) A
		<![CDATA[
		WHERE ROWNUM <= #lastrownum#
		)
		WHERE RN_ > #firstrownum#
		]]>
	</select>
	
	<select id="select_${alias}_count" parameterClass="${packagePath}.entity.${alias?cap_first}"
		resultClass="java.lang.Long">
		<![CDATA[ 
		select count(1) from ${tableName} WHERE 1 = 1
		]]> 
		<#list fields as field>
		<#assign isForeign = false />
		<#list relations as relation>
		<#if (relation.foreignCol == field.fieldName)>
		<#assign isForeign = true />
		<isNotEmpty prepend="and" property="${relation.alias}.${relation.primaryCol}"> 
		<![CDATA[ 
		${field.fieldName} in (select ${relation.primaryCol} from ${relation.primaryTable} where ${relation.primaryCol} = #${relation.alias}.${relation.primaryCol}#)
		]]> 
		</isNotEmpty>
		</#if>
		</#list>
		<#if (!(isForeign))>
		<isNotEmpty prepend="and" property="${field.fieldName}"> 
		<![CDATA[ 
		${field.fieldName} 
		<#if (field.dbType == "NUMBER")>
		= #${field.fieldName}#
		<#else>
		like '%' || #${field.fieldName}# || '%'
		</#if>
		]]> 
		</isNotEmpty>
		</#if>
		</#list>
	</select>

</sqlMap>