<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="Mosaddek">
		<meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
		<title>大屏后台管理系统</title>
		<link href="//cdn.bootcss.com/tether/1.3.6/css/tether.min.css" rel="stylesheet">
		<script src="//cdn.bootcss.com/tether/1.3.6/js/tether.min.js"></script>
		<%@include file="/ems/bigscreen_backstage/bigscreen_backstage_head.jsp"%>
	</head>
	<body>
		<div id="container"></div>
	
		<script src="${basePath}/react/build/main.js"></script>
<!-- 		<script src="http://localhost:8081/main.js"></script> -->
		
		<%--<script src="${basePath}/ems/bigscreen_backstage/main/js/react_bigscreen_backstage_main_component.js"></script>--%>
	</body>
</html>