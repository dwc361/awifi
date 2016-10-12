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
<title>修改e_theme信息</title>

<%@include file="/ems_common/ems_head_boot.jsp"%>
<script type="text/javascript" src="${basePath}/awifi/theme/theme_update.js"></script>
</head>
<body>
	<div class="container layout-wraper" style="width: 100%;">
		<form id="bigscreen_theme_form" action="${basePath}/awifi/themeAction/update.json" method="post">
			<div class="row">
				<div class="col-xs-12 layout-header">
					<button type="submit" class="btn btn-default">提交</button>
					<button id="bigscreen_theme_update_close_btn" class="btn btn-default">取消</button>
				</div>
			</div>
			<%@include file="theme_form.jsp"%>
		</form>
	</div>
</body>
</html>