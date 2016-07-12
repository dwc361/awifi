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
			ROOF.Utils.emptyForm($("#dbForm"));
		});

		$("#searchBtn").click(function() {
			$("#dbForm").submit();
		});

		$("#createBtn").click(function() {
			$("#dbForm").attr("action", $(this).attr('href'));
			$("#dbForm").submit();
		});

		table = new ROOF.SelectableTable($('#mainTable'));
		$('#updateBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#dbForm").attr("action", $(this).attr('href') + '?db.id=' + id);
				$("#dbForm").submit();
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
						'db.id' : id
					}, function(d) {
						alert(d.message);
						ROOF.Utils.hideBlock();
						$("#dbForm").submit();
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
	<form id="dbForm" action="${basePath}/uac_authorization_system_dbAction!list.action" method="post">
		<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 15%">所属应用：</th>
				<td style="width: 35%"><input type="text" id="db_app_name" name="db.app.name" class="inpy"
					value='${db.app.name }' /></td>
				<th style="width: 15%">描述：</th>
				<td style="width: 35%"><input type="text" id="db_describe" name="db.describe" class="inpy"
					value='${db.describe }' /></td>
			</tr>
			<tr>
				<th style="width: 15%">ip地址：</th>
				<td style="width: 35%"><input type="text" id="db_ip" name="db.ip" class="inpy" value='${db.ip }' /></td>
				<th style="width: 15%">资源名称：</th>
				<td style="width: 35%"><input type="text" id="db_name" name="db.name" class="inpy" value='${db.name }' /></td>
			</tr>
			<tr>
				<th style="width: 15%">数据库类型：</th>
				<td style="width: 35%"><select class="inpy" name="db.dbType.id" id="db_dbType_id">
						<option value="">请选择...</option>
						<c:forEach var="obj" items="${dbTypeList}">
							<c:choose>
								<c:when test="${obj.id == db.dbType.id}">
									<option value="${obj.id }" selected="selected">${obj.text }</option>
								</c:when>
								<c:otherwise>
									<option value="${obj.id }">${obj.text }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select></td>
				<th style="width: 15%">账号状态：</th>
				<td style="width: 35%"><select class="inpy" name="db.state.id" id="db_state_id">
						<option value="">请选择...</option>
						<c:forEach var="obj" items="${stateList}">
							<c:choose>
								<c:when test="${obj.id == db.state.id}">
									<option value="${obj.id }" selected="selected">${obj.text }</option>
								</c:when>
								<c:otherwise>
									<option value="${obj.id }">${obj.text }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select></td>
			</tr>
		</table>
		<div class="btnBox">
			<a id="resetBtn" href="javascript:void(0)">
				<p class="sBtn">重置</p>
			</a> <a id="searchBtn" href="javascript:void(0)">
				<p class="sBtn">查询</p>
			</a> <a id="deleteBtn" href="${basePath}/uac_authorization_system_dbAction!delete.action">
				<p class="sBtn">删除</p>
			</a> <a id="updateBtn" href="${basePath}/uac_authorization_system_dbAction!update_page.action">
				<p class="sBtn">修改</p>
			</a> <a id="createBtn" href="${basePath}/uac_authorization_system_dbAction!create_page.action">
				<p class="sBtn">新增</p>
			</a>
		</div>
		<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
			<tr>
				<th>&nbsp;</th>
				<th>所属应用</th>
				<th>资源名称</th>
				<th>ip地址</th>
				<th>端口号</th>
				<th>数据库类型</th>
				<th>账号状态</th>
			</tr>
			<c:forEach var="db" items="${page.dataList }" varStatus="status">
				<tr>
					<td align="center"><input name="dbList.id" id="db_id_${status.index + 1 }" type="checkbox" value="${db.id }">
					</td>
					<td align="center">${db.app.name }</td>
					<td align="center">${db.name }</td>
					<td align="center">${db.ip }</td>
					<td align="center">${db.port }</td>
					<td align="center">${db.dbType.text }</td>
					<td align="center">${db.state.text }</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		<c:set var="pageForm" value="dbForm" />
		<%@include file="/uac/uac_page_bar.jsp"%>
	</form>
</body>
</html>