// 基于准备好的dom，初始化echarts实例
var hotspotChart = echarts.init(document.getElementById('hotspot'));

// 指定图表的配置项和数据
var x_data = [['学校','医院','码头','酒店','公园'],
              ['机场','浴室','商场','游乐场','文化厅','车站'],
              ['饭馆','图书馆','展览馆','美容店','体育场','招待所']];
var y_data = [[37, 77.4, 58, 14.7, 25],
              [67.1, 25.4, 78.1, 19.8, 29.1, 67.9],
              [72, 75.4, 6.8, 40.8, 19.6, 37.3]];

var x_data0 = ['学校','医院','码头','酒店','公园'];
var y_data0 = [37, 77.4, 58, 14.7, 25];

var x_data1 = ['机场','浴室','商场','游乐场','文化厅','车站'];
var y_data1 = [67.1, 25.4, 78.1, 19.8, 29.1, 67.9];

var x_data2 = ['饭馆','图书馆','展览馆','美容店','体育场','招待所'];
var y_data2 = [72, 75.4, 6.8, 40.8, 19.6, 37.3];

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
		type : 'category',
		data : x_data[0],
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
		data: y_data[0],
		type: 'scatter',
		symbolSize: function(data) {
			return 32;
		},
		label: {
			emphasis: {
				show: true,
				formatter: function(param) {
					return param.data;
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

var hot_spot_index = 0;
var app_hotspot = {};
app_hotspot.currentIndex = -1;
app_hotspot.timeTicket = setInterval(function() {
	var dataLen = opt_hotspot.series[0].data.length;
	
	app_hotspot.currentIndex++;
	if(app_hotspot.currentIndex == dataLen) {
		app_hotspot.currentIndex = -1;
		hot_spot_index = (hot_spot_index + 1) % x_data.length;
		
		// 重新加载图表内容
		opt_hotspot.xAxis.data = x_data[hot_spot_index];
		opt_hotspot.series[0].data = y_data[hot_spot_index];
		hotspotChart.setOption(opt_hotspot, true);
		
		// 取消之前高亮的图形
		hotspotChart.dispatchAction({
			type: 'downplay',
			seriesIndex: 0,
			dataIndex: app_hotspot.currentIndex
		});
		
		// 隐藏 tooltip
		hotspotChart.dispatchAction({
			type: 'hideTip',
			seriesIndex: 0,
			dataIndex: app_hotspot.currentIndex
		});
	} else {
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
	}
}, 1000);
//用刚指定的配置项和数据显示图表。
hotspotChart.setOption(opt_hotspot);