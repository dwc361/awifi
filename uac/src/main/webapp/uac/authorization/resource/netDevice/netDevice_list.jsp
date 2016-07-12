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
			ROOF.Utils.emptyForm($("#netDeviceForm"));
		});

		$("#searchBtn").click(function() {
			$("#netDeviceForm").submit();
		});

		$("#createBtn").click(function() {
			$("#netDeviceForm").attr("action", $(this).attr('href'));
			$("#netDeviceForm").submit();
		});

		table = new ROOF.SelectableTable($('#mainTable'));
		$('#updateBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#netDeviceForm").attr("action", $(this).attr('href') + '?netDevice.id=' + id);
				$("#netDeviceForm").submit();
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
						'netDevice.id' : id
					}, function(d) {
						alert(d.message);
						ROOF.Utils.hideBlock();
						$("#netDeviceForm").submit();
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
	<form id="netDeviceForm" action="${basePath}/uac_authorization_system_netDeviceAction!list.action" method="post">
		<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
				<th style="width: 15%">所属应用：</th>
				<td style="width: 35%"><input type="text" id="netDevice_app_name" name="netDevice.app.name" class="inpy"
					value='${netDevice.app.name }' /></td>
				<th style="width: 15%">描述：</th>
				<td style="width: 35%"><input type="text" id="netDevice_describe" name="netDevice.describe" class="inpy"
					value='${netDevice.describe }' /></td>
			</tr>
			<tr>
				<th style="width: 15%">ip地址：</th>
				<td style="width: 35%"><input type="text" id="netDevice_ip" name="netDevice.ip" class="inpy"
					value='${netDevice.ip }' /></td>
				<th style="width: 15%">资源名称：</th>
				<td style="width: 35%"><input type="text" id="netDevice_name" name="netDevice.name" class="inpy"
					value='${netDevice.name }' /></td>
			</tr>
			<tr>
				<th style="width: 15%">端口号：</th>
				<td style="width: 35%"><input type="text" id="netDevice_port" name="netDevice.port" class="inpy"
					value='${netDevice.port }' /></td>
				<th style="width: 15%">账号状态：</th>
				<td style="width: 35%"><select class="inpy" name="netDevice.state.id" id="netDevice_state_id">
						<option value="">请选择...</option>
						<c:forEach var="obj" items="${stateList}">
							<c:choose>
								<c:when test="${obj.id == netDevice.state.id}">
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
			</a> <a id="deleteBtn" href="${basePath}/uac_authorization_system_netDeviceAction!delete.action">
				<p class="sBtn">删除</p>
			</a> <a id="updateBtn" href="${basePath}/uac_authorization_system_netDeviceAction!update_page.action">
				<p class="sBtn">修改</p>
			</a> <a id="createBtn" href="${basePath}/uac_authorization_system_netDeviceAction!create_page.action">
				<p class="sBtn">新增</p>
			</a>
		</div>
		<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
			<tr>
				<th>&nbsp;</th>
				<th>所属应用</th>
				<th>资源名称</th>
				<th>ip地址</th>
				<th>服务名称</th>
				<th>服务类型</th>
				<th>端口号</th>
				<th>账号状态</th>
			</tr>
			<c:forEach var="netDevice" items="${page.dataList }" varStatus="status">
				<tr>
					<td align="center"><input name="netDeviceList.id" id="netDevice_id_${status.index + 1 }" type="checkbox"
						value="${netDevice.id }"></td>
					<td align="center">${netDevice.app.name }</td>
					<td align="center">${netDevice.name }</td>
					<td align="center">${netDevice.ip }</td>
					<td align="center">${netDevice.serve_name }</td>
					<td align="center">${netDevice.serve_type.text }</td>
					<td align="center">${netDevice.port }</td>
					<td align="center">${netDevice.state.text }</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		<c:set var="pageForm" value="netDeviceForm" />
		<%@include file="/uac/uac_page_bar.jsp"%>
	</form>
</body>
</html>