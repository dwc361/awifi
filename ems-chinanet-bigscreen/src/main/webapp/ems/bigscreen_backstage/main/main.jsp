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
<%@include file="/ems/bigscreen_backstage/bigscreen_backstage_head.jsp"%>
<!-- 字体&图标 -->
<link rel="stylesheet" href="${basePath}/ems/bigscreen_backstage/main/font-awesome-4.6.3/css/font-awesome.css">
<!-- bootstrap样式 -->
<link rel="stylesheet" href="${basePath}/ems/bigscreen_backstage/main/css/bootstrap.min.css">
<script src="${basePath}/ems/bigscreen_backstage/main/js/bootstrap.min.js"></script>
<!-- jquery-ui拖拽 -->
<script src="${basePath}/ems/bigscreen_backstage/main/js/jquery-ui"></script>
<!-- 自定义样式和js -->
<link rel="stylesheet" href="${basePath}/ems/bigscreen_backstage/main/css/index.css">
<script src="${basePath}/ems/bigscreen_backstage/main/js/main.js"></script>
</head>

<body>
	<div id="header">
		<div id="line"></div>
		<div id="head">
			<div id="logo" class="col-sm-10 col-md-10 col-lg-10">
				<img src="${basePath}/ems/bigscreen_backstage/main/image/logo.png" alt="">
			</div>
			<div id="user" class="col-sm-2 col-md-2 col-lg-2">
				<div id="innerUr">
					<div>
						<span class="fa fa-user"></span>
						<p id="admin" value="admin">Admin</p>
						<button></button>
						<div id="window">
							<a href="#">退出账号</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="content">
		<form id="bigscreen" class="form-inline" method="post" action="" target="_self">
			<input type="hidden" name="id" value="${bigscreen.id }" />
			<input type="hidden" name="theme.id" value="${theme.id }" />
			<input type="hidden" name="templates.id" value="${templates.id }" />
			<input type="hidden" name="templates.path" value="${templates.path }" />
			<div id="right">
				<div class="handle">
	               <span>
	                  <i id="save" class="fa fa-folder-open"></i>
	                  <i id="preview" class="fa fa-eye"></i>
	               </span>
	            </div>
				<div id="screen" class="col-sm-12 col-md-12 col-lg-12"></div>
			</div>
		</form>
		<div id="left">
			<div id="title">
				<h2>配置图表</h2>
			</div>
			<div id="chartCnt"></div>
		</div>
	</div>
</body>
	
<!--react封装的组件-->
<script type="text/javascript">
	var chartList = ${chartList};
	var relList = ${relList};
</script>
<script src="${basePath}/ems/bigscreen_backstage/main/js/react_bigscreen_backstage_main_component.js"></script>
</html>