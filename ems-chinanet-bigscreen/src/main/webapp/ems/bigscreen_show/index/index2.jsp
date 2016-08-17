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
		<link rel="stylesheet" type="text/css" href="${basePath}/ems/bigscreen_show/index/css/screen_2.css" />
		<script src="${basePath}/ems/bigscreen_show/index/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${basePath}/ems/bigscreen_show/index/js/screen.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body scrolling="no">
		<!--以下是新闻-->
		<footer class="news">
			<span>［2015-09-23 06:41:28］互联网作为20世纪最伟大的发明之一，把世界变成了“地球村”。但是，这块“新疆域”</span>
		</footer>
		<!--以下是左侧4栏-->
		<div class="leftPart">
			<div class="left col-md-12 col-lg-12 col-sm-12">
				<div class="topH">
					<h1>[ 平台用户总量 ]</h1></div>
				<div class="Hchart" style="position: relative;">
					<!--<script type="text/javascript" src="${basePath}/ems/bigscreen_show/chart/js/areaspline_chart.js"></script>-->

					<div id="areaspline_div"></div>
					<div id="textname">
						<ul style="font-size: 1.4rem;">
							<li> <i style="background: #367bdb;"></i>用户数量：<i id="areaspline1">343242424</i> <span></span></li>
							</ul>
					</div>
				</div>
			</div>

			<div class="left col-md-12 col-lg-12 col-sm-12">
				<div class="topH">
					<h1 style="	position: absolute;top: 0;">[ 定制终端设备状态统计 ]</h1></div>
				<div class="Echart" ui-view="chart_2">
					<div id="state_1" class="state"></div>
				</div>
			</div>
			<div class="left col-md-12 col-lg-12 col-sm-12">
				<div class="topH">
					<h1 style="	position: absolute;top: 0;">[ 设备类型分布 ]</h1></div>
				<div class="Echart">
					<div id='spread'></div>
				</div>
			</div>
			<div class="left col-md-12 col-lg-12 col-sm-12">
				<div class="topH">
					<h1 style="	position: absolute;top: 0;">[ 全省设备排名 ]</h1></div>
				<div class="Echart" ui-view="chart_2">
					<!--<div id="funnel_div"></div>-->
					<div id="rank" ></div>
				</div>
			</div>
		</div>

		<!--以下是中心1栏-->
		<div class="centerPart">
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
					<span class="stnFont-mid">全国商户总数— </span>
					<i class="blueFont">1232434</i>
				</div>
				<div class="col-md-4 col-lg-4 col-sm-4" style="padding-right: 12px;padding-left: 12px;line-height: 3rem;text-align: center;">
					<section class="stnFont-right col-md-5 col-lg-5 col-sm-5">PV- <i class="blueFont-right">125658</i></section>
					<section class="stnHr col-md-1 col-lg-1 col-sm-1" style="width: 6%;">
						<img src="img/xiaojiange.png" alt="" />
					</section>
					<section class="stnFont-right col-md-5 col-lg-5 col-sm-5">UV- <i class="blueFont-right">125658</i></section>
				</div>
			</header>
			<div class="content">
				<div class="floater bottomLeft" ui-view="chart_5">
					<div id="dashboard"></div>
				</div>

				<div id="map">
					<div class="items" id="Item1">
						<a href="javascript:;" class="fold"></a>
						<div class="itemCon mymap">
							<div class="map-bg" id="ChinaMapBg"></div>
							<div class="map-grand"></div>
							<div class="map-d" id="ChinaMap1"></div>
							<div class="map-d" id="ChinaMap2"></div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!--以下是右侧4栏-->
		<div class="rightPart">
			<div class="right col-md-12 col-lg-12 col-sm-12">
				<div class="topH">
					<h1>[ 用户认证状态 ]</h1></div>
				<div class="Echart">
					<!--<div id="textname" style="top: 4.8rem;">
						<ul style="font-size: 1.4rem;">
							<li> <i style="background: #367bdb;"></i>胖AP：<i id="areaspline1">343242424</i> <span></span></li>
							<li><i style="background: #00d9bc;"></i>光猫：<i id="areaspline2">54654645654</i><span></span></li>
							<li><i style="background: #ff5f5f;"></i>三合一融合备份：<i id="areaspline3">3242435</i><span></span></li>
						</ul>
					</div>-->
					<!--<div id="many_spline_div"></div>-->
					<div id="zhongduan"></div>
				</div>

			</div>
			<div class="right col-md-12 col-lg-12 col-sm-12">
				<div class="topH">
					<h1>[ NAS设备状态统计 ]</h1></div>
				<div class="Echart">
					<!--<div id="round_div"></div>-->
					<!--<div id="rate" style="width: 100%;height: 90%;"></div>-->
					<div id="state_2" class="state"></div>
				</div>

			</div>
			<div class="right col-md-12 col-lg-12 col-sm-12">
				<div class="topH">
					<h1>[ 爱wifi热点类型分布 ]</h1></div>
				<div class="Echart" >
					<div id="hotspot"></div>
				</div>
			</div>
			<div class="right col-md-12 col-lg-12 col-sm-12">
				<div class="topH">
					<h1>[ 胖ap激活率统计 ]</h1></div>
				<div class="Hchart">
					<div id="jihuolv"></div>
				</div>
			</div>
		</div>
	</body>
	
	<!--以下是map-->
	<script type="text/javascript" src="${basePath}/ems/bigscreen_show/index/moduel/moduel_map/js/lib/raphael-min.js"></script>
	<script type="text/javascript" src="${basePath}/ems/bigscreen_show/index/moduel/moduel_map/js/res/chinaMapConfig.js"></script>
	<script type="text/javascript" src="${basePath}/ems/bigscreen_show/index/moduel/moduel_map/js/map.js"></script>
	
	<!--以下是<chart_1></chart_1>-->
	<script src="${basePath}/ems/bigscreen_show/index/js/chart_1/areaspline_chart.js" type="text/javascript" charset="utf-8"></script>

	<!--以下是chart_2-->
	<!--<script src="${basePath}/ems/bigscreen_show/index/js/chart_2/device_char_div.js" type="text/javascript" charset="utf-8"></script>-->
	<script src="${basePath}/ems/bigscreen_show/index/js/chart_2/fenbu.js" type="text/javascript" charset="utf-8"></script>

	<!--以下是chart_3-->
	<!--<script src="${basePath}/ems/bigscreen_show/index/js/e_chart/e_chart.js" type="text/javascript" charset="utf-8"></script>-->
	<script src="${basePath}/ems/bigscreen_show/index/js/chart_2/state_1.js" type="text/javascript" charset="utf-8"></script>

	<!--以下是chart_4-->
	<!--<script src="${basePath}/ems/bigscreen_show/index/js/chart_4/num_funnel_div.js" type="text/javascript" charset="utf-8"></script>-->
	<script src="${basePath}/ems/bigscreen_show/index/js/chart_4/rank.js" type="text/javascript" charset="utf-8"></script>
	<script src="${basePath}/ems/bigscreen_show/index/js/chart_5/jihuolv.js" type="text/javascript" charset="utf-8"></script>

	<!--以下是仪表盘-->
	<!--<script src="${basePath}/ems/bigscreen_show/index/js/dashboard/watch_chart.js" type="text/javascript" charset="utf-8"></script>-->
	<script src="${basePath}/ems/bigscreen_show/index/js/dashboard/dashboard_2.js" type="text/javascript" charset="utf-8"></script>

	<!--以下是chart_5-->
	<!--<script src="${basePath}/ems/bigscreen_show/index/js/chart_5/round_char_div.js" type="text/javascript" charset="utf-8"></script>-->
	<!--<script src="${basePath}/ems/bigscreen_show/index/js/chart_5/highcharts-3d.js" type="text/javascript" charset="utf-8"></script>-->
	<script src="${basePath}/ems/bigscreen_show/index/js/chart_5/dingshizhongduan.js" type="text/javascript" charset="utf-8"></script>
	

	<!--以下是chart_6-->
	<script src="${basePath}/ems/bigscreen_show/index/js/chart_6/chart_6.js" type="text/javascript" charset="utf-8"></script>
	<!--<script src="${basePath}/ems/bigscreen_show/index/js/chart_6/rate.js" type="text/javascript" charset="utf-8"></script>-->
	<script src="${basePath}/ems/bigscreen_show/index/js/chart_6/state_2.js" type="text/javascript" charset="utf-8"></script>

	<!--以下是chart_7-->
	<script src="${basePath}/ems/bigscreen_show/index/js/chart_7/many_spline_chart.js" type="text/javascript" charset="utf-8"></script>
	<script src="${basePath}/ems/bigscreen_show/index/js/chart_7/hotspot.js" type="text/javascript" charset="utf-8"></script>

	<script type="text/javascript">
		$(function() {
			// 自定义宽高
			var mapWidth = 1260;
			var mapHeight = mapWidth * 0.8;

			$('#ChinaMap1').SVGMap({
				mapName: 'china',
				mapWidth: mapWidth,
				mapHeight: mapHeight,
				stateInitColor: "",
				strokeColor: "#6F6BD7",
				strokeWidth: 1,
				//  hoverCallback:function(stateData,obj){
				//  	console.log(stateData);
				//  	console.log(obj);
				//  },
				stateHoverColor: "#d62a37"
					// showTip:0
			});
			$('#ChinaMapBg').SVGMap({
				mapName: 'china',
				mapWidth: mapWidth,
				mapHeight: mapHeight,
				strokeColor: "#2A0A6D",
				stateInitColor: "#2A0A6D",
				stateHoverColor: "#2A0A6D",
				showTip: 0
			});
			$('#ChinaMapBg,#ChinaMap1').css('position', "absolute");

			// 每隔1秒钟，滚播中国地图
			var num = 0;
			var china_province = [];
			for(var name in eval("china" + "MapConfig").names) {
				china_province.push(name);
			}
			var R = Raphael("ChinaMap2", 1000 + 888, 800);
			setInterval(function() {
				mapShow(num, china_province);
				showRect(R, pathPointArr[num], null);
				num = num + 1;
				if(num == 34) num = 0;
			}, 1000 * 5);
		});

		function mapShow(i, provinces) {
			var map_svg = $(".map-d").find(".svggroup").find("svg");
			var map_path = map_svg.find("path");
			map_path.attr("fill", "none");
			$(map_path[i]).attr("fill", "#d62a37");
		}

		/**
		 * 显示(绘制)信息展示框
		 * @param {Object} R 画布
		 * @param {Object} point 区域中心点对象 x y name
		 * @param {Object} val 区域值or值的集合对象
		 * By Shown: offset should included in configuration items, an object about 'config'
		 */
		function showRect(R, point, val) {
			R.clear();
			
			// 偏移量
			var xx = point.x * 1.8 + 200;
			var yy = point.y * 1.65 + 135;
			var name = point.name;
			var leftRect = {
				x: xx - 100,
				y: yy - 50,
				w: 160,
				h: 40
			};
			var rightRect = {
				x: xx,
				y: yy - 50,
				w: 100,
				h: 40
			}
			
			// 省都名字的样式
			var textAttr = {
				"fill": "#fff",
				"font-size": "24px",
				"cursor": "pointer"
			};
			left = R.rect(leftRect.x, leftRect.y, leftRect.w, leftRect.h).attr({
				fill: "#FA3F4D",
				stroke: "red",
				"stroke-width": 1,
				"stroke-opacity": 0.5
			})
			
			// 省份字的偏移量
			ltext = R.text((xx - 50), (yy - 30), name).attr(textAttr);
			right = R.rect(rightRect.x, rightRect.y, rightRect.w, rightRect.h).attr({
				fill: "white",
				stroke: "wihte",
				"stroke-width": 1,
				"stroke-opacity": 0.5
			})
			rtext = R.text((xx + 50), (yy - 30), Math.floor(1000000 * Math.random())).attr({
				"fill": "#FA3F4D",
				"font-size": "24px",
				"cursor": "pointer"
			});
		}
	</script>
</html>