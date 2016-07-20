<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="common/js/jquery/jquery-1.7.2.js"></script>
<title>很抱歉，找不到目录或文件！</title>
<link href="<%=basePath%>/common/css/404.css" rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript">
	$(document).ready(function() {
		$("img").click(function() {
			logoutUac();
		});
	});
	function logoutUac() {
		$.ajax({
			type : "POST",
			url : "systemAction!j_spring_security_logout",
			dataType : "json",
			data : {},
			error : function(msg) {
				//alert("Ajax调用失败");
				window.location.href = "logout";
			},
			success : function(result) {
				//alert("注销成功");
			}
		});
	}
</script>
<body>
	<div class="wrapper">
		<div class="content">
			<img src="<%=basePath%>/common/images/404.jpg" border="0" usemap="#Map" />
			<map name="Map" id="Map">
				<area shape="rect" coords="182,480,254,501" href="javascript:void(0)" />
				<area shape="rect" coords="439,434,552,551" href="javascript:void(0)" />
			</map>
		</div>
	</div>
</body>
</html>
