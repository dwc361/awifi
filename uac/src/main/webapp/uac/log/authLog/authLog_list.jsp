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
		$("input[name='authLog.auth_time']").datepicker({
			changeMonth : true,
			changeYear : true
		});
		$("input[name='authLog.auth_time_end']").datepicker({
			changeMonth : true,
			changeYear : true
		});
		

		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#authLogForm"));
		});

		$("#searchBtn").click(function() {
			$("#authLogForm").submit();
		});

		$("#createBtn").click(function() {
			$("#authLogForm").attr("action", $(this).attr('href'));
			$("#authLogForm").submit();
		});
		
		$("#exportBtn").click(function() {
			/* $("#authLogForm").ajaxSubmit({
				dataType : 'json',
				url : "uac_log_authLogAction!exportXls.action",
				success : function(d) {
					alert(d.message);
				}
			}); */
			
			var auth_time = $('#authLog_auth_time').val();
			var auth_time_end = $('#authLog_auth_time_end').val();
			if(auth_time != "" && auth_time_end != ""){
				var oDate1 = new Date(Date.parse(auth_time));
				var oDate2 = new Date(Date.parse(auth_time_end));
				var iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24)
				if(iDays>7){
					alert("认证时间跨度太大，请导一周内数据");
					return false;
				}
			}
			var action = $("#authLogForm").attr("action");
			$("#authLogForm").attr("action", "uac_log_authLogAction!exportXls.action");
			$("#authLogForm").submit();
			$("#authLogForm").attr("action", action);
// 			window.localhost.href="uac_log_accountLogAction!exportXls.action";
			return false;
		});

		var table = new ROOF.SelectableTable($('#mainTable'));
		$('#updateBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#authLogForm").attr("action", $(this).attr('href') + '?authLog.id=' + id);
				$("#authLogForm").submit();
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
						'authLog.id' : id
					}, function(d) {
						alert(d.message);
						ROOF.Utils.hideBlock();
						$("#authLogForm").submit();
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
	<form id="authLogForm" action="${basePath}/uac_log_authLogAction!list.action" method="post">
		<div class="bread">
			您的位置：<a href="javascript:void(0)" id="parNode">${module.parent.parent.name }</a> &gt; <a href="javascript:void(0)"
				id="currNode">${module.parent.name }</a>
		</div>
		<div class="main">
			<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
				<tr>
					<th style="width: 15%">认证时间：</th>
					<td style="width: 35%"><input type="text" id= "authLog_auth_time" name="authLog.auth_time" readonly
						value='<fmt:formatDate value="${authLog.auth_time }" pattern="yyyy-MM-dd" />' size="10"
						style="margin: 5px 0px 0px 3px; border: 1px solid #e8e8e8; border-radius: 5px;" />--<input type="text"
						id= "authLog_auth_time_end" name="authLog.auth_time_end" readonly
						value='<fmt:formatDate value="${authLog.auth_time_end }" pattern="yyyy-MM-dd" />' size="10"
						style="margin: 5px 0px 0px 3px; border: 1px solid #e8e8e8; border-radius: 5px;" /></td>
					<th style="width: 15%">请求地址：</th>
					<td style="width: 35%"><input type="text" id="authLog_sysResource_name" name="authLog.requestUrl" class="inpy"
						value='${authLog.requestUrl }' /></td>
				<tr>
					<th style="width: 15%">认证IP：</th>
					<td style="width: 35%"><input type="text" id="authLog_ip" name="authLog.ip" class="inpy"
						value='${authLog.ip }' /></td>
					<th style="width: 15%">登陆失败原因：</th>
					<td style="width: 35%"><select class="inpy" name="authLog.loginFailReason.id" id="authLog_loginFailReason_id">
							<option value="">请选择...</option>
							<c:forEach var="obj" items="${loginFailReasonList}">
								<c:choose>
									<c:when test="${obj.id == authLog.loginFailReason.id}">
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
					<th style="width: 15%">登陆结果：</th>
					<td style="width: 35%"><select class="inpy" name="authLog.loginResult.id" id="authLog_loginResult_id">
							<option value="">请选择...</option>
							<c:forEach var="obj" items="${loginResultList}">
								<c:choose>
									<c:when test="${obj.id == authLog.loginResult.id}">
										<option value="${obj.id }" selected="selected">${obj.text }</option>
									</c:when>
									<c:otherwise>
										<option value="${obj.id }">${obj.text }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select></td>
					<th style="width: 15%">认证用户：</th>
					<td style="width: 35%"><input type="text" id="authLog_staff_name" name="authLog.staff.name" class="inpy"
						value='${authLog.staff.name }' /></td>
				</tr>
				<tr>
			</table>
			<div class="btnBox">
				<a id="resetBtn" href="javascript:void(0)">
					<p class="sBtn">重置</p>
				</a> <a id="searchBtn" href="javascript:void(0)">
					<p class="sBtn">查询</p>
				</a> <a id="exportBtn" href="javascript:void(0)">
						<p class="sBtn">导出</p>
					</a>
				<%-- <a id="deleteBtn" href="${basePath}/uac_log_authLogAction!delete.action">
				<p class="sBtn">删除</p>
			</a>
			<a id="updateBtn" href="${basePath}/uac_log_authLogAction!update_page.action">
				<p class="sBtn">修改</p>
			</a>
			<a id="createBtn" href="${basePath}/uac_log_authLogAction!create_page.action">
				<p class="sBtn">新增</p>
			</a> --%>
			</div>
			<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
				<tr>
					<th width="5%">&nbsp;</th>
					<th width="20%">认证时间</th>
					<th width="10%">认证IP</th>
					<th width="10%">登陆失败原因</th>
					<th width="10%">登陆结果</th>
					<th width="10%">认证用户</th>
					<th width="10%">认证账号</th>
					<!-- 					<th width="45%">请求地址</th>
 -->
					<th>请求系统</th>
					<th>所属地市</th>
				</tr>
				<c:forEach var="authLog" items="${page.dataList }" varStatus="status">
					<tr>
						<td align="center"><input name="authLogList.id" id="authLog_id_${status.index + 1 }" type="checkbox"
							value="${authLog.id }"></td>
						<td align="center"><fmt:formatDate value="${authLog.auth_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="center">${authLog.ip }</td>
						<td align="center">${authLog.loginFailReason.text }</td>
						<td align="center">${authLog.loginResult.text }</td>
						<td align="center">${authLog.staff.name }</td>
						<td align="center">${authLog.staff.username }</td>						
						<%-- 						<td align="center" style="word-break: break-all;">${authLog.requestUrl }</td>
 --%>
						<td align="center">${authLog.sysResource.name }</td>
						<td align="center">${empty authLog.staff.org.org_name ?  authLog.staff.user.org.org_name : authLog.staff.org.org_name}</td> 
					</tr>
				</c:forEach>
			</table>
			<c:set var="pageForm" value="authLogForm" />
			<%@include file="/uac/uac_page_bar.jsp"%>
		</div>

	</form>
</body>
</html>