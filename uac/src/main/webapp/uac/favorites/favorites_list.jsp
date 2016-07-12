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
<script src="${basePath}/uac/home.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#favoritesForm"));
		});

		$("#searchBtn").click(function() {
			$("#favoritesForm").submit();
		});

		$("#createBtn").click(function() {
			$("#favoritesForm").attr("action", $(this).attr('href'));
			$("#favoritesForm").submit();
		});

		var table = new ROOF.SelectableTable($('#mainTable'));
		$('#updateBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#favoritesForm").attr("action", $(this).attr('href') + '?favorites.id=' + id);
				$("#favoritesForm").submit();
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
						'favorites.id' : id
					}, function(d) {
						alert(d.message);
						ROOF.Utils.hideBlock();
						$("#favoritesForm").submit();
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
	<form id="favoritesForm" action="${basePath}/uac_favoritesAction!list.action" method="post">
		<div>
			<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
				<tr>
					<th style="width: 15%">系统名称：</th>
					<td style="width: 35%"><input type="text" id="favorites_app_name" name="favorites.app.name" class="inpy"
						value='${favorites.app.name }' /></td>
				<tr>
			</table>
			<div class="btnBox">
				<a id="resetBtn" href="javascript:void(0)">
					<p class="sBtn">重置</p>
				</a> <a id="searchBtn" href="javascript:void(0)">
					<p class="sBtn">查询</p>
				</a> <a id="deleteBtn" href="${basePath}/uac_favoritesAction!delete.action">
					<p class="sBtn">删除</p>
				</a>
				<%-- <a id="updateBtn" href="${basePath}/uac_favoritesAction!update_page.action">
				<p class="sBtn">修改</p>
			</a>
			<a id="createBtn" href="${basePath}/uac_favoritesAction!create_page.action">
				<p class="sBtn">新增</p>
			</a> --%>
			</div>
			<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
				<tr>
					<th>&nbsp;</th>
					<th>系统名称</th>
					<th width="30%">子账号</th>
					<th width="20%">操作</th>
				</tr>
				<c:forEach var="favorites" items="${page.dataList }" varStatus="status">
					<tr>
						<td align="center"><input name="favoritesList.id" id="favorites_id_${status.index + 1 }" type="checkbox"
							value="${favorites.id }"></td>
						<td align="center">${favorites.app.name }</td>
						<td align="center">${favorites.subUser.username }</td>
						<td align="center">
						<a href="javascript:void(0)" onclick="loginFavorites(this)" title="${favorites.app.path }"
												rel="${favorites.app.needPassword }" lang="${favorites.subUser.username }">登录</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="floatBox" id="fillPassword" style="display: none; width: 200px;">
			<div class="title">
				<p class="txt">请输入密码</p>
				<p class="colse" onclick="$.unblockUI();"></p>
			</div>
			<div class="floatCon">
				<input type="password" id="inputPassword" value="" /> <input type="hidden" id="sub_href" value="" /> <input
					type="hidden" id="subUser_name" value="" />
				<p class="floatBtn" onclick="loginByPwd()">确定</p>
			</div>
		</div>
		<c:set var="pageForm" value="favoritesForm" />
		<%@include file="/uac/uac_page_bar.jsp"%>
	</form>
</body>
</html>