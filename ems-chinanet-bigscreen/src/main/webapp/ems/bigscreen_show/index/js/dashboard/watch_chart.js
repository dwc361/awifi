$(function() {
	$('#watch_div').highcharts({
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
					//                backgroundColor: {
					//                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
					//                    stops: [
					//                        [0, '#FFF'],
					//                        [1, '#333']
					//                    ]
					//                },
					backgroundColor: 'transparent',
					borderWidth: 0,
					outerRadius: '109%'
				}, {
					//                backgroundColor: {
					//                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
					//                    stops: [
					//                        [0, '#333'],
					//                        [1, '#FFF']
					//                    ]
					//                },
					backgroundColor: 'transparent',
					borderWidth: 1,
					outerRadius: '107%'
				}, {
					backgroundColor: 'transparent'
				}, {
					backgroundColor: 'transparent',
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
				minorTickColor: '',
				tickPixelInterval: 30,
				tickWidth: 0,
				tickPosition: 'inside',
				tickLength: 10,
				tickColor: 'red',
				lineWidth: 0,
				labels: { // 仪表盘刻度值
					step: 2,
					rotation: 'auto',
					style: {
						//fontWeight: 'bold',
						color: 'transparent'
					}
				},
				title: {
					text: ''
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
					formatter: function() {
						return '<span style="color:#fff; font-size:1.4rem;">' + this.y + '%</span>';
					},
					color: '#fff',
					style: {
						fontWeight: 'bold'
					},
					borderWidth: 0
				},
				tooltip: {
					valueSuffix: '%'
				}
			}]
		},
		// Add some life
		function(chart) {
			if(!chart.renderer.forExport) {
				setInterval(function() {
					var point = chart.series[0].points[0],
						newVal,
						inc = Math.round((Math.random() - 0.5) * 20);
					newVal = point.y + inc;
					if(newVal < 0 || newVal > 100) {
						newVal = point.y - inc;
					}
					point.update(newVal);
				}, 3000);
			}
		});

	// 图表本身的背景设置成透明
	var rect = $("#watch_div").find("rect");
	rect.attr("fill", "transparent");
	//console.log(rect);

	// 图表中心设置成红色
	var pointer_circle = $("#watch_div").find("g .highcharts-series").find("circle");
	pointer_circle.attr("fill", "red");

	$("#highcharts-7").find("path").attr("fill", "transparent")

	// 指针设置成红色
	var pointer_path = $("#watch_div").find("g .highcharts-series").find("path");
	pointer_path.attr("fill", "red");

	// body背景颜色设置成透明
	document.body.style.backgroundColor = "transparent";

	$("#highcharts-7").css({
		"position": "relative",
		"overflow": "hidden",
		"width": "229px",
		"height": "191px",
		"top": "15.2rem",
		"left": "0.92rem",
		"text-align": "left",
		"line-height": "normal"

	})
});