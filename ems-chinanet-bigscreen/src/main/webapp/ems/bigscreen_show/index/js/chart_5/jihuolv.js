// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('jihuolv'));

// 指定图表的配置项和数据

//app.title = '折柱混合';

option = {
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
			name: '蒸发量',
			type: 'bar',
			data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
		},

		{
			name: '平均温度',
			type: 'line',
			yAxisIndex: 1,
			data: [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
		}
	]
};
// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);