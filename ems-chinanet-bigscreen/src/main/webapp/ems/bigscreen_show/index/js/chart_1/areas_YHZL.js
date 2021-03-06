now = new Date(); //定义一个时间对象
y = now.getFullYear(); //获取完整的年份(4位,1970-????)
m = now.getMonth(); //获取当前月份(0-11,0代表1月)
d = now.getDate(); //获取当前日(1-31)
h = now.getHours(); //时  
$(function() {
	var x_arr = new Array();
	for(i = h - 11; i <= h; i++) {
		x_arr.push(Date.UTC(y, m, d, i));
	}

	$.ajax({
		type: 'post',
		dataType: "json",
		url: ROOF.Utils.projectName() + "/ems/bigscreen_show/dataShowAction/areaspline_chart_data.action",
		data: {
			'x_json': $.toJSON(x_arr)
		},
		success: function(d) {
			if(d.data != null) {
				var data = [];
				$.each(d.data, function(i, n) {
					data.push({
						x: n.x,
						y: n.y
					});
					$("#areaspline1").html(n.y);
				});
			}
			show_areaspline_chart(data);
		},
		error: function(d) {
//			alert(d.statusText);
		}
	});
});

function show_areaspline_chart(data) {
	Highcharts.getOptions().colors = "#18909c,#156871,#0e3236,#090b0b".split(",");
	$('#areas_YHZL').highcharts({
		chart: {
			type: 'areaspline',
			events: {
				load: function() {
					var series = this.series[0];
					series.points[series.points.length - 1].setState('hover');
					setInterval(function() {
						h++;
						var x = Date.UTC(y, m, d, h);
						var x_arr = new Array();
						x_arr.push(x);

						$.ajax({
							type: 'post',
							dataType: "json",
							url: ROOF.Utils.projectName() + "/ems/bigscreen_show/dataShowAction/areaspline_chart_data.action",
							data: {
								'x_json': $.toJSON(x_arr)
							},
							success: function(d) {
								if(d.data != null) {
									$.each(d.data, function(i, n) {
										var x = n.x;
										var y = n.y;
										var a = n.a;
										var b = n.b;
										var c = n.c;

										series.points[series.points.length - 1].setState();
										series.addPoint([x, y], true, true);
										series.points[series.points.length - 1].setState('hover');
										
										$("#areaspline1").html(y);
									});
								}
							},
							error: function(d) {
//								alert(d.statusText);
							}
						});

					}, 3000);
				}
			}
		},
		title: {
			text: '',
			style: {
				color: 'white', // 标题白色字体
				fontWeight: 'bold' // 标题加粗
			}
		},
		subtitle: {
			text: ''
		},
		xAxis: {
			lineColor: '#4debf9',
			tickColor: '#4debf9',
			labels: {
				style: {
					color: '#aaa',
					  fontSize:'1.2rem',
//					fontWeight: 'bold'
				}
			},
			type: 'datetime',
			dateTimeLabelFormats: {
				millisecond: '%H:%M:%S.%L',
				second: '%H:%M:%S',
				minute: '%H:%M',
				hour: '%H:%M',
				day: '%e. %b %H:%M',
				week: '%e. %b',
				month: '%b \'%y',
				year: '%Y'
			}
		},
		yAxis: {
			title: {
				text: ''
			},
			labels: {
				formatter: function() {
					return '';
				}
			},
			gridLineColor: ''
		},
		legend: { // 图例
			enabled: false
		},
		tooltip: {
			enabled: false
		},
		credits: { // 版权信息
			enabled: false
		},
		plotOptions: { // 数据点配置
			areaspline: {
				fillColor: {
					linearGradient: {
						x1: 0,
						y1: 0,
						x2: 0,
						y2: 1
					},
					stops: [
						[0, Highcharts.getOptions().colors[0]],
						[1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
					]
				},
				marker: {
					enabled: false,
					radius: 6
				},
				lineWidth: 1,
				states: {
					hover: {
						lineWidth: 2
					}
				},
				threshold: null
			},
			series: {
				lineWidth: 2,
				lineColor: '#57ebf8'
			}
		},
		series: [{ // 数据列
			type: 'areaspline',
			name: '业务受理量',
			pointStart: Date.UTC(y, m, d, 0), // 开始时间：y年m月d日0时
			pointInterval: 3600 * 1000, // 每隔1小时
			data: (function() {
				return data;
			})(),
			marker: {
				lineWidth: 1,
				lineColor: '#fff',
				fillColor: '#d62a37'
			}
		}]
	});

	// 图标div宽度和高度定义
	//$("#areas_YHZL").css("width", $(window).width() / 2 + 'px');
	//$("#areas_YHZL").css("height", $(window).height() / 2 + 'px');

	// 图表本身的背景设置成透明
	var rect = $("#areas_YHZL").find("rect");
	rect.attr("fill", "transparent");

	// body背景颜色设置成透明
	document.body.style.backgroundColor = "transparent";
}

function getDateTime(now) {
	var year = now.getFullYear(); //年
	var month = now.getMonth() + 1; //月
	var date = now.getDate(); //日
	var hour = now.getHours(); //时  
	var minute = now.getMinutes(); //分     
	var second = now.getSeconds(); //秒
	return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}