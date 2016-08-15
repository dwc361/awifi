$(function() {
	if(!Highcharts.theme) {
		Highcharts.setOptions({
			chart: {
				//              backgroundColor: 'black'
			},
			colors: ['#d9a831', '#367bdb', '#df5964'],
			title: {
				style: {
					color: 'silver',
					fontFamily: '微软雅黑'

				}
			},
			tooltip: {
				style: {
					color: 'silver',
					fontFamily: '微软雅黑'

				}
			}
		});
	}
	$('#round_div').highcharts({
		chart: {
			type: 'pie',
			backgroundColor: 'rgba(0,0,0,0)',
			options3d: {
				enabled: true,
				alpha: 20
			}
		},
		xAxis: {
			lineColor: '#fff',
			tickColor: 'red',
			labels: {
				style: {
					color: '#aaa',
					fontSize: '1.2rem',
					//					fontWeight: 'bold'
				}
			},
		},
		title: {
			text: ''
		},
		subtitle: {
			text: ''
		},
		legend: {
			enabled: false
		},
		credits: {
			enabled: false
		},
		tooltip: {
			headerFormat: '<span style="color:white;font-size:19px;">{series.name}</span><br>',
			pointFormat: '<span style="color:{series.color};font-size:19px;">{series.name}</span>: <b style="color:white;font-size:19px;">{point.y:.0f}/台</b><br/>',
			//	                backgroundColor: 'black',
			style: {
				fontFamily: 'sans-serif',
				shadow: false
			}
		},
		plotOptions: {
			pie: {
				size: 180,
				center: [200, 200],
				innerSize: 90,
				depth: 14,
				dataLabels: {
					enabled: true,
					//		                    backgroundColor: '#aaa',
					//		                    borderColor: '#42aeb8',
					//		                    borderWidth: 2,
					format: '<span style="color:#fff;">{series.name}</span>: <b style="color:#fff;">{point.percentage:.0f}%</b><br/>',

					style: {
						color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || '#fff',
						fontFamily: 'sans-serif',
						fontSize: '1.2rem',
						shadow: false,
						fontWeight: 'bolder'
					}
				}
			}
		},
		series: [{
			name: '胖AP激活率',

			data: [
				['光猫', 8],
				['二合一', 3],
				['三合一', 6]
			]
		}]
	});
	setInterval(function() {
		// 光 猫
		var chart = $('#round_div').highcharts(),
			point,
			newVal,
			inc;
		if(chart) {
			point = chart.series[0].points[0];
			inc = Math.round((Math.random() - 0.5) * 50);
			newVal = point.y + inc;
			if(newVal < 0 || newVal > 200) {
				newVal = point.y - inc;
			}
			point.update(newVal);
		}
		//二合一
		chart = $('#round_div').highcharts();
		if(chart) {
			point = chart.series[0].points[1];
			inc = Math.random() - 0.5;
			newVal = point.y + inc;
			if(newVal < 200 || newVal > 1000) {
				newVal = point.y - inc;
			}
			point.update(newVal);
		}
		//三合一
		chart = $('#round_div').highcharts();
		if(chart) {
			point = chart.series[0].points[2];
			inc = Math.random() - 0.5;
			newVal = point.y + inc;
			if(newVal < 200 || newVal > 1000) {
				newVal = point.y - inc;
			}
			point.update(newVal);
		}
	}, 1000 * 5);
});