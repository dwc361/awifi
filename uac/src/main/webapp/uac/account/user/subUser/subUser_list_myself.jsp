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
			您的位置：<a href="javascript:void(0)" id="parNode">自助服务</a> &gt; <a href="javascript:void(0)"
				id="currNode">${module.name }</a>
		</div>
		<div class="main">
			主账号列表：
			<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
				<tr>
					<th>序号</th>
					<th>姓名</th>
					<th>主账号</th>
					<th>密码设置时间</th>
					<th>证书编码</th>
					<th>账号性质</th>
					<th>失效日期</th>
					<th>用户类别</th>
					<th>身份证号</th>
					<th>性别</th>
					<th>电话</th>
					<th>邮箱</th>
					<th>状态</th>
					<!-- <th>操作</th> -->
				</tr>
				<c:forEach var="user" items="${mainUsers }" varStatus="status">
					<tr>
						<td>${status.index + 1 }</td>
						<td>${user.name }</td>
						<td>${user.username }</td>
						<td><fmt:formatDate value="${user.pwdSetTime }" pattern="yyyy-MM-dd" /></td>
						<td>${user.certCoding }</td>
						<td>${user.scope.text }</td>
						<td><fmt:formatDate value="${user.pwdDisableTime }" pattern="yyyy-MM-dd" /></td>
						<td>${user.category.text }</td>
						<td>${user.idNumber }</td>
						<td>${user.gender.text }</td>
						<td>${user.tel }</td>
						<td>${user.mail }</td>
						<td><c:if test="${user.enabled == true}">
							有效
						</c:if> <c:if test="${user.enabled == false}">
							失效
						</c:if></td>
						<%-- <td><a href="uac_account_userAction!update_page_myself.action?user.id=${user.id }">修改</a></td> --%>
					</tr>
				</c:forEach>
			</table>
			<br/>
		
			子账号列表：
			<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
				<tr>
					<th>序号</th>
					<th>子账号</th>
					<th>权限类型</th>
					<th>账号性质</th>
					<th>账号失效时间</th>
					<!-- 
					<th>资源类型</th>
					 -->
					<th>所属系统</th>
					<th>状态</th>
					<!-- <th>操作</th> -->
				</tr>
				<c:forEach var="subUser" items="${subUsers }" varStatus="status">
					<tr>
						<td>${status.index + 1 }</td>
						<td align="center">${subUser.username }</td>
						<td align="center">${subUser.privilege.text }</td>
						<td align="center">${subUser.scope.text }</td>
						<td align="center"><fmt:formatDate value="${subUser.pwdDisableTime }" pattern="yyyy-MM-dd" /></td>
						<!-- 
						<td align="center"><c:choose>
								<c:when test="${subUser.sysResource['class'].simpleName == 'APP'}">
									应用
								</c:when>
								<c:when test="${subUser.sysResource['class'].simpleName == 'Os'}">
									操作系统
								</c:when>
								<c:when test="${subUser.sysResource['class'].simpleName == 'Db'}">
									数据库
								</c:when>
								<c:when test="${subUser.sysResource['class'].simpleName == 'NetDevice'}">
									网络设备
								</c:when>
								<c:when test="${subUser.sysResource['class'].simpleName == 'NetSecurityDevice'}">
									安全设备
								</c:when>
								<c:when test="${subUser.sysResource['class'].simpleName == 'Host'}">
									主机
								</c:when>
								<c:otherwise>
									未知
								</c:otherwise>
							</c:choose></td>
							 -->
						<td align="center">${subUser.sysResource.name }</td>
						<td><c:if test="${subUser.enabled == true}">
						有效
					</c:if> <c:if test="${subUser.enabled == false}">
						失效
					</c:if></td>
					<%-- <td><a href="uac_account_subUserAction!update_page_myself.action?user_id=${subUser.user.id }&subUser.id=${subUser.id }">修改</a></td> --%>
					</tr>
				</c:forEach>
			</table>
		</div>

	</form>
</body>
</html>