<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>e_theme管理</title>

<%@include file="/ems_common/ems_head_boot.jsp"%>
<script type="text/javascript" src="${basePath}/awifi/theme/theme_list.js"></script>
</head>
<body>
	<!-- 增删改成模块 start -->
	<div class="panel panel-default" style="background-color: #f8f8f8; overflow: hidden;">
		<div class="panel-heading">e_theme列表</div>
		<div class="panel-body">
			<form id="bigscreen_theme_search_form" class="form-inline" method="post" action="${basePath}/awifi/themeAction/list.action">
				<%@include file="/ems_common/ems_page_bar_cond_default.jsp"%>
				<div class="form-group">
					<label class="" for="exampleInputName2">主键:</label> 
					<input id="theme_id" name="id" type="text" class="form-control input-sm"
					value="${theme.id }"  >
				</div>
				<div class="form-group">
					<label class="" for="exampleInputName2">名称:</label> 
					<input id="theme_name" maxlength="255" name="name" type="text" class="form-control input-sm"
					value="${theme.name }"  >
				</div>
				<div class="form-group">
					<label class="" for="exampleInputName2">背景图片:</label> 
					<input id="theme_picture" maxlength="255" name="picture" type="text" class="form-control input-sm"
					value="${theme.picture }"  >
				</div>
				<div class="form-group">
					<label class="" for="exampleInputName2">是否可用:</label> 
					<input id="theme_enabled" maxlength="1" name="enabled" type="text" class="form-control input-sm"
					value="${theme.enabled }"  >
				</div>
				<div class="form-group">
					<label class="" for="exampleInputName2">备注:</label> 
					<input id="theme_remark" maxlength="255" name="remark" type="text" class="form-control input-sm"
					value="${theme.remark }"  >
				</div>
				<div class="form-group">
					<label class="" for="exampleInputName2">新建时间:</label> 
					<input id="theme_create_time" name="create_time" type="text" class="form-control input-sm"
					readonly value="<fmt:formatDate value="${theme.create_time }" pattern="yyyy-MM-dd" />"
					 >
				</div>
				<div class="form-group">
					<label class="" for="exampleInputName2">所属用户:</label> 
					<input id="theme_create_by" maxlength="255" name="create_by" type="text" class="form-control input-sm"
					value="${theme.create_by }"  >
				</div>
				<div class="form-group">
					<label class="" for="exampleInputName2">更新时间:</label> 
					<input id="theme_update_time" name="update_time" type="text" class="form-control input-sm"
					readonly value="<fmt:formatDate value="${theme.update_time }" pattern="yyyy-MM-dd" />"
					 >
				</div>
				<div class="form-group">
					<label class="" for="exampleInputName2">更新用户:</label> 
					<input id="theme_update_by" maxlength="255" name="update_by" type="text" class="form-control input-sm"
					value="${theme.update_by }"  >
				</div>
				<button id="serchBtn" type="button" class="btn btn-default">查询</button>
				<button id="reset" type="button" class="btn btn-default">重置</button>
			</form>
		</div>
		<div style="margin: 0 15px; background-color: white;">
			<div class="oper-bar">
				<button id="bigscreen_theme_create_btn" type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				</button>
				<button id="bigscreen_theme_delete_btn" type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
				</button>
				<button id="bigscreen_theme_update_btn" type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑
				</button>
			</div>
			<!-- Table -->
			<table id="bigscreen_theme_table" class="table table-bordered table-hover" style="margin-bottom: 0;">
				<thead>
					<tr class='active'>
						<th>选择</th> 
						<th>名称</th> 
						<th>背景图片</th> 
						<th>是否可用</th> 
						<th>备注</th> 
						<th>新建时间</th> 
						<th>所属用户</th> 
						<th>更新时间</th> 
						<th>更新用户</th> 
					</tr>
				</thead>
				<tbody>
					<c:forEach var="theme" items="${page.dataList }" varStatus="status">
						<tr>
							<td><input type="checkbox" name="id" class="ck" value="${theme.id }"> </td> 
							<td>${theme.name}</td> 
							<td>${theme.picture}</td> 
							<td>${theme.enabled}</td> 
							<td>${theme.remark}</td> 
							<td><fmt:formatDate value="${theme.create_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
							<td>${theme.create_by}</td> 
							<td><fmt:formatDate value="${theme.update_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
							<td>${theme.update_by}</td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<%@include file="/ems_common/ems_page_bar_boot.jsp"%>
	</div>
	<!-- 增删改成模块 end -->
</body>
</html>