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
	<link rel="stylesheet" type="text/css" href="${basePath}/ems/bigscreen_show/second/css/screen_2.css" />
	<script type="text/javascript" src="${basePath}/ems/bigscreen_show/second/js/screen.js"></script>
	<script type="text/javascript">
		var ws = null;
	
		$(function() {
			connect();
			
			$.ajax({
				type: "post",
				url: ROOF.Utils.projectName() + "/ems/bigscreen_show/dataShowAction/user_pv_uv_data.action",
				data: {username:$("#username").val(), content:$("#content").val()},
				dataType: "json",
				success: function(data) {
					$("#merchantNum").val(data.merchantNum);
					$("#userNum").val(data.userNum);
					$("#PV").val(data.PV);
					$("#UV").val(data.UV);
				}
			});
		});
	
		// 建立连接
		function connect() {
			//ws = new WebSocket("ws://127.0.0.1:8080/ssmr-ems-bigscreen/WebSocketServlet/websocket");
			ws = new SockJS(ROOF.Utils.projectName() + "/WebSocketServlet/websocket/sockjs");
	
			ws.onopen = function() {
				//console.log('Info: connection opened.');
			};

			ws.onmessage = function(event) {
				//console.log('Received: ' + event.data);
				var url = event.data;
				if(isUrl(url)) {
					echo("切换成功！");
					// 直接跳转
					window.location.href = url;
					// 定时跳转
					//setTimeout("javascript:location.href='" + url + "'", 5000); 
				}
			};

			ws.onclose = function(event) {
				//console.log('Info: connection closed.');
			};
		}
	
		// 断开连接
		function disconnect() {
			if (ws != null) {
				ws.close();
				ws = null;
			}
		}
	
		// 发送消息
		function echo(message) {
			if (ws != null) {
				ws.send(message);
			} else {
				alert('connection not established, please connect.');
			}
		}
		
		// 使用正则表达式判断是否为URL
		function isUrl(url) {
			var reg = /^(\w+):\/\/([^\/:]*)(?::(\d+))?\/([^\/]*)(\/.*)/;
			if (!reg.exec(url))
				return false
			return true
		}
	</script>
</head>

<body scrolling="no">
	<!--以下是新闻-->
	<footer class="news">
		<span>［2015-09-23 06:41:28］互联网作为20世纪最伟大的发明之一，把世界变成了“地球村”。但是，这块“新疆域”</span>
	</footer>
	<!--以下是左侧4栏-->
	<div class="leftPart">
		<div id='part_1_1' class="box"></div>
        <div id='part_2_1' class="box"></div>
		<div id='part_3_1' class="box"></div>
		<div id='part_4_1' class="box"></div>
	</div>

	<!--以下是中心1栏-->
	<div class="centerPart">
		<header>
			<div class="col-md-4 col-lg-4 col-sm-4">
				<section class="stnFont col-md-6 col-lg-6 col-sm-6">3500080</section>
				<section class="stnHr col-md-1 col-lg-1 col-sm-1"><img src="${basePath}/ems/bigscreen_show/second/img/xiaojiange.png" alt="" /></section>
				<section class="stnP col-md-4 col-lg-4 col-sm-4">
					<span class="stnP-span">用户总数</span><br />
					<i class="stnP-i"><input type="text" id="userNum" style= "background-color:transparent;border-style: solid; border-width: 0"/></i>
				</section>
			</div
			><div class="col-md-4 col-lg-4 col-sm-4" style="text-align: center;">
				<span class="stnFont-mid">全国商户总数— </span>
				<i class="blueFont"><input type="text" id="merchantNum" style= "background-color:transparent;border-style: solid; border-width: 0"/></i>
			</div
			><div class="col-md-4 col-lg-4 col-sm-4" style="padding-right: 12px;padding-left: 12px;line-height: 3rem;text-align: center;">
				<section class="stnFont-right col-md-5 col-lg-5 col-sm-5">PV- <i class="blueFont-right"><input type="text" id="PV" style= "background-color:transparent;border-style: solid; border-width: 0"/></i></section>
				<section class="stnHr col-md-1 col-lg-1 col-sm-1" style="width: 6%;">
					<img src="${basePath}/ems/bigscreen_show/second/img/xiaojiange.png" alt="" />
				</section>
				<section class="stnFont-right col-md-5 col-lg-5 col-sm-5">UV- <i class="blueFont-right"><input type="text" id="UV" style= "background-color:transparent;border-style: solid; border-width: 0"/></i></section>
			</div>
		</header>
		<div class="content">
			<div class="floater bottomLeft" ui-view="chart_5">
				<div id="gauge_YBP"></div>
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
		<div id='part_1_5' class="box"></div>
		<div id='part_2_5' class="box"></div>
		<div id='part_3_5' class="box"></div>
		<div id='part_4_5' class="box"></div>
	</div>
</body>

<!--react封装的组件模板-->
<script type="text/javascript">
	var chartNameList = ${list};
</script>
<script src="http://localhost:8081/secondScreen.js"></script>
<!-- <script src="${basePath}/react/build/secondScreen.js"></script> -->
<!-- <script src="${basePath}/ems/bigscreen_show/index/bigscreen_display/second/js/second_display_react_charts_templates.js"></script> -->

<!--以下是map-->
<script type="text/javascript" src="${basePath}/ems/bigscreen_show/second/moduel/moduel_map/js/lib/raphael-min.js"></script>
<script type="text/javascript" src="${basePath}/ems/bigscreen_show/second/moduel/moduel_map/js/res/chinaMapConfig.js"></script>
<script type="text/javascript" src="${basePath}/ems/bigscreen_show/second/moduel/moduel_map/js/map.js"></script>

<script type="text/javascript">
	$(function() {
		// 自定义宽高
		var mapWidth = document.body.clientWidth * 0.54;
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

		//				偏移量
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
			//				省都名字的样式
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

		//				省份字的偏移量
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