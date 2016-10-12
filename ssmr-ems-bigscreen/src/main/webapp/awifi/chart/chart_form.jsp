<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<input type="hidden" id="chart_id" name="id" value="${chart.id }" />

<div class="row text-center form-wrapper">
	<div class="row">
		<div class="col-xs-2">
			<b>图表名称</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="chart_name" maxlength="255" name="name" value="${chart.name }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>是否可用</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<select id=chart_enabled name="enabled" class="form-control input-sm">
				<option></option>
				<c:forEach  items="${enableds }" var="dic" >
					<option value="${dic.val }" 
					<c:if test="${ chart.enabled == dic.val}">
					selected="selected"</c:if>>${dic.text }</option>
				</c:forEach >
			</select>
<%-- 			<input type="text" id="chart_enabled" maxlength="1" name="enabled" value="${chart.enabled }" class="form-control input-sm"> --%>
		</div>
		<div class="col-xs-2">
			<b>刷新时间</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="chart_re_times" name="re_times" value="${chart.re_times }" class="form-control input-sm">
		</div>
<!-- 		<div class="col-xs-2"> -->
<!-- 			<b>配置json</b><span class='red'>*</span> : -->
<!-- 		</div> -->
<!-- 		<div class="col-xs-2"> -->
<%-- 			<input type="text" id="chart_configure" maxlength="255" name="configure" value="${chart.configure }" class="form-control input-sm"> --%>
<!-- 		</div> -->
	</div>
	<div class="row">
		<div class="col-xs-2">
			<b>代码路径</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-10">
			<input type="text" id="chart_path" maxlength="255" name="path" value="${chart.path }" class="form-control input-sm">
		</div>
	</div>
	<div class="row">
		<div class="col-xs-2">
			<b>图标</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-10">
			<input type="text" id="chart_icon" maxlength="255" name="icon" value="${chart.icon }" class="form-control input-sm">
		</div>
	</div>
</div>