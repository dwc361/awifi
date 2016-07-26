<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
		<meta content="telephone=no,email=no" name="format-detection">
		<title>Watch统计图</title>
		<script type="text/javascript" src="${basePath}/common/js/jquery/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="${basePath}/ems/bigscreen/chart/js/highcharts.js"></script>
		<script type="text/javascript" src="${basePath}/ems/bigscreen/chart/js/highcharts-more.js"></script>
		<script type="text/javascript">
			$(function () {
			    $('#container').highcharts({
			        chart: {
			            type: 'gauge',
			            plotBackgroundColor: null,
			            plotBackgroundImage: null,
			            plotBorderWidth: 0,
			            plotShadow: false
			        },
			        title: {
			            text: ''
			        },
			        pane: {
			            startAngle: -150,
			            endAngle: 150,
			            background: [{
// 			                backgroundColor: {
// 			                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
// 			                    stops: [
// 			                        [0, '#FFF'],
// 			                        [1, '#333']
// 			                    ]
// 			                },
							backgroundColor: 'transparent',
			                borderWidth: 0,
			                outerRadius: '109%'
			            }, {
// 			                backgroundColor: {
// 			                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
// 			                    stops: [
// 			                        [0, '#333'],
// 			                        [1, '#FFF']
// 			                    ]
// 			                },
							backgroundColor: 'transparent',
			                borderWidth: 1,
			                outerRadius: '107%'
			            }, {
							backgroundColor: 'transparent'
			            }, {
			                backgroundColor: '#DDD',
			                borderWidth: 0,
			                outerRadius: '105%',
			                innerRadius: '103%'
			            }]
			        },
			        // the value axis
			        yAxis: {
			            min: 0,
			            max: 100,
			            minorTickInterval: 'auto',
			            minorTickWidth: 1,
			            minorTickLength: 10,
			            minorTickPosition: 'inside',
			            minorTickColor: 'white',
			            tickPixelInterval: 30,
			            tickWidth: 2,
			            tickPosition: 'inside',
			            tickLength: 10,
			            tickColor: 'red',
			            labels: { // 仪表盘刻度值
			                step: 2,
			                rotation: 'auto',
			                style: {
			                	//fontWeight: 'bold',
		                        color: 'blue'
		                    }
			            },
			            title: {
			                text: '实时数据' 
			            },
			            plotBands: [{ // 仪表盘刻度背景
			                from: 0,
			                to: 100,
			                color: 'blue'
			            }]
			        },
			        credits: { // 版权信息
			            enabled: false
			        },
			        tooltip: { // 数据点提示框
			        	enabled: false
			        },
			        series: [{
			            name: 'Rate',
			            data: [80],
			            dataLabels: {
			                formatter: function () {
			                    return '<span style="color:blue; font-size:20px;">' + this.y + '%</span>';
			                },
			                borderWidth: 0
			            },
			            tooltip: {
			                valueSuffix: '%'
			            }
			        }]
			    },
			    // Add some life
			    function (chart) {
			        if (!chart.renderer.forExport) {
			            setInterval(function () {
			                var point = chart.series[0].points[0],
			                    newVal,
			                    inc = Math.round((Math.random() - 0.5) * 20);
			                newVal = point.y + inc;
			                if (newVal < 0 || newVal > 100) {
			                    newVal = point.y - inc;
			                }
			                point.update(newVal);
			            }, 3000);
			        }
			    });

				// 图表本身的背景设置成透明
			    var rect = $("#container").find("rect");
			    rect.attr("fill", "transparent");
			    //console.log(rect);
			    
			    // 指针设置成红色
			    var pointer_path = $("#container").find("g .highcharts-series").find("path");
			    pointer_path.attr("fill", "red");
			    
			    // 图表中心设置成红色
			    var pointer_circle = $("#container").find("g .highcharts-series").find("circle");
			    pointer_circle.attr("fill", "red");
				
				// body背景颜色设置成黑色
			    document.body.style.backgroundColor="black";
			});
		</script>
		
		<style>
			div#div1 {
				position: fixed;
				z-index: 1000;
				width:250px; 
				height:250px;
				margin:300px 0 0 150px;
			}
			
			div#div1>img {
				border: 0;
				width: 100%;
				height: 100%;
			}
			
			div#container {
				width:250px; 
				height:250px; 
				margin:-250px auto 0;
			}
		</style>
	</head>
	<body>
		<form id="areaspline_chart_form" method="post" action="">
			<div id="div1">
				<img src="../chart/image/watch_chart.png" />
				<div id="container"></div>
			</div>
		</form>
	</body>
</html>