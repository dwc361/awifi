<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/roof-web/head.jsp"%>
<script type="text/javascript" src="${basePath }/roof-web/web/user/js/staff_list.js"></script>
<script type="text/javascript" src="${basePath }/common/js/ROOF.Class.js"></script>
<script type="text/javascript" src="${basePath }/roof-web/web/js/ROOF.SelectableTable.js"></script>
<link rel="stylesheet" href="${basePath}/common/js/fort_awesome/css/font-awesome.min.css" />
<!--[if IE 7]>
  <link rel="stylesheet" href="${basePath}/common/js/fort_awesome/css/font-awesome-ie7.min.css" />
 <![endif]-->
<title></title>
</head>
<body>
	<div class="ui-table-toolbar">
		<p class="yleft padding20 gray14">
			<b>用户搜索</b>
		</p>
		<ul class="yright">
			<li><a id="resetBtn" href="#"><i class="icon-repeat icon-large"></i> 重置</a></li>
			<li><a id="searchBtn" href="#"><i class="icon-search icon-large"></i> 搜索</a></li>
		</ul>
	</div>
	<form id="mainForm" action="${basePath }/staffAction!list.action" method="post">
		<table border="0" cellpadding="0" cellspacing="1" class="ui-table" width="100%">
			<tr>
				<td class="ui-table-header2" style="text-align: center;" width="20%">姓名:</td>
				<td><input type="text" name="staff.name" style="width: 300px;" value="${staff.name }" /></td>
				<td class="ui-table-header2" style="text-align: center;" width="20%">用户名:</td>
				<td><input type="text" name="staff.username" style="width: 300px;" value="${staff.username }" />
				</td>
			</tr>
		</table>
	<div class="ui-table-toolbar">
		<p class="yleft padding20 gray14">
			<b>用户列表</b>
		</p>
		<ul class="yright">
			<li><a href="staffAction!create_page.action?currentPage=${page.currentPage }"><i class="icon-plus icon-large"></i>新增</a></li>
			<li><a id="updateBtn" href="staffAction!update_page.action?currentPage=${page.currentPage }"><i class="icon-edit icon-large"></i> 修改</a></li>
			<li><a id="deleteBtn" href="staffAction!delete.ajax"><i class="icon-trash icon-large"></i> 删除</a></li>
		</ul>
	</div>
	<table border="0" cellpadding="0" cellspacing="1" class="ui-table" width="100%">
		<tr>
			<td class="ui-table-header2" style="text-align: center;" width="10%"></td>
			<td class="ui-table-header2" style="text-align: center;" width="10%">序号</td>
			<td class="ui-table-header2" style="text-align: center;" width="20%">姓名</td>
			<td class="ui-table-header2" style="text-align: center;" width="20%">用户名</td>
			<td class="ui-table-header2" style="text-align: center;" width="20%">部门</td>
			<td class="ui-table-header2" style="text-align: center;" width="20%">创建日期</td>
		</tr>
		<c:forEach var="staff" items="${page.dataList }" varStatus="status">
			<tr>
				<td><input type="checkbox" name="staffs[${status.index }].id" value="${staff.id }" /></td>
				<td style="text-align: center;">${status.index + 1 }</td>
				<td style="text-align: center;">${staff.name }</td>
				<td style="text-align: center;">${staff.username }</td>
				<td style="text-align: center;">${staff.org.org_name }</td>
				<td style="text-align: center;"><fmt:formatDate value="${staff.create_date }" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@include file="/roof-web/page_bar.jsp" %>
	</form>
</body>
</html>