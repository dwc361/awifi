<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账号权限审计</title>
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

		$("#createBtn").click(function() {
			$("#subUserForm").attr("action", $(this).attr('href'));
			$("#subUserForm").submit();
		});

		var table = new ROOF.SelectableTable($('#mainTable'));
		$('#updateBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#subUserForm").attr("action", $(this).attr('href') + '&subUser.id=' + id);
				$("#subUserForm").submit();
			} else {
				alert('请选择一行记录!');
			}
			return false;
		});

		$('#deleteBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				if (confirm("确定要删除吗？")) {
					ROOF.Utils.showBlock();
					$.post($(this).attr('href'), {
						'subUser.id' : id
					}, function(d) {
						alert(d.message);
						ROOF.Utils.hideBlock();
						$("#subUserForm").submit();
					}, 'json');
				}
			} else {
				alert('请选择一行记录!');
			}
			return false;
		});
	});
</script>
</head>
<body>
	<form id="subUserForm" action="${basePath}/uac_account_subUserAction!list_sub_user_log.action?subUser.user.id=${user.id}"
		method="post">
		<div class="bread">
			您的位置：<a href="javascript:void(0)" id="parNode">审计管理</a> &gt; <a href="javascript:void(0)"
				id="currNode">账号权限审计</a>
		</div>
		<div class="main">
			<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
				<tr>
					<th style="width: 15%">子账号：</th>
					<td style="width: 35%"><input type="text" id="subUser_username" name="subUser.username" class="inpy"
						value='${subUser.username }' /></td>
					<th style="width: 15%">&nbsp;</th>
					<td style="width: 35%">&nbsp;</td>
				</tr>

			</table>

			<div class="btnBox">
				<a id="resetBtn" href="javascript:void(0)">
					<p class="sBtn">重置</p>
				</a> <a id="searchBtn" href="javascript:void(0)">
					<p class="sBtn">查询</p>
				</a>
			</div>
			<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
				<tr>
					<th>&nbsp;</th>
					<th>序号</th>
					<th>姓名</th>
					<th>主账号</th>
					<th>子账号</th>
					<th>权限类型</th>
					<th>所属地市</th>
				</tr>
				<c:forEach var="subUser" items="${page.dataList }" varStatus="status">
					<tr>
						<td><input type="checkbox" name="subUsers[${status.index }].id" value="${subUser.id }" /></td>
						<td>${status.index + 1 }</td>
						<td align="center">${subUser.user.name }</td>
						<td align="center">${subUser.user.username }</td>
						<td align="center">${subUser.username }</td>
						<td align="center">${subUser.privilege.text }</td>
						<td align="center">${subUser.user.org.org_name}</td> 
					</tr>
				</c:forEach>
			</table>
			<c:set var="pageForm" value="subUserForm" />
			<%@include file="/uac/uac_page_bar.jsp"%>
		</div>

	</form>
</body>
</html>