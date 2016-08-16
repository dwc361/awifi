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
<title>大屏管理</title>

<%@include file="/ems_common/ems_head_boot.jsp"%>
<script type="text/javascript" src="${basePath}/awifi/bigscreen/bigscreen_list.js"></script>
</head>
<body>
	<!-- 增删改成模块 start -->
	<div class="panel panel-default" style="background-color: #f8f8f8; overflow: hidden;">
		<div class="panel-heading">大屏列表</div>
		<div class="panel-body">
			<form id="bigscreen_bigscreen_search_form" class="form-inline" method="post" action="${basePath}/awifi/bigscreenAction/list.action">
				<%@include file="/ems_common/ems_page_bar_cond_default.jsp"%>
				<div class="form-group">
					<label class="" for="exampleInputName2">名称:</label> 
					<input id="bigscreen_name" maxlength="255" name="name" type="text" class="form-control input-sm"
					value="${bigscreen.name }"  >
				</div>
				<div class="form-group">
					<label class="" for="exampleInputName2">是否发布:</label> 
					<input id="bigscreen_publish" maxlength="1" name="publish" type="text" class="form-control input-sm"
					value="${bigscreen.publish }"  >
				</div>
				<button id="serchBtn" type="button" class="btn btn-default">查询</button>
				<button id="reset" type="button" class="btn btn-default">重置</button>
			</form>
		</div>
		<div style="margin: 0 15px; background-color: white;">
			<div class="oper-bar">
				<button id="bigscreen_bigscreen_create_btn" type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				</button>
				<button id="bigscreen_bigscreen_delete_btn" type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
				</button>
				<button id="bigscreen_bigscreen_update_btn" type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑
				</button>
			</div>
			<!-- Table -->
			<table id="bigscreen_bigscreen_table" class="table table-bordered table-hover" style="margin-bottom: 0;">
				<thead>
					<tr class='active'>
						<th>选择</th> 
						<th>名称</th> 
						<th>模板id</th> 
						<th>主题id</th> 
						<th>是否发布</th> 
						<th>刷新时间</th> 
						<th>re_type</th> 
						<th>新建时间</th> 
						<th>所属用户</th> 
						<th>更新时间</th> 
						<th>更新用户</th> 
					</tr>
				</thead>
				<tbody>
					<c:forEach var="bigscreen" items="${page.dataList }" varStatus="status">
						<tr>
							<td><input type="checkbox" name="id" class="ck" value="${bigscreen.id }"> </td> 
							<td>${bigscreen.name}</td> 
							<td>${bigscreen.template_id}</td> 
							<td>${bigscreen.theme_id}</td> 
							<td>${bigscreen.publish}</td> 
							<td>${bigscreen.re_time}</td> 
							<td>${bigscreen.re_type}</td> 
							<td><fmt:formatDate value="${bigscreen.create_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
							<td>${bigscreen.create_by}</td> 
							<td><fmt:formatDate value="${bigscreen.update_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
							<td>${bigscreen.update_by}</td> 
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