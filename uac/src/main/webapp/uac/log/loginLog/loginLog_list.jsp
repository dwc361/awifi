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
<script type="text/javascript" src="${basePath }/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("input[name='loginLog.login_time']").datepicker({
			changeMonth : true,
			changeYear : true
		});
		$("input[name='loginLog.login_time_end']").datepicker({
			changeMonth : true,
			changeYear : true
		});

		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#loginLogForm"));
		});

		$("#searchBtn").click(function() {
			$("#loginLogForm").attr("action", "uac_log_loginLogAction!list.action");
			$("#loginLogForm").submit();
			return false;
		});

		$("#createBtn").click(function() {
			$("#loginLogForm").attr("action", $(this).attr('href'));
			$("#loginLogForm").submit();
		});
		$("#exportBtn").click(function() {
			/* $("#loginLogForm").ajaxSubmit({
				dataType : 'json',
				url : "uac_log_loginLogAction!exportXls.action",
				success : function(d) {
					alert(d.message);
				}
			}); */
			var auth_time = $('#loginLog_login_time').val();
			var auth_time_end = $('#loginLog_login_time_end').val();
			if(auth_time != "" && auth_time_end != ""){
				var oDate1 = new Date(Date.parse(auth_time));
				var oDate2 = new Date(Date.parse(auth_time_end));
				var iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24)
				if(iDays>7){
					alert("认证时间跨度太大，请导一周内数据");
					return false;
				}
			}
			var action = $("#loginLogForm").attr("action");
			$("#loginLogForm").attr("action", "uac_log_loginLogAction!exportXls.action");
			$("#loginLogForm").submit();
			$("#loginLogForm").attr("action", action);
			return false;
		});

		var table = new ROOF.SelectableTable($('#mainTable'));
		$('#updateBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#loginLogForm").attr("action", $(this).attr('href') + '?loginLog.id=' + id);
				$("#loginLogForm").submit();
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
						'loginLog.id' : id
					}, function(d) {
						alert(d.message);
						ROOF.Utils.hideBlock();
						$("#loginLogForm").submit();
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
	<!--
<c:if test="${user.enabled == true}">
	有效
</c:if>
<c:if test="${user.enabled == false}">
	失效
</c:if>
-->
	<form id="loginLogForm" action="${basePath}/uac_log_loginLogAction!list.action" method="post">
		<div class="bread">
			您的位置：<a href="javascript:void(0)" id="parNode">${module.parent.parent.name }</a> &gt; <a href="javascript:void(0)"
				id="currNode">${module.parent.name }</a>
		</div>
		<div class="main">
			<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
				<tr>
					<th style="width: 15%">登陆IP：</th>
					<td style="width: 35%"><input type="text" id="loginLog_ip" name="loginLog.ip" class="inpy"
						value='${loginLog.ip }' /></td>
					<th style="width: 15%">登陆账号：</th>
					<td style="width: 35%"><input type="text" id="loginLog_user_name" name="loginLog.user.username" class="inpy"
						value='${loginLog.user.username }' /></td>
				</tr>
				<tr>
					<th style="width: 15%">登陆认证方式：</th>
					<td style="width: 35%"><select class="inpy" name="loginLog.loginType.id" id="loginLog_loginType_id">
							<option value="">请选择...</option>
							<c:forEach var="obj" items="${loginTypeList}">
								<c:choose>
									<c:when test="${obj.id == loginLog.loginType.id}">
										<option value="${obj.id }" selected="selected">${obj.text }</option>
									</c:when>
									<c:otherwise>
										<option value="${obj.id }">${obj.text }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select></td>
					</td>
					<th style="width: 15%">登陆结果：</th>
					<td style="width: 35%"><select class="inpy" name="loginLog.loginResult.id" id="loginLog_loginResult_id">
							<option value="">请选择...</option>
							<c:forEach var="obj" items="${loginResultList}">
								<c:choose>
									<c:when test="${obj.id == loginLog.loginResult.id}">
										<option value="${obj.id }" selected="selected">${obj.text }</option>
									</c:when>
									<c:otherwise>
										<option value="${obj.id }">${obj.text }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th style="width: 15%">登陆时间：</th>
					<td style="width: 35%"><input type="text" id = "loginLog_login_time" name="loginLog.login_time" readonly
						value='<fmt:formatDate value="${loginLog.login_time }" pattern="yyyy-MM-dd" />' size="10"
						style="margin: 5px 0px 0px 3px; border: 1px solid #e8e8e8; border-radius: 5px;" />--<input type="text"
						id = "loginLog_login_time_end" name="loginLog.login_time_end" readonly
						value='<fmt:formatDate value="${loginLog.login_time_end }" pattern="yyyy-MM-dd" />' size="10"
						style="margin: 5px 0px 0px 3px; border: 1px solid #e8e8e8; border-radius: 5px;" /></td>

					<th style="width: 15%">&nbsp;</th>
					<td style="width: 35%">&nbsp;</td>
				</tr>
			</table>
			<div class="btnBox">
				<a id="resetBtn" href="javascript:void(0)">
					<p class="sBtn">重置</p>
				</a> <a id="searchBtn" href="javascript:void(0)">
					<p class="sBtn">查询</p>
					</a> <a id="exportBtn" href="javascript:void(0)">
						<p class="sBtn">导出</p>
					</a>
				<%-- <a id="deleteBtn" href="${basePath}/uac_log_loginLogAction!delete.action">
				<p class="sBtn">删除</p>
			</a>
			<a id="updateBtn" href="${basePath}/uac_log_loginLogAction!update_page.action">
				<p class="sBtn">修改</p>
			</a>
			<a id="createBtn" href="${basePath}/uac_log_loginLogAction!create_page.action">
				<p class="sBtn">新增</p>
			</a> --%>
			</div>
			<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
				<tr>
					<th>&nbsp;</th>
					<th>登陆IP</th>
					<th>登陆结果</th>
					<th>登陆时间</th>
					<th>登陆认证方式</th>
					<th>登陆账号</th>
					<th>姓名</th>
					<th>所属地市</th>
				</tr>
				<c:forEach var="loginLog" items="${page.dataList }" varStatus="status">
					<tr>
						<td align="center"><input name="loginLogList.id" id="loginLog_id_${status.index + 1 }" type="checkbox"
							value="${loginLog.id }"></td>
						<td align="center">${loginLog.ip }</td>
						<td align="center">${loginLog.loginResult.text }</td>
						<td align="center"><fmt:formatDate value="${loginLog.login_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="center">${loginLog.loginType.text }</td>
						<td align="center">${empty loginLog.user.username ?  loginLog.user.user.username : loginLog.user.username}</td>
						<td align="center">${empty loginLog.user.name ?  loginLog.user.user.name : loginLog.user.name}</td>
						<td align="center">${empty loginLog.user.org.org_name ?  loginLog.user.user.org.org_name : loginLog.user.org.org_name}</td>
					</tr>
				</c:forEach>
			</table>
			<c:set var="pageForm" value="loginLogForm" />
			<%@include file="/uac/uac_page_bar.jsp"%>
		</div>
	</form>
</body>
</html>