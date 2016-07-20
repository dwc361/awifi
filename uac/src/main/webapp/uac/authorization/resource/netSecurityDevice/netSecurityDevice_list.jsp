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
			ROOF.Utils.emptyForm($("#netSecurityDeviceForm"));
		});

		$("#searchBtn").click(function() {
			$("#netSecurityDeviceForm").submit();
		});

		$("#createBtn").click(function() {
			$("#netSecurityDeviceForm").attr("action", $(this).attr('href'));
			$("#netSecurityDeviceForm").submit();
		});

		table = new ROOF.SelectableTable($('#mainTable'));
		$('#updateBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#netSecurityDeviceForm").attr("action", $(this).attr('href') + '?netSecurityDevice.id=' + id);
				$("#netSecurityDeviceForm").submit();
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
						'netSecurityDevice.id' : id
					}, function(d) {
						alert(d.message);
						ROOF.Utils.hideBlock();
						$("#netSecurityDeviceForm").submit();
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
	<form id="netSecurityDeviceForm" action="${basePath}/uac_authorization_system_netSecurityDeviceAction!list.action"
		method="post">
		<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 15%">所属应用：</th>
				<td style="width: 35%"><input type="text" id="netSecurityDevice_app_name" name="netSecurityDevice.app.name"
					class="inpy" value='${netSecurityDevice.app.name }' /></td>
				<th style="width: 15%">描述：</th>
				<td style="width: 35%"><input type="text" id="netSecurityDevice_sysdescribe" name="netSecurityDevice.sysdescribe"
					class="inpy" value='${netSecurityDevice.sysdescribe }' /></td>
			</tr>
			<tr>
				<th style="width: 15%">ip地址：</th>
				<td style="width: 35%"><input type="text" id="netSecurityDevice_ip" name="netSecurityDevice.ip" class="inpy"
					value='${netSecurityDevice.ip }' /></td>
				<th style="width: 15%">资源名称：</th>
				<td style="width: 35%"><input type="text" id="netSecurityDevice_name" name="netSecurityDevice.name"
					class="inpy" value='${netSecurityDevice.name }' /></td>
			</tr>
			<tr>
				<th style="width: 15%">端口号：</th>
				<td style="width: 35%"><input type="text" id="netSecurityDevice_port" name="netSecurityDevice.port"
					class="inpy" value='${netSecurityDevice.port }' /></td>
				<th style="width: 15%">账号状态：</th>
				<td style="width: 35%"><select class="inpy" name="netSecurityDevice.state.id" id="netSecurityDevice_state_id">
						<option value="">请选择...</option>
						<c:forEach var="obj" items="${stateList}">
							<c:choose>
								<c:when test="${obj.id == netSecurityDevice.state.id}">
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
			</a> <a id="deleteBtn" href="${basePath}/uac_authorization_system_netSecurityDeviceAction!delete.action">
				<p class="sBtn">删除</p>
			</a> <a id="updateBtn" href="${basePath}/uac_authorization_system_netSecurityDeviceAction!update_page.action">
				<p class="sBtn">修改</p>
			</a> <a id="createBtn" href="${basePath}/uac_authorization_system_netSecurityDeviceAction!create_page.action">
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
				<th>账号状态</th>
			</tr>
			<c:forEach var="netSecurityDevice" items="${page.dataList }" varStatus="status">
				<tr>
					<td align="center"><input name="netSecurityDeviceList.id" id="netSecurityDevice_id_${status.index + 1 }"
						type="checkbox" value="${netSecurityDevice.id }"></td>
					<td align="center">${netSecurityDevice.app.name }</td>
					<td align="center">${netSecurityDevice.name }</td>
					<td align="center">${netSecurityDevice.ip }</td>
					<td align="center">${netSecurityDevice.port }</td>
					<td align="center">${netSecurityDevice.state.text }</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		<c:set var="pageForm" value="netSecurityDeviceForm" />
		<%@include file="/uac/uac_page_bar.jsp"%>
	</form>
</body>
</html>