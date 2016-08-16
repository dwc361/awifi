<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test handlebars</title>
<script type="text/javascript" src="${basePath}/common/js/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${basePath}/ems/handlebars-v4.0.5.js"></script>
<%-- <script type="text/javascript" src="${basePath}/common/js/jquery/jquery-1.7.2.min.js"></script> --%>
<%-- <script type="text/javascript" src="${basePath}/ems/bigscreen_show/chart/js/highcharts.js"></script> --%>
<%-- <script type="text/javascript" src="${basePath}/ems/bigscreen_show/chart/js/highcharts-more.js"></script> --%>
<%-- <script type="text/javascript" src="${basePath}/ems/handlebars.js"></script> --%>
<script type="text/javascript" src="${basePath}/ems-dest/scripts/templates.js"></script>


<%-- <%@include file="/ems/report01.jsp"%> --%>

<script type="text/javascript">
$(function(){
	var users = [{name:'sbt',age:'12',addr:"hz"},{name:"23",age:456,addr:"nb"}];
	var cars = [{brand:"bmw"},{brand:"benz"}];

	$("#user").html(Handlebars.templates["c1"](users));
	$("#car").html(Handlebars.templates["c2"](cars));
	
});

</script>


</head>
<body>
<!-- 	<div id="part1" style="width: 600px; height: 600px; background: blue;"> -->
		
<!-- 	</div> -->
	<div id="user"></div>
	<div id="car"></div>
</body>
</html>