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
<script type="text/javascript" src="${basePath}/common/js/jquery_ui/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("input[name='whiteIpList.startDate']").datepicker({
			changeMonth : true,
			changeYear : true
		});

		$("input[name='whiteIpList.endDate']").datepicker({
			changeMonth : true,
			changeYear : true
		});

		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#whiteipListForm"));
		});

		$("#searchBtn").click(function() {
			$("#whiteipListForm").submit();
		});

		$("#createBtn").click(function() {
			$("#whiteipListForm").attr("action", $(this).attr('href'));
			$("#whiteipListForm").submit();
			return false;
		});

		var table = new ROOF.SelectableTable($('#mainTable'));
		$('#updateBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#whiteipListForm").attr("action", $(this).attr('href') + '?whiteIpList.id=' + id);
				$("#whiteipListForm").submit();
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
						'whiteIpList.id' : id
					}, function(d) {
						alert(d.message);
						ROOF.Utils.hideBlock();
						$("#whiteipListForm").submit();
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
	<form id="whiteipListForm" action="${basePath}/uac_account_whiteIpListAction!list.action" method="post">
		<div class="bread">
			您的位置：<a href="javascript:void(0)" id="parNode">${module.parent.parent.name }</a> &gt; <a href="javascript:void(0)"
				id="currNode">${module.parent.name }</a>
		</div>
		<div class="main">
			<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
				<tr>
					<th style="width: 15%">IP：</th>
					<td style="width: 35%"><input type="text" id="whiteList_account" name="whiteIpList.whiteIP" class="inpy"
						value='${whiteIpList.whiteIP }' /></td>
					<th style="width: 15%">&nbsp;</th>
					<td style="width: 35%">&nbsp;</td>
				</tr>
				<!-- 
				<tr>

					<th style="width: 15%">开始日期：</th>
					<td style="width: 35%"><input type="text" id="whiteList_startDate" name="whiteIpList.startDate" class="inpy"
						readonly value='<fmt:formatDate value="${whiteIpList.startDate }" pattern="yyyy-MM-dd" />' /></td>
					<th style="width: 15%">结束日期：</th>
					<td style="width: 35%"><input type="text" id="whiteList_endDate" name="whiteIpList.endDate" class="inpy"
						readonly value='<fmt:formatDate value="${whiteIpList.endDate }" pattern="yyyy-MM-dd" />' /></td>
				</tr>
				 -->
			</table>
			<div class="btnBox">
				<a id="resetBtn" href="javascript:void(0)">
					<p class="sBtn">重置</p>
				</a> <a id="searchBtn" href="javascript:void(0)">
					<p class="sBtn">查询</p>
				</a> <a id="deleteBtn" href="${basePath}/uac_account_whiteIpListAction!delete.action">
					<p class="sBtn">删除</p>
				</a> <a id="updateBtn" href="${basePath}/uac_account_whiteIpListAction!update_page.action">
					<p class="sBtn">修改</p>
				</a> <a id="createBtn" href="${basePath}/uac_account_whiteIpListAction!create_page.action">
					<p class="sBtn">新增</p>
				</a>
			</div>
			<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
				<tr>
					<th>&nbsp;</th>
					<th>所属ip部门名称</th>
					<th>IP</th>
					<th>开始日期</th>
					<th>结束日期</th>
				</tr>
				<c:forEach var="whiteIpList" items="${page.dataList }" varStatus="status">
					<tr>
						<td align="center"><input name="whiteipListList.id" id="whiteList_id_${status.index + 1 }" type="checkbox"
							value="${whiteIpList.id }"></td>
						<td align="center">${whiteIpList.nameIP }</td>
						<td align="center">${whiteIpList.whiteIP }</td>  
						<td align="center"><fmt:formatDate value="${whiteIpList.startDate }" pattern="yyyy-MM-dd" /></td>
						<td align="center"><fmt:formatDate value="${whiteIpList.endDate }" pattern="yyyy-MM-dd" /></td>
					</tr>
				</c:forEach>
			</table>
			<%@include file="/uac/uac_page_bar.jsp"%>
		</div>
		<c:set var="pageForm" value="whiteipListForm" />
	</form>
</body>
</html>