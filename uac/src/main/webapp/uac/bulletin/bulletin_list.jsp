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
	$(document).ready(function() {
		$("#resetBtn").click(function() {
			ROOF.Utils.emptyForm($("#bulletinForm"));
		});
		
		$("#searchBtn").click(function() {
			$("#bulletinForm").submit();
		});
		
		$("#createBtn").click(function() {
			$("#bulletinForm").attr("action",$(this).attr('href'));
			$("#bulletinForm").submit();
		});

		var table = new ROOF.SelectableTable($('#mainTable'));
		$('#updateBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#bulletinForm").attr("action",$(this).attr('href') + '?bulletin.id=' + id);
				$("#bulletinForm").submit();
			} else {
				alert('请选择一行记录!');
			}
			return false;
		});
		$('#detailBtn').click(function() {
			var id = table.getSeleted();
			if (id) {
				$("#bulletinForm").attr("action",$(this).attr('href') + '?bulletin.id=' + id);
				$("#bulletinForm").submit();
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
						'bulletin.id' : id
					}, function(d) {
						alert(d.message);
						ROOF.Utils.hideBlock();
						$("#bulletinForm").submit();
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
<form id="bulletinForm" action="${basePath}/uac_bulletinAction!list${isRead }.action" method="post">
<input type="hidden" id="isRead" name="isRead" value="${isRead }" />
	<div class="bread">
		您的位置：
		<a href="javascript:void(0)" id="parNode">公告管理</a> &gt; 
		<a href="javascript:void(0)" id="currNode">公告管理列表</a>
	</div>
	<div class="main">
			<table border="0" cellpadding="1" cellspacing="1" class="ctable" style="margin: 0px auto;" width="100%">
			<tr>
			<th style="width: 15%">内容：</th>
			<td style="width: 35%">
				 <input type="text" id="bulletin_content" name="bulletin.content" class="inpy" value='${bulletin.content }'/>
			</td>
			<th style="width: 15%">标题：</th>
			<td style="width: 35%">
				 <input type="text" id="bulletin_title" name="bulletin.title" class="inpy" value='${bulletin.title }'/>
			</td>
			</table>
		<div class="btnBox">
			<a id="resetBtn" href="javascript:void(0)">
				<p class="sBtn">重置</p>
			</a> 
			<a id="searchBtn" href="javascript:void(0)">
				<p class="sBtn">查询</p>
			</a>
			<a id="detailBtn" href="${basePath}/uac_bulletinAction!detail_page.action">
				<p class="sBtn">查看</p>
			</a>
			<c:if test="${empty isRead}">
			<a id="deleteBtn" href="${basePath}/uac_bulletinAction!delete.action">
				<p class="sBtn">删除</p>
			</a>
			<a id="updateBtn" href="${basePath}/uac_bulletinAction!update_page.action">
				<p class="sBtn">修改</p>
			</a>
			<a id="createBtn" href="${basePath}/uac_bulletinAction!create_page.action">
				<p class="sBtn">新增</p>
			</a>
			</c:if>
		</div>
		<table id="mainTable" border="0" cellpadding="1" cellspacing="1" class="ytable" width="100%">
			<tr>
				<th>
					&nbsp;
				</th>
				<th>
					标题
				</th>
				<th>
					浏览量
				</th>
				<th>
					发布时间
				</th>
			</tr>
			<c:forEach var="bulletin" items="${page.dataList }" varStatus="status">
				<tr>
					<td align="center">
					<input name="bulletinList.id" id="bulletin_id_${status.index + 1 }" type="checkbox" value="${bulletin.id }">
					</td>
					<td align="center">
						${bulletin.title }
					</td>
					<td align="center">
						${bulletin.visitors }
					</td>
					<td align="center">
						<fmt:formatDate value="${bulletin.create_date }" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<c:set var="pageForm" value="bulletinForm" />
	<%@include file="/uac/uac_page_bar.jsp"%>
</form>
</body>
</html>