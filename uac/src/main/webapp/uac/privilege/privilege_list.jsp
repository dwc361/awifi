<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${module.parent.name }</title>
<%@include file="/head.jsp"%>
<script type="text/javascript" src="${basePath}/roof-web/web/js/ROOF.SelectableTable.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#subUserForm"));
		});

		$("#searchBtn").click(function() {
			$("#subUserForm").submit();
		});

		var table = new ROOF.SelectableTable($('#mainTable'));
		$('#updateBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#subUserForm").attr("action", 'uac_privilegeAction!update_page.action?' + 'subUser.id=' + id);
				$("#subUserForm").submit();
			} else {
				alert('请选择一行记录!');
			}
			return false;
		});

	});
</script>
</head>
<body>
	<div class="bread">
		您的位置：<a href="javascript:void(0)" id="parNode">系统管理</a> &gt; <a href="javascript:void(0)"
			id="currNode">${module.parent.name }</a>
	</div>
	<form id="subUserForm" action="${basePath}/uac_privilegeAction!list.action" method="post">
		<div class="main">
			<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
				<tr>
					<th style="width: 15%">主账号:</th>
					<td style="width: 35%"><input type="text" id="subUser_user_username" name="subUser.user.username" class="inpy"
						value='${subUser.user.username }' /></td>
					<th style="width: 15%">子账号：</th>
					<td style="width: 35%"><input type="text" id="subUser_username" name="subUser.username" class="inpy"
						value='${subUser.username }' /></td>

				</tr>
				<tr>
					<th style="width: 15%">姓名:</th>
					<td style="width: 35%"><input type="text" id="subUser_user_name" name="subUser.user.name" class="inpy"
						value='${subUser.user.name }' /></td>
					<th style="width: 15%">&nbsp;</th>
					<td style="width: 35%">&nbsp;</td>

				</tr>

			</table>

			<div class="btnBox">
				<a id="resetBtn" href="javascript:void(0)">
					<p class="sBtn">重置</p>
				</a> <a id="searchBtn" href="javascript:void(0)">
					<p class="sBtn">查询</p> <%-- </a> <a id="deleteBtn" href="${basePath}/uac_account_subUserAction!delete.action">
					<p class="sBtn">删除</p> --%>
				</a> <a id="updateBtn" href="#">
					<p class="sBtn">修改</p>
				</a>
			</div>
			<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
				<tr>
					<th>&nbsp;</th>
					<th>序号</th>
					<th>姓名</th>
					<th>主账号</th>
					<th>子账号</th>
					<th>系统权限</th>
				</tr>
				<c:forEach var="subUser" items="${page.dataList }" varStatus="status">
					<tr>
						<td><input type="checkbox" id="subUsers[${status.index }].id" value="${subUser.id }" /></td>
						<td>${status.index + 1 }</td>
						<td align="center">${subUser.user.name }</td>
						<td align="center">${subUser.user.username }</td>
						<td align="center">${subUser.username }</td>
						<td align="center"><c:forEach var="roles" items="${subUser.roles }">
							${roles.name }
							</c:forEach></td>
					</tr>
				</c:forEach>
			</table>
			<c:set var="pageForm" value="subUserForm" />
			<%@include file="/uac/uac_page_bar.jsp"%>
		</div>
	</form>
</body>
</html>