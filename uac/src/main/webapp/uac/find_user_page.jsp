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
<script type="text/javascript" src="${basePath }/common/js/jquery/jquery.validate.rules.js"></script>
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

	});
</script>
</head>
<body>
	<form id="mainForm"
		action="${basePath }/uacAction!find_sms_page.action?user.org.org_id=${user.org.org_id }&unEditable=${unEditable}"
		method="post">
		<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 15%">姓名：<input type="hidden" name="org_id" value="${user.org.org_id }" /></th>
				<td style="width: 35%"><input class="inpy" name="user.name" type="text" value="${user.name }" /></td>

				<th style="width: 15%">主账号：</th>
				<td style="width: 35%"><input class="inpy" name="user.username" type="text" value="${user.username }" /></td>
			</tr>
			<tr>
				<th style="width: 15%">手机号码：</th>
				<td style="width: 35%"><input class="inpy" name="user.tel" type="text" value="${user.idNumber }" /></td>

				<th style="width: 15%"></th>
				<td style="width: 35%"></td>
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
				<!-- <a id="updateIdBtn" href="#">
					<p class="sBtn">修改身份证</p>
				</a>
				<a id="resetPwdBtn" href="#">
					<p class="sBtn">重置密码</p>
				</a>
				<a id="updateUserNameBtn" href="#">
					<p class="sBtn">修改主账号</p>
				</a> -->
				
			</c:if>
		</div>
		<!------end of btnBox-------->
		<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
			<tr>
				<th></th>
				<th>序号</th>
				<th>主账号</th>
				<th>手机号码</th>
				<!--<th>密码设置时间</th>
 				<th>证书编码</th>-->
			
				
				<th>验证码</th>
				<th>是否发送成功</th>
				<th>发送时间</th>
				
			</tr>
			<c:forEach var="sms" items="${page.dataList }" varStatus="status">
				<tr >
					<td><input type="checkbox" <%-- name="users[${status.index }].id" --%> value="${sms.id }" /></td>
					<td>${status.index + 1 }</td>
					<td>${sms.staff_code }</td>
					<td>${sms.tel }</td>
					<td>${sms.message }</td>
					<td><c:if test="${sms.sts== true}">
						成功
					</c:if> <c:if test="${sms.sts == false}">
						失败
					</c:if></td>
					
					<td><fmt:formatDate value="${sms.log_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					
				</tr>
			</c:forEach>
		</table>
		<%@include file="/uac/uac_page_bar.jsp"%>
	</form>
</body>
</html>