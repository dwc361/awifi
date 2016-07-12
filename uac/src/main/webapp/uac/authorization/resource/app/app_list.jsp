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
<script type="text/javascript">
	var table = null;
	function getSelected() {
		return table.getSelectedTrNoClone();
	}
	$(document).ready(function() {
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#appForm"));
		});

		$("#searchBtn").click(function() {
			$("#appForm").submit();
		});

		$("#createBtn").click(function() {
			$("#appForm").attr("action", $(this).attr('href'));
			//$("#appForm").attr("target", "_parent");
			$("#appForm").submit();
		});

		table = new ROOF.SelectableTable($('#mainTable'));
		$('#updateBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#appForm").attr("action", $(this).attr('href') + '?app.id=' + id);
				$("#appForm").submit();
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
						'app.id' : id
					}, function(d) {
						alert(d.message);
						ROOF.Utils.hideBlock();
						$("#appForm").submit();
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
	<form id="appForm" action="${basePath}/uac_authorization_appAction!list.action" method="post">
		<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<th style="width: 15%">名称：</th>
			<td style="width: 35%"><input type="text" id="app_name" name="app.name" class="inpy" value='${app.name }' /></td>
			<th style="width: 15%">路径：</th>
			<td style="width: 35%"><input type="text" id="app_path" name="app.path" class="inpy" value='${app.path }' /></td>
			</tr>
			<tr>
				<th style="width: 15%">描述：</th>
				<td style="width: 35%"><input type="text" id="app_describe" name="app.describe" class="inpy"
					value='${app.describe }' /></td>
				<th style="width: 15%">所属域：</th>
				<td style="width: 35%"><select class="inpy" name="app.region.id" id="app_region_id">
						<option value="">请选择...</option>
						<c:forEach var="obj" items="${regionList}">
							<c:choose>
								<c:when test="${obj.id == app.region.id}">
									<option value="${obj.id }" selected="selected">${obj.text }</option>
								</c:when>
								<c:otherwise>
									<option value="${obj.id }">${obj.text }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select></td>
			<tr>
		</table>
		<div class="btnBox">
			<a id="resetBtn" href="javascript:void(0)">
				<p class="sBtn">重置</p>
			</a> <a id="searchBtn" href="javascript:void(0)">
				<p class="sBtn">查询</p>
			</a> <a id="deleteBtn" href="${basePath}/uac_authorization_appAction!delete.action">
				<p class="sBtn">删除</p>
			</a> <a id="updateBtn" href="${basePath}/uac_authorization_appAction!update_page.action">
				<p class="sBtn">修改</p>
			</a> <a id="createBtn" href="${basePath}/uac_authorization_appAction!create_page.action?app_region_id=${app.region.id}">
				<p class="sBtn">新增</p>
			</a>
		</div>
		<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
			<tr>
				<th>&nbsp;</th>
				<th>名称</th>
				<th>表达式</th>
				<!-- 				<th>路径</th>
 -->
				<th>所属域</th>
				<th>账号状态</th>
			</tr>
			<c:forEach var="app" items="${page.dataList }" varStatus="status">
				<tr>
					<td align="center"><input name="appList.id" id="app_id_${status.index + 1 }" type="checkbox"
						value="${app.id }"></td>
					<td align="center">${app.name }</td>
					<td style="text-align: left;">${app.expression }</td>
					<%-- 					<td style="text-align: left;">${app.path }</td>
 --%>
					<td align="center"><c:forEach var="obj" items="${regionList}">
							<c:choose>
								<c:when test="${obj.id == app.region.id}">
								${obj.text }
								</c:when>
							</c:choose>
						</c:forEach></td>
					<td align="center"><c:forEach var="obj" items="${stateList}">
							<c:choose>
								<c:when test="${obj.id == app.state.id}">
								${obj.text }
								</c:when>
							</c:choose>
						</c:forEach></td>
				</tr>
			</c:forEach>
		</table>
		</div>
		<c:set var="pageForm" value="appForm" />
		<%@include file="/uac/uac_page_bar.jsp"%>
	</form>
</body>
</html>