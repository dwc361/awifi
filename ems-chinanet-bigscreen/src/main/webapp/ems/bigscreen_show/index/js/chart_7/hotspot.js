// 基于准备好的dom，初始化echarts实例
var hotspotChart = echarts.init(document.getElementById('hotspot'));

// 指定图表的配置项和数据
var data = [
	[
		[38604, 57, 127096869, '学校'],
		[41163, 77.4, 227662440, '医院'],
		[20516, 68, 1154605773, '码头'],
		[13670, 74.7, 185289082, ' 酒店'],
		[28599, 55, 432986705, '公园'],
		[29476, 67.1, 576943299, '机场'],
		[31476, 25.4, 78958237, '浴室'],
		[28666, 78.1, 256854830, '商场'],
		[3777, 19.8, 870601340, '游乐场'],
		[29550, 29.1, 122249285, '文化厅'],
		[2076, 67.9, 201934354, '车站'],
		[12087, 72, 483972254, '饭馆'],
		[24021, 75.4, 128397534, '图书馆'],
		[43296, 76.8, 4240375, '展览馆'],
		[10088, 40.8, 88195258, '美容店'],
		[19349, 49.6, 147568552, '体育场'],
		[10670, 37.3, 293994605, '招待所']
	]
];

opt_hotspot = {
	tooltip: {
		trigger: 'axis',
		itemGap: '	0',
		axisPointer: {
			type: 'shadow'
		}
	},
	//  backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
	//      offset: 0,
	//      color: '#333'
	//  }, {
	//      offset: 1,
	//      color: '#333'
	//  }]),

	xAxis: {

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
			lineStyle: {
				type: 'dashed'
			}
		}
	},
	yAxis: {
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
			lineStyle: {
				type: 'dashed'
			}
		},
		scale: true
	},
	series: [{
		name: '',
		data: data[0],
		type: 'scatter',
		symbolSize: function(data) {
			return Math.sqrt(data[2]) / 5e2;
		},
		label: {
			emphasis: {
				show: true,
				formatter: function(param) {
					return param.data[3];
				},
				position: 'top'
			}
		},

		itemStyle: {
			normal: {
				shadowBlur: 10,
				shadowColor: 'rgba(120, 36, 50, 0.5)',
				shadowOffsetY: 5,
				color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
					     offset: 0,
                    color: 'rgb(129, 227, 238)'
                }, {
                    offset: 1,
                    color: 'rgb(25, 183, 207)'
				}])
			}
		}

	}]
};

var app_hotspot = {};

app_hotspot.currentIndex = -1;

app_hotspot.timeTicket = setInterval(function() {
	
	var dataLen = opt_hotspot.series[0].data.length;

	// 取消之前高亮的图形
	hotspotChart.dispatchAction({
		type: 'downplay',
		seriesIndex: 0,
		dataIndex: app_hotspot.currentIndex
	});
	
	app_hotspot.currentIndex = (app_hotspot.currentIndex + 1) % dataLen;
	
	// 高亮当前图形
	hotspotChart.dispatchAction({
		type: 'highlight',
		seriesIndex: 0,
		dataIndex: app_hotspot.currentIndex
	});
	// 显示 tooltip
	hotspotChart.dispatchAction({
		type: 'showTip',
		seriesIndex: 0,
		dataIndex: app_hotspot.currentIndex
	});
}, 3000);
//用刚指定的配置项和数据显示图表。
hotspotChart.setOption(opt_hotspot);