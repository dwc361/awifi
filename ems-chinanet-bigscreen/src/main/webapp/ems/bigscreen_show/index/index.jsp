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
		<link rel="stylesheet" type="text/css" href="${basePath}/ems/bigscreen_show/index/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="${basePath}/ems/bigscreen_show/index/css/screen.css" />
	</head>

	<body scrolling="no">
		
		<!--<iframe src="img/floor.svg" width="100%"  height="24%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>-->		
		<svg class="bgImg"></svg>
		<div class="container">
			<div class="col-md-3 col-lg-3 col-sm-3">
				<div class="left col-md-12 col-lg-12 col-sm-12" ></div>
				<div class="left col-md-12 col-lg-12 col-sm-12" ></div>
			</div>
			<div class="col-md-6 col-lg-6 col-sm-6">
				<div class="media-middle col-md-12 col-lg-12 col-sm-12">
					<header>
						<div class="col-md-4 col-lg-4 col-sm-4">
							<section class="stnFont col-md-6 col-lg-6 col-sm-6">3500080</section>
							<section class="stnHr col-md-1 col-lg-1 col-sm-1"><img src="img/xiaojiange.png" alt="" /></section>
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
								<img src="img/xiaojiange.png" alt="" />
							</section>
							<section class="stnFont-right col-md-5 col-lg-5 col-sm-5">UV- <i class="blueFont-right">125658</i></section>
						</div>
					</header>
					<div class="content">
							<div class="floater topLeft"></div>
							<div class="floater topRight"></div>
							<div class="floater bottomLeft"></div>
							<div class="floater bottomRight"></div>
						
						
						<div id="map"></div>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-lg-3 col-sm-3">
				<div class="right col-md-12 col-lg-12 col-sm-12" ></div>
				<div class="right col-md-12 col-lg-12 col-sm-12" ></div>
				<div class="right col-md-12 col-lg-12 col-sm-12" ></div>
			</div>
			<img src="img/floor.svg" alt="" />
		</div>
		
		
		<!--以下是js-->
		<script src="${basePath}/ems/bigscreen_show/index/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${basePath}/ems/bigscreen_show/index/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${basePath}/ems/bigscreen_show/index/js/screen.js" type="text/javascript" charset="utf-8"></script>
	</body>
</html>