<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table border="0" cellpadding="0" cellspacing="1" class="ui-table" width="100%">
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">名称:</td>
		<td><input type="text" name="roles.name" style="width: 300px;" value="${roles.name }" /></td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">描述:</td>
		<td><textarea name="roles.description" cols="80" rows="10">${roles.description }</textarea></td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">资源:</td>
		<td>
			<div class="ztree" style="width: 20%; height: 100%; float: left;"></div></td>
	</tr>
</table>