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
		<div class="toushi"><img src="../index/img/floor.png" alt="" /></div>

		<div class="container">
			<div class="col-md-3 col-lg-3 col-sm-3">
				<div class="left col-md-12 col-lg-12 col-sm-12">
					<div class="topH">
						<h1>[ 设备状态统计 ]</h1></div>
					<div id="areaspline_chart" class="Hchart">
<%-- 						<iframe src="${basePath}/ems/bigscreen_show/chartShowAction/areaspline_chart.action" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe> --%>
						<%@include file="/ems/bigscreen_show/chart/areaspline_chart_div.jsp"%>
					</div>
				</div>
				<div class="left col-md-12 col-lg-12 col-sm-12">
					<div class="topH">
						<h1 style="	position: absolute;top: 0;">[ 设备类型分布 ]</h1></div>
					<div id="device_chart" class="Hchart">
						<%@include file="/ems/bigscreen_show/chart/device_char_div.jsp"%>
					</div>
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
							<span class="stnFont-mid">PM- </span>
							<i class="blueFont">1232434</i>
						</div>
						<div class="col-md-4 col-lg-4 col-sm-4" style="padding-right: 12px; padding-left: 12px;line-height: 3rem;text-align: center;">
							<section class="stnFont-right col-md-5 col-lg-5 col-sm-5">PV- <i class="blueFont-right">125658</i></section>
							<section class="stnHr col-md-1 col-lg-1 col-sm-1" style="width: 6%;">
								<img src="../index/img/xiaojiange.png" alt="" />
							</section>
							<section class="stnFont-right col-md-5 col-lg-5 col-sm-5">UV- <i class="blueFont-right">125658</i></section>
						</div>
					</header>
					<div class="content">
						<div class="floater topLeft"></div>
						<div class="floater topRight"></div>
						<div id="watch_chart" class="floater bottomLeft">
<%-- 							<iframe src="${basePath}/ems/bigscreen_show/chartShowAction/watch_chart.action" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" ></iframe> --%>
							<%@include file="/ems/bigscreen_show/chart/watch_chart_div.jsp"%>
						</div>
						<div class="floater bottomRight"></div>

						<div id="map"></div>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-lg-3 col-sm-3">
				<div class="right col-md-12 col-lg-12 col-sm-12">
					<div id="spline_chart" class="topH_right">
						<h1>[ 胖ap激活率统计 ]</h1></div>
					<div id="spline_chart" class="Hchart">
<%-- 						<iframe src="${basePath}/ems/bigscreen_show/chartShowAction/spline_chart.action" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" ></iframe> --%>
						<%@include file="/ems/bigscreen_show/chart/many_spline_chart_div.jsp"%>
					</div>
				</div>
				<div class="right col-md-12 col-lg-12 col-sm-12">
					<div class="topH_right">
						<h1>[ 爱wifi热点类型排名 ]</h1></div>
					<div class="Hchart"></div>
				</div>
				<div class="right col-md-12 col-lg-12 col-sm-12">
					<div class="topH_right">
						<h1>[ 爱wifi热点类型分布 ]</h1></div>
					<div id="awifi_chart" class="Hchart">
						<%@include file="/ems/bigscreen_show/chart/awifi_char_div.jsp"%>
					</div>
				</div>
			</div>
			
			 <footer class="news">
			 	<span>［2015-09-23 06:41:28］互联网作为20世纪最伟大的发明之一，把世界变成了“地球村”。但是，这块“新疆域”</span>
			 </footer>
		</div>
	</body>
</html>