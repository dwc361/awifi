// 基于准备好的dom，初始化echarts实例
var endChart = echarts.init(document.getElementById('end'));

// 指定图表的配置项和数据
opt_end = {
	//  backgroundColor: '#2c343c',
	color: [
		'#21c6a5', '#65c7f7', '#096dc5'
	],
	title: {
		text: '',
		subtext: ''
	},
	tooltip: {
		trigger: 'axis',
		itemGap: '	0'
	},
	legend: {
		data: ['', '']
	},
	toolbox: {
		show: false,
		feature: {
			dataView: {
				show: true,
				readOnly: false
			},
			magicType: {
				show: true,
				type: ['line', 'bar']
			},
			restore: {
				show: true
			},
			saveAsImage: {
				show: true
			}
		}
	},
	calculable: true,
	xAxis: [{
		type: 'category',
		data: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'],
		nameTextStyle: {
			color: "#fff",
			fontStyle: 'normal',
			fontFamily: 'sans-serif',
			fontSize: 24
		},
		axisLine: {
			show: true,
			onZero: true,
			lineStyle: {
				color: '#aaa',
				width: 1,
				type: 'solid',
				opacity:1,
			},
		},
	}],
	yAxis: [{
		type: 'value',
		nameTextStyle: {
			color: '#fff',
			fontStyle: 'normal',
			fontFamily: 'sans-serif',
			fontSize: 16
		},
		axisLine: {
			show: true,
			onZero: true,
			lineStyle: {
				color: '#aaa',
				width: 1,
				type: 'solid',
				opacity: 1,
			},
		},
		splitLine: {
			show: false,
			interval: 'auto',
			lineStyle: {
				color: ['#ccc'],
				width: 0,
				type: 'solid',
			},
		},
	}],
	series: [{
		name: '手机',
		type: 'bar',
		data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6],
		markPoint: {
			data: [{
				type: 'max',
				name: '最大值'
			}, {
				type: 'min',
				name: '最小值'
			}]
		},
		markLine: {
			data: [{
				type: 'average',
				name: '平均值'
			}]
		}
	}, {
		name: 'PC',
		type: 'bar',
		data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6],
		markPoint: {
			data: [{
				type: 'max',
				name: '最大值'
			}, {
				type: 'min',
				name: '最小值'
			}]
		},
		markLine: {
			data: [{
				type: 'average',
				name: '平均值'
			}]
		}
	}, {
		name: 'PAD',
		type: 'bar',
		data: [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6],
		markPoint: {
			data: [{
				name: '年最高',
				value: 182.2,
				xAxis: 7,
				yAxis: 183
			}, {
				name: '年最低',
				value: 2.3,
				xAxis: 11,
				yAxis: 3
			}]
		},
		markLine: {
			data: [{
				type: 'average',
				name: '平均值'
			}]
		}
	}]
};


var app_end = {};

app_end.currentIndex = -1;

app_end.timeTicket = setInterval(function() {
	var dataLen = opt_end.series[0].data.length;
	// 取消之前高亮的图形
	endChart.dispatchAction({
		type: 'downplay',
		seriesIndex: 0,
		dataIndex: app.currentIndex
	});
	app_end.currentIndex = (app_end.currentIndex + 1) % dataLen;
	// 高亮当前图形
	endChart.dispatchAction({
		type: 'highlight',
		seriesIndex: 0,
		dataIndex: app.currentIndex
	});
	// 显示 tooltip
	endChart.dispatchAction({
		type: 'showTip',
		seriesIndex: 0,
		dataIndex: app.currentIndex
	});
}, 5000);
// 使用刚指定的配置项和数据显示图表。
endChart.setOption(opt_end);