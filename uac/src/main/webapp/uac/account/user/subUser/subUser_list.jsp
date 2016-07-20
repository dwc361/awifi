<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${module.name }</title>
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
	<form id="subUserForm" action="${basePath}/uac_account_subUserAction!list.action?subUser.user.id=${user.id}"
		method="post">
		<div class="bread">
			您的位置：<a href="javascript:void(0)" id="parNode">${module.parent.parent.name }</a> &gt; <a href="javascript:void(0)"
				id="currNode">${module.parent.name }</a> &gt; ${user.name }
		</div>
		<div class="main">
			<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
				<tr>
					<th style="width: 15%">用户名：</th>
					<td style="width: 35%"><input type="text" id="subUser_username" name="subUser.username" class="inpy"
						value='${subUser.username }' /></td>
					<th style="width: 15%">&nbsp;</th>
					<td style="width: 35%">&nbsp;</td>
				</tr>

			</table>

			<div class="btnBox">
				<a id="backBtn" href="${basePath}/uac_account_userAction!index.action?user.org.org_id=${user.org.org_id}">
					<p class="sBtn">返回</p>
				</a> <a id="resetBtn" href="javascript:void(0)">
					<p class="sBtn">重置</p>
				</a> <a id="searchBtn" href="javascript:void(0)">
					<p class="sBtn">查询</p>
				<%-- </a> <a id="deleteBtn" href="${basePath}/uac_account_subUserAction!delete.action">
					<p class="sBtn">删除</p> --%>
				</a> <a id="updateBtn" href="${basePath}/uac_account_subUserAction!update_page.action?user_id=${user.id}">
					<p class="sBtn">修改</p>
				</a> <a id="createBtn" href="${basePath}/uac_account_subUserAction!create_page.action?user_id=${user.id}">
					<p class="sBtn">新增</p>
				</a>
			</div>
			<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
				<tr>
					<th>&nbsp;</th>
					<th>序号</th>
					<th>子账号</th>
					<th>权限类型</th>
					<th>账号性质</th>
					<th>账号失效时间</th>
					<th>资源类型</th>
					<th>所属系统</th>
					<th>状态</th>
				</tr>
				<c:forEach var="subUser" items="${page.dataList }" varStatus="status">
					<tr>
						<td><input type="checkbox" name="subUsers[${status.index }].id" value="${subUser.id }" /></td>
						<td>${status.index + 1 }</td>
						<td align="center">${subUser.username }</td>
						<td align="center">${subUser.privilege.text }</td>
						<td align="center">${subUser.scope.text }</td>
						<td align="center"><fmt:formatDate value="${subUser.pwdDisableTime }" pattern="yyyy-MM-dd" /></td>
						<td align="center">
							<c:choose>
								<c:when test="${subUser.sysResource.resourcetype == 'App'}">
									应用
								</c:when>
								<c:when test="${subUser.sysResource.resourcetype == 'Os'}">
									操作系统
								</c:when>
								<c:when test="${subUser.sysResource.resourcetype == 'Db'}">
									数据库
								</c:when>
								<c:when test="${subUser.sysResource.resourcetype == 'NetDevice'}">
									网络设备
								</c:when>
								<c:when test="${subUser.sysResource.resourcetype == 'NetSecurityDevice'}">
									安全设备
								</c:when>
								<c:when test="${subUser.sysResource.resourcetype == 'Host'}">
									主机
								</c:when>
								<c:otherwise>
									未知
								</c:otherwise>
							</c:choose>
							</td>
						<td align="center">${subUser.sysResource.name }</td>
						<td><c:if test="${subUser.enabled == true}">
						有效
					</c:if> <c:if test="${subUser.enabled == false}">
						失效
					</c:if></td>
					</tr>
				</c:forEach>
			</table>
			<c:set var="pageForm" value="subUserForm" />
			<%@include file="/uac/uac_page_bar.jsp"%>
		</div>

	</form>
</body>
</html>