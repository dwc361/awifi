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
		<title>Watch统计图</title>
		<%@include file="/ems/bigscreen_show/bigscreen_show_head.jsp"%>
		<script type="text/javascript" src="${basePath}/ems/bigscreen_show/chart/js/watch_chart.js"></script>
		
		<style>
			div#div1 {
				position: fixed;
				z-index: 1000;
				width:250px; 
				height:250px;
				margin:0 0 0 0;
			}
			
			div#div1>img {
				border: 0;
				width: 100%;
				height: 100%;
			}
			
			div#watch_div {
				width:250px; 
				height:250px; 
				margin:-240px auto 0;
			}
		</style>
	</head>
	<body>
		<form id="areaspline_chart_form" method="post" action="">
			<div id="div1" style="width:100%;height:100%">
				<img src="../chart/image/watch_chart.png" />
				<div id="watch_div"></div>
			</div>
		</form>
	</body>
</html>