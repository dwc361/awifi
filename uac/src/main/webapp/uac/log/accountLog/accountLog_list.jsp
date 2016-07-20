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
		$("input[name='accountLog.op_time']").datepicker({
			changeMonth : true,
			changeYear : true
		});
		$("input[name='accountLog.op_time_end']").datepicker({
			changeMonth : true,
			changeYear : true
		});

		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#accountLogForm"));
		});

		$("#searchBtn").click(function() {
			$("#accountLogForm").attr("action", "uac_log_accountLogAction!list.action");
			$("#accountLogForm").submit();
			return false;
		});

		$("#createBtn").click(function() {
			$("#accountLogForm").attr("action", $(this).attr('href'));
			$("#accountLogForm").submit();
			return false;
		});

		$("#exportBtn").click(function() {
			var action = $("#accountLogForm").attr("action");
			$("#accountLogForm").attr("action", "uac_log_accountLogAction!exportXls.action");
			$("#accountLogForm").submit();
			$("#accountLogForm").attr("action", action);
// 			window.localhost.href="uac_log_accountLogAction!exportXls.action";
			return false;
		});

		var table = new ROOF.SelectableTable($('#mainTable'));
		$('#updateBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#accountLogForm").attr("action", $(this).attr('href') + '?accountLog.id=' + id);
				$("#accountLogForm").submit();
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
						'accountLog.id' : id
					}, function(d) {
						alert(d.message);
						ROOF.Utils.hideBlock();
						$("#accountLogForm").submit();
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
	<form id="accountLogForm" action="${basePath}/uac_log_accountLogAction!list.action" method="post">
		<div class="bread">
			您的位置：<a href="javascript:void(0)" id="parNode">${module.parent.parent.name }</a> &gt; <a href="javascript:void(0)"
				id="currNode">${module.parent.name }</a>
		</div>
		<div class="main">
			<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
				<tr>
					<th style="width: 15%">操作账号：</th>
					<td style="width: 35%"><input type="text" id="accountLog_opStaff_name" name="accountLog.opStaff.username"
						class="inpy" value='${accountLog.opStaff.username }' /></td>
					<th style="width: 15%">操作时间：</th>
					<td style="width: 35%"><input type="text" id="accountLog_op_time" name="accountLog.op_time" readonly
						value='<fmt:formatDate value="${accountLog.op_time }" pattern="yyyy-MM-dd" />' size="10"
						style="margin: 5px 0px 0px 3px; border: 1px solid #e8e8e8; border-radius: 5px;" />--<input type="text"
						id="accountLog_op_time_end" name="accountLog.op_time_end" readonly
						value='<fmt:formatDate value="${accountLog.op_time_end }" pattern="yyyy-MM-dd" />' size="10"
						style="margin: 5px 0px 0px 3px; border: 1px solid #e8e8e8; border-radius: 5px;" /></td>
				</tr>
				<tr>
					<th style="width: 15%">账号操作类型：</th>
					<td style="width: 35%"><select class="inpy" name="accountLog.opType.id" id="accountLog_opType_id">
							<option value="">请选择...</option>
							<c:forEach var="obj" items="${opTypeList}">
								<c:choose>
									<c:when test="${obj.id == accountLog.opType.id}">
										<option value="${obj.id }" selected="selected">${obj.text }</option>
									</c:when>
									<c:otherwise>
										<option value="${obj.id }">${obj.text }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					</select></td>
					<th style="width: 15%">操作人：</th>
					<td style="width: 35%"><input type="text" id="accountLog_user_name" name="accountLog.user.name" class="inpy"
						value='${accountLog.user.name }' /></td>
				</tr>
			</table>
			<div class="btnBox">
				<a id="exportBtn" href="javascript:void(0)">
					<p class="sBtn">导出</p>
				</a> <a id="resetBtn" href="javascript:void(0)">
					<p class="sBtn">重置</p>
				</a> <a id="searchBtn" href="javascript:void(0)">
					<p class="sBtn">查询</p>
				</a>
				<%-- <a id="deleteBtn" href="${basePath}/uac_log_accountLogAction!delete.action">
				<p class="sBtn">删除</p>
			</a>
			<a id="updateBtn" href="${basePath}/uac_log_accountLogAction!update_page.action">
				<p class="sBtn">修改</p>
			</a>
			<a id="createBtn" href="${basePath}/uac_log_accountLogAction!create_page.action">
				<p class="sBtn">新增</p>
			</a> --%>
			</div>
			<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
				<tr>
					<th>&nbsp;</th>
					<th>操作结果</th>
					<th>被操作账号</th>
					<th>操作时间</th>
					<th>账号操作类型</th>
					<th>操作账号</th>
					<th>姓名</th>
					<th>所属地市</th>
				</tr>
				<c:forEach var="accountLog" items="${page.dataList }" varStatus="status">
					<tr>
						<td align="center"><input name="accountLogList.id" id="accountLog_id_${status.index + 1 }" type="checkbox"
							value="${accountLog.id }"></td>
						<td align="center">${accountLog.op_result }</td>
						<td align="center">${accountLog.opStaff.username }</td>
						<td align="center"><fmt:formatDate value="${accountLog.op_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="center">${accountLog.opType.text }</td>
						<td align="center">${accountLog.user.user.username }</td>
						<td align="center">${accountLog.user.user.name }</td>
						<td align="center">${empty accountLog.user.org.org_name ?  accountLog.user.user.org.org_name : accountLog.user.org.org_name}</td> 
					</tr>
				</c:forEach>
			</table>
			<c:set var="pageForm" value="accountLogForm" />
			<%@include file="/uac/uac_page_bar.jsp"%>
		</div>
		</div>

	</form>
</body>
</html>