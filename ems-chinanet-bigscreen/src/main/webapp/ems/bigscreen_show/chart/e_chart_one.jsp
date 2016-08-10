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
		<title>e-chart-one</title>
		<%@include file="/ems/bigscreen_show/bigscreen_show_head.jsp"%>
	</head>
	<body>
		<div class="Echart" >
			<div id="main" style="width: 100%;height: 500px;"></div>
		</div>
	</body>
	<script type="text/javascript" src="${basePath}/ems/bigscreen_show/chart/js/e_chart_one.js"></script>
</html>