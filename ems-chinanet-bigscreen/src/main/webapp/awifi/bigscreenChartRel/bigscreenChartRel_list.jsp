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
<title>e_bigscreen_chart_rel管理</title>

<%@include file="/web_common/web_head_boot.jsp"%>
<script type="text/javascript" src="${basePath}/awifi/bigscreenChartRel/bigscreenChartRel_list.js"></script>
</head>
<body>
	<!-- 增删改成模块 start -->
	<div class="panel panel-default" style="background-color: #f8f8f8; overflow: hidden;">
		<div class="panel-heading">e_bigscreen_chart_rel列表</div>
		<div class="panel-body">
			<form id="manage_bigscreenChartRel_search_form" class="form-inline" method="post" action="${basePath}/awifi/bigscreen_chart_relAction/list.action">
				<%@include file="/web_common/web_page_bar_cond_default.jsp"%>
				<div class="form-group">
					<label class="" for="exampleInputName2">id:</label> 
					<input id="bigscreenChartRel_id" name="id" type="text" class="form-control input-sm"
					value="${bigscreenChartRel.id }"  >
				</div>
				<div class="form-group">
					<label class="" for="exampleInputName2">chart_id:</label> 
					<input id="bigscreenChartRel_chart_id" name="chart_id" type="text" class="form-control input-sm"
					value="${bigscreenChartRel.chart_id }"  >
				</div>
				<div class="form-group">
					<label class="" for="exampleInputName2">screen_id:</label> 
					<input id="bigscreenChartRel_screen_id" name="screen_id" type="text" class="form-control input-sm"
					value="${bigscreenChartRel.screen_id }"  >
				</div>
				<div class="form-group">
					<label class="" for="exampleInputName2">target:</label> 
					<input id="bigscreenChartRel_target" maxlength="255" name="target" type="text" class="form-control input-sm"
					value="${bigscreenChartRel.target }"  >
				</div>
				<button id="serchBtn" type="button" class="btn btn-default">查询</button>
				<button id="reset" type="button" class="btn btn-default">重置</button>
			</form>
		</div>
		<div style="margin: 0 15px; background-color: white;">
			<div class="oper-bar">
				<button id="manage_bigscreenChartRel_create_btn" type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
				</button>
				<button id="manage_bigscreenChartRel_delete_btn" type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
				</button>
				<button id="manage_bigscreenChartRel_update_btn" type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑
				</button>
			</div>
			<!-- Table -->
			<table id="manage_bigscreenChartRel_table" class="table table-bordered table-hover" style="margin-bottom: 0;">
				<thead>
					<tr class='active'>
						<th>选择</th> 
						<th>chart_id</th> 
						<th>screen_id</th> 
						<th>target</th> 
					</tr>
				</thead>
				<tbody>
					<c:forEach var="bigscreenChartRel" items="${page.dataList }" varStatus="status">
						<tr>
							<td><input type="checkbox" name="id" class="ck" value="${bigscreenChartRel.id }"> </td> 
							<td>${bigscreenChartRel.chart_id}</td> 
							<td>${bigscreenChartRel.screen_id}</td> 
							<td>${bigscreenChartRel.target}</td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<%@include file="/web_common/web_page_bar_boot.jsp"%>
	</div>
	<!-- 增删改成模块 end -->
</body>
</html>