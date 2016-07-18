<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table border="0" cellpadding="0" cellspacing="1" class="ui-table" width="100%">
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">组织名: 
		<input name="org.org_id" type="hidden" value="${org.org_id }" />
		</td>
		<td><input type="text" name=org.org_name style="width: 300px;" value="${org.org_name }" />
		</td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">组织简称: 
		</td>
		<td><input type="text" name="org.alias" style="width: 300px;" value="${org.alias }" />
		</td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">上级节点:</td>
		<td><input type="text" style="width: 300px;" value="${org.parent.org_name }" readonly="readonly" />
		</td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">等级:</td>
		<td><input type="text" style="width: 300px;" name="org.lvl" value="${org.lvl }" readonly="readonly" />
		</td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">排序:</td>
		<td><input type="text" style="width: 300px;" name="org.seq" value="${org.seq }" />
		</td>
	</tr>
</table>