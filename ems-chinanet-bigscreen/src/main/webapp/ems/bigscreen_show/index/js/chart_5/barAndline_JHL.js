// 基于准备好的dom，初始化echarts实例   胖ap激活率统计
var jihuolvChart = echarts.init(document.getElementById('barAndline_JHL'));

// 指定图表的配置项和数据

//app.title = '折柱混合';

opt_jihuolv = {
	//  backgroundColor:'#333',
	color: [
		'#21c6a5', '#65c7f7'
	],
	tooltip: {
		trigger: 'axis'
	},
	xAxis: [{
		type: 'category',
		axisLine: {
			show: false,
			onZero: true,
			lineStyle: {
				color: '#aaa',
				width: 1,
				type: 'solid',
				opacity: 1,
			},
		},

		data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
	}],
	yAxis: [{
		type: 'value',
		min: 0,
		max: 250,
		interval: 50,
		axisLabel: {
			show:false
//			formatter: '{value} ml'
		},
		axisLine: {
			show: false,
			onZero: true,
			lineStyle: {
				color: '#aaa',
				width: 1,
				type: 'solid',
				opacity: 1,
			},
		},

	}, {
		type: 'value',
		min: 0,
		max: 25,
		interval: 5,
		axisLabel: {
//			formatter: '{value} °C'
show:false
		}
	}],
	series: [{
			name: '激活率',
			type: 'bar',
			data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
		},

		{
			name: '激活量',
			type: 'line',
			yAxisIndex: 1,
			data: [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
		}
	]
};

var app_jihuolv = {};

app_jihuolv.currentIndex = -1;

app_jihuolv.timeTicket = setInterval(function() {
	
	var dataLen = opt_jihuolv.series[0].data.length;

	// 取消之前高亮的图形
	jihuolvChart.dispatchAction({
		type: 'downplay',
		seriesIndex: 0,
		dataIndex: app_jihuolv.currentIndex
	});
	
	app_jihuolv.currentIndex = (app_jihuolv.currentIndex + 1) % dataLen;
	
	// 高亮当前图形
	jihuolvChart.dispatchAction({
		type: 'highlight',
		seriesIndex: 0,
		dataIndex: app_jihuolv.currentIndex
	});
	// 显示 tooltip
	jihuolvChart.dispatchAction({
		type: 'showTip',
		seriesIndex: 0,
		dataIndex: app_jihuolv.currentIndex
	});
}, 3000);
// 使用刚指定的配置项和数据显示图表。
jihuolvChart.setOption(opt_jihuolv);