package ${packagePath}.vo;

import java.util.Date;
import java.util.List;

/**
 * 自动生成
 */
public class ${alias?cap_first}Vo {
	<#list fields as field>
	
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
