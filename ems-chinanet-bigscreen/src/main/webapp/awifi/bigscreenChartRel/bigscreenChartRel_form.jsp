<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<input type="hidden" id="bigscreenChartRel_id" name="id" value="${bigscreenChartRel.id }" />

<div class="row text-center form-wrapper">
	<div class="row">
		<div class="col-xs-2">
			<b>chart_id</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="bigscreenChartRel_chart_id" name="chart_id" value="${bigscreenChartRel.chart_id }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>screen_id</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="bigscreenChartRel_screen_id" name="screen_id" value="${bigscreenChartRel.screen_id }" class="form-control input-sm">
		</div>
		<div class="col-xs-2">
			<b>target</b><span class='red'>*</span> :
		</div>
		<div class="col-xs-2">
			<input type="text" id="bigscreenChartRel_target" maxlength="255" name="target" value="${bigscreenChartRel.target }" class="form-control input-sm">
		</div>
	</div>
</div>