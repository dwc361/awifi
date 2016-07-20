<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${module.parent.name }</title>
<%@include file="/head.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#backBtn").click(function() {
			history.back(-1);
		});
		$("#subUserForm").ajaxForm({
			type : 'post',
			cache : false,
			dataType : 'json',
			error : function() {
				alert('Ajax信息加载出错,请重试');
			},
			success : function(replay) {
				alert(replay.message);
			}
		});
		$("#updateBtn").click(function() {
			$("#subUserForm").submit();
			return false;
		});
	});
</script>
</head>
<body>
	<div class="bread">
		您的位置： <a href="javascript:void(0)" id="parParNode">${module.parent.parent.parent.name }</a> &gt; <a
			href="javascript:void(0)" id="parNode">${module.parent.parent.name }</a> &gt; <a href="javascript:void(0)"
			id="currNode">${module.parent.name }</a>
	</div>

	<form id="subUserForm" name="subUserForm" method="post" action="${basePath }/uac_account_subUserAction!update.action">
		<input type="hidden" name="subUser.id" value="${subUser.id }" />
		<input type="hidden" name="blj" value="blj" />
		<input type="hidden" name="subUser.user.id" value="${subUser.user.id }" />
		<input type="hidden" name="subUser.sysResource.id" value="${subUser.sysResource.id }" />
		<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 30%"><span></span><font color="red">*</font>账号:</th>
				<td style="width: 70%"><input id="subUser_username" disabled="disabled" class="inpy" name="subUser.username"
					type="text" value="${subUser.username }" /></td>
			</tr>
			<tr>
				<th style="width: 30%"><span></span><font color="red">*</font>姓名:</th>
				<td style="width: 70%"><input id="subUser_username" disabled="disabled" class="inpy" name="subUser.username"
					type="text" value="${subUser.user.name }" /></td>
			</tr>
			<tr>
				<th style="width: 30%"><span></span><font color="red">*</font>权限:</th>
				<td style="width: 70%"><c:forEach var="roles" items="${roleses }" varStatus="status">
						<c:set var="flag" value="false" />
						<c:forEach var="subUserRoles" items="${subUser.roles }">
							<c:if test="${roles.id ==  subUserRoles.id}">
								<input type="checkbox" checked="checked" name="subUser.roles[${status.index }].id" value="${roles.id }" />${roles.name } &nbsp;&nbsp;&nbsp;
								<c:set var="flag" value="true" />
							</c:if>
						</c:forEach>
						<c:if test="${!flag }">
							<input type="checkbox" name="subUser.roles[${status.index }].id" value="${roles.id }" />${roles.name } &nbsp;&nbsp;&nbsp;
						</c:if>
					</c:forEach></td>
			</tr>
		</table>
	</form>
	<div class="btnBox">
		<a id="backBtn" href="javascript:void(0)">
			<p class="sBtn">返回</p>
		</a> <a id="updateBtn" href="javascript:void(0)">
			<p class="sBtn">保存</p>
		</a>
	</div>
</body>
</html>