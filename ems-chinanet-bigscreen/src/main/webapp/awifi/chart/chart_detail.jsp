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
<title>修改e_chart信息</title>

<%@include file="/ems_common/ems_head_boot.jsp"%>
<script type="text/javascript" src="${basePath}/awifi/chart/chart_update.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		ROOF.Utils.readonlyForm($("#bigscreen_chart_form"));
	});
</script>
</head>
<body>
	<div class="container layout-wraper" style="width: 912px; position: absolute; top: 10px; left: 10px;">
		<form id="bigscreen_chart_form" action="" method="post">
			<div class="row">
				<div class="col-xs-12 layout-header">
					<button id="bigscreen_chart_detail_close_btn" class="btn btn-default">取消</button>
				</div>
			</div>
			<%@include file="chart_form.jsp"%>
		</form>
	</div>
</body>
</html>