// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('rank'));

// 指定图表的配置项和数据

option = {
//	color: [
//		'#21c6a5', '#65c7f7', '#096dc5'
//	],
	tooltip: {
		trigger: 'axis',
		axisPointer: {
			type: 'shadow'
		}
	},
	grid: {
		left: '3%',
		right: '4%',
		bottom: '3%',
		containLabel: true
	},
	xAxis: {
		type: 'value',
		boundaryGap: [0, 0.01],
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
	},
	yAxis: {
		type: 'category',
		data: ['澳门', '海南', '西藏', '宁夏', '青海', '香港', '台湾', '广西', '天津', '甘肃', '重庆', '贵州', '山西', '新疆', '陕西', '吉林', '江西', '湖南', '黑龙江', '内蒙古', '湖北', '云南', '福建', '上海', '安徽', '北京', '河北', '河南', '四川', '浙江', '辽宁', '山东', '江苏', '广东'],
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
	},
	series: [{
		type: 'bar',
		data: [235170, 241901, 269941, 415591, 870769, 960606, 1058416, 1124934, 1274043, 1391961, 1481961, 1541342, 1788844, 1970530, 2773405, 2921388, 3038549, 3112855, 3439042, 3549601, 3752547, 4123875, 4169945, 4535957, 5665334, 5822675, 6383995, 6804941, 7892072, 8125849, 10660686, 16928489, 21222070, 25673858],
		itemStyle: {
			normal: {

				color: function(params) {
					var colorList = ['#21c6a5', '#65c7f7', '#096dc5','#21c6a5', '#65c7f7', '#096dc5','#21c6a5', '#65c7f7', '#096dc5','#21c6a5', '#65c7f7', '#096dc5','#21c6a5', '#65c7f7', '#096dc5','#21c6a5', '#65c7f7', '#096dc5','#21c6a5', '#65c7f7', '#096dc5','#21c6a5', '#65c7f7', '#096dc5','#21c6a5', '#65c7f7', '#096dc5','#21c6a5', '#65c7f7', '#096dc5','#21c6a5', '#65c7f7', '#096dc5','#21c6a5', '#65c7f7', '#096dc5'];
					return colorList[params.dataIndex];
				}
			}
		}
	}]
};

// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);