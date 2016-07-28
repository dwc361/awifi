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
		<title>大屏监控网</title>
		<%@include file="/ems/bigscreen_show/bigscreen_show_head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${basePath}/ems/bigscreen_show/index/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="${basePath}/ems/bigscreen_show/index/css/screen.css" />
		<script src="${basePath}/ems/bigscreen_show/index/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${basePath}/ems/bigscreen_show/index/js/screen.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(function () {
				//$("#areaspline_chart").load(ROOF.Utils.projectName() + "/ems/bigscreen_show/chartShowAction/areaspline_chart.action");
				//$("#spline_chart").load(ROOF.Utils.projectName() + "/ems/bigscreen_show/chartShowAction/spline_chart.action");
				//$("#watch_chart").load(ROOF.Utils.projectName() + "/ems/bigscreen_show/chartShowAction/watch_chart.action");
			});
		</script>
	</head>

	<body scrolling="no">
		
		<!--<iframe src="../index/img/floor.svg" width="100%"  height="24%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>-->		
		<svg class="bgImg"></svg>
		<div class="container">
			<div class="col-md-3 col-lg-3 col-sm-3" style="padding:2rem;">
				<div id="areaspline_chart" class="left col-md-12 col-lg-12 col-sm-12" >
<%-- 					<iframe src="${basePath}/ems/bigscreen_show/chartShowAction/areaspline_chart.action" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe> --%>
					<%@include file="/ems/bigscreen_show/chart/areaspline_chart_div.jsp"%>
				</div>
				<div class="left col-md-12 col-lg-12 col-sm-12" >
					
				</div>
			</div>
			<div class="col-md-6 col-lg-6 col-sm-6">
				<div class="media-middle col-md-12 col-lg-12 col-sm-12">
					<header>
						<div class="col-md-4 col-lg-4 col-sm-4">
							<section class="stnFont col-md-6 col-lg-6 col-sm-6">3500080</section>
							<section class="stnHr col-md-1 col-lg-1 col-sm-1"><img src="../index/img/xiaojiange.png" alt="" /></section>
							<section class="stnP col-md-4 col-lg-4 col-sm-4">
								<span class="stnP-span">用户总数</span><br />
								<i class="stnP-i">2016.02.14</i>
							</section>
						</div>
						<div class="col-md-4 col-lg-4 col-sm-4" style="text-align: center;">
							<span class="stnFont-mid" >PM- </span>
							<i class="blueFont">1232434</i>
						</div>
						<div class="col-md-4 col-lg-4 col-sm-4" style="padding-right: 12px;
    padding-left: 12px;line-height: 3rem;text-align: center;">
							<section class="stnFont-right col-md-5 col-lg-5 col-sm-5">PV- <i class="blueFont-right">125658</i></section>
							<section class="stnHr col-md-1 col-lg-1 col-sm-1" style="width: 6%;">
								<img src="../index/img/xiaojiange.png" alt="" />
							</section>
							<section class="stnFont-right col-md-5 col-lg-5 col-sm-5">UV- <i class="blueFont-right">125658</i></section>
						</div>
					</header>
					<div class="content">
							<div class="floater topLeft">1</div>
							<div class="floater topRight"></div>
							<div id="watch_chart" class="floater bottomLeft">
<%-- 								<iframe src="${basePath}/ems/bigscreen_show/chartShowAction/watch_chart.action" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" ></iframe> --%>
								<%@include file="/ems/bigscreen_show/chart/watch_chart_div.jsp"%>
							</div>
							<div class="floater bottomRight"></div>
						<div id="map"></div>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-lg-3 col-sm-3">
				<div id="spline_chart" class="right col-md-12 col-lg-12 col-sm-12" style="padding:2rem;">
<%-- 					<iframe src="${basePath}/ems/bigscreen_show/chartShowAction/spline_chart.action" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" ></iframe> --%>
					<%@include file="/ems/bigscreen_show/chart/spline_chart_div.jsp"%>
				</div>
				<div class="right col-md-12 col-lg-12 col-sm-12" >6</div>
				<div class="right col-md-12 col-lg-12 col-sm-12" >7</div>
			</div>
			<img src="../index/img/floor.svg" alt="" />
		</div>
	</body>
</html>