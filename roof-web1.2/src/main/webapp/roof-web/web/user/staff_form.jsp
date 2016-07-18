<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table border="0" cellpadding="0" cellspacing="1" class="ui-table" width="100%">
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">姓名:</td>
		<td><input type="text" name="staff.name" style="width: 300px;" value="${staff.name }" /></td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">用户名:</td>
		<td><input type="text" name="staff.username" style="width: 300px;" value="${staff.username }" /></td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">部门:</td>
		<td><input type="hidden" name="staff.org.org_id" value="${staff.org.org_id }" /><input type="text"
			readonly="readonly" name="staff.org.org_name" style="width: 300px;" value="${staff.org.org_name }" />&nbsp;&nbsp;<a
			id="org_select_btn" class="btn" href="#"> <i class="icon-search"></i> </a></td>
	</tr>

	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">密码:</td>
		<td><input type="password" name="staff.password" style="width: 300px;" value="${staff.password }" /></td>
	</tr>
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">重复密码:</td>
		<td><input type="password" name="repassword" style="width: 300px;" value="${staff.password }" /></td>
	</tr>
</table>
<table border="0" cellpadding="0" cellspacing="1" class="ui-table" width="100%">
	<tr>
		<td class="ui-table-header2" style="text-align: center;" width="20%">角色:</td>
		<td><%@include file="/roof-web/web/role/rolw_select_dialog.jsp"%></td>
	</tr>
</table>
<div id="org_select_dialog" title="请选择部门">
	<div class="ztree" style="height: 80%; overflow: auto;"></div>
</div>