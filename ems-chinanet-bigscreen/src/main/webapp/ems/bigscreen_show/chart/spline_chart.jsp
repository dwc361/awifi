<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
		<meta content="telephone=no,email=no" name="format-detection">
		<title>胖AP激活率统计图</title>
		<%@include file="/ems/bigscreen_show/bigscreen_show_head.jsp"%>
		<script type="text/javascript" src="${basePath}/ems/bigscreen_show/chart/js/spline_chart.js"></script>
	</head>
	<body>
		<form id="areaspline_chart_form" method="post" action="">
			<div id="spline_div" style="width:100%;height:100%"></div>
		</form>
	</body>
</html>