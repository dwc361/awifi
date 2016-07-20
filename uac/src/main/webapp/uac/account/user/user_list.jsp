<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主账号列表</title>
<%@include file="/head.jsp"%>
<script type="text/javascript" src="${basePath }/roof-web/web/js/ROOF.SelectableTable.js"></script>
<script type="text/javascript">
	var table = null;
	function getSelected() {
		return table.getSelectedTrNoClone();
	}
	$(document).ready(function() {
		$("#resetBtn").click(function() {
			$("#mainForm :input").val('');
		});
		$("#searchBtn").click(function() {
			$("#mainForm").submit();
		});

		table = new ROOF.SelectableTable($('#mainTable'));
		$("#createBtn").click(function() {
			$("#mainForm").attr("action", $(this).attr('href'));
			$("#mainForm").attr("target", "_parent");
			$("#mainForm").submit();
		});
		$('#updateBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#mainForm").attr("action", $(this).attr('href') + "?user.id=" + id);
				$("#mainForm").attr("target", "_parent");
				$("#mainForm").submit();
			} else {
				alert('请选择一行记录!');
			}
			return false;
		});

		$('#subUserBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#mainForm").attr("action", $(this).attr('href') + "?subUser.user.id=" + id);
				$("#mainForm").attr("target", "_parent");
				$("#mainForm").submit();
			} else {
				alert('请选择一行记录!');
			}
			return false;
		});

		$('#deleteBtn').click(function() {
			if (confirm("确定要删除吗？")) {
				var id = table.getSeleted();
				if (id) {
					ROOF.Utils.showBlock();
					$.post($(this).attr('href'), {
						'user.id' : id
					}, function(d) {
						alert(d.message);
						ROOF.Utils.hideBlock();
						window.location.reload();
					}, 'json');
				} else {
					alert('请选择一行记录!');
				}
			}
			return false;
		});
	});
</script>
</head>
<body>
	<form id="mainForm"
		action="${basePath }/uac_account_userAction!list.action?user.org.org_id=${user.org.org_id }&unEditable=${unEditable}"
		method="post">
		<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 15%">姓名：<input type="hidden" name="org_id" value="${user.org.org_id }" /></th>
				<td style="width: 35%"><input class="inpy" name="user.name" type="text" value="${user.name }" /></td>

				<th style="width: 15%">主账号：</th>
				<td style="width: 35%"><input class="inpy" name="user.username" type="text" value="${user.username }" /></td>
			</tr>
			<tr>
				<th style="width: 15%">身份证号：</th>
				<td style="width: 35%"><input class="inpy" name="user.idNumber" type="text" value="${user.idNumber }" /></td>

				<th style="width: 15%">邮箱：</th>
				<td style="width: 35%"><input class="inpy" name="user.mail" type="text" value="${user.mail }" /></td>
			</tr>
		</table>

		<!------btnBox-------->
		<div class="btnBox">
			<a id="resetBtn" href="#">
				<p class="sBtn">重置</p>
			</a> <a id="searchBtn" href="#">
				<p class="sBtn">查询</p>
			</a>
			<c:if test="${empty unEditable || !unEditable}">
				<!-- 
				<a id="deleteBtn" href="${basePath }/uac_account_userAction!delete.action">
					<p class="sBtn">停用</p>
				</a>
				 -->
				<a id="updateBtn" href="${basePath }/uac_account_userAction!update_page.action">
					<p class="sBtn">修改</p>
				</a>
				<a id="createBtn" href="${basePath }/uac_account_userAction!create_page.action">
					<p class="sBtn">新增</p>
				</a>
				</a>
				<a id="subUserBtn" href="${basePath }/uac_account_subUserAction!list.action">
					<p class="sBtn">子账号</p>
				</a>
			</c:if>
		</div>
		<!------end of btnBox-------->
		<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
			<tr>
				<th></th>
				<th>序号</th>
				<th>姓名</th>
				<th>主账号</th>
				<th>身份证号</th>
				<!--<th>密码设置时间</th>
 				<th>证书编码</th>-->
				<th>账号性质</th>
				<th>失效日期</th>
				<!-- <th>用户类别</th> -->
				<!-- 
				<th>身份证号</th> -->
				<th>性别</th>
				<th>电话</th>
				<!-- <th>邮箱</th> -->
				<th>状态</th>
			</tr>
			<c:forEach var="user" items="${page.dataList }" varStatus="status">
				<tr>
					<td><input type="checkbox" <%-- name="users[${status.index }].id" --%> value="${user.id }" /></td>
					<td>${status.index + 1 }</td>
					<td>${user.name }</td>
					<td>${user.username }</td>
					<td>${user.idNumber }</td>
					<%-- <td><fmt:formatDate value="${user.pwdSetTime }" pattern="yyyy-MM-dd" /></td>
					<td>${user.certCoding }</td> --%>
					<td>${user.scope.text }</td>
					<td><fmt:formatDate value="${user.pwdDisableTime }" pattern="yyyy-MM-dd" /></td>
					<%-- <td>${user.category.text }</td> --%>
					<!-- 
					<td>${user.idNumber }</td> -->
					<td>${user.gender.text }</td>
					<td>${user.tel }</td>
					<%-- <td>${user.mail }</td> --%>
					<td><c:if test="${user.enabled == true}">
						有效
					</c:if> <c:if test="${user.enabled == false}">
						失效
					</c:if></td>
				</tr>
			</c:forEach>
		</table>
		<%@include file="/uac/uac_page_bar.jsp"%>
	</form>
</body>
</html>