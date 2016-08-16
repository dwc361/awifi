var typeNameArray0 = [
	'浙江', '河北', '陜西', '河南', '山东', '甘肃', '海南'
];
var typeNameArray1 = [
	'山西', '辽宁', '吉林', '黑龙江', '云南', '贵州'
];
var typeNameArray2 = [
	'福建', '广东', '重庆', '上海', '四川', '湖北'
];
var typeNameArray3 = [
	'湖南', '江西', '安徽', '江苏', '青海', '新疆'
];
var typeNameArray4 = [
	'内蒙古', '宁夏', '西藏', '广西', '北京', '天津'
];

var numberArray0 = [
	812, 899, 890, 880, 870, 850, 70
];
var numberArray1 = [
	812, 809, 779, 760, 710, 670
];
var numberArray2 = [
	812, 600, 520, 570, 490, 460
];
var numberArray3 = [
	812, 390, 360, 230, 200, 190
];
var numberArray4 = [
	812, 150, 130, 120, 100, 99
];

// 基于准备好的dom，初始化echarts实例
var rankChart = echarts.init(document.getElementById('rank'));

// 指定图表的配置项和数据
rank_option = {
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
		axisLabel: {
//			interval: 0,
//			rotate: 45,
//			margin: 2,
			textStyle: {
				color: "#fff",
				fontSize: 12
			}
		},
		data: typeNameArray0,
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
	},
	series: [{
		type: 'bar',
		data: numberArray0,
		itemStyle: {
			normal: {

				color: function(params) {
					var colorList = ['#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5'];
					return colorList[params.dataIndex];
				}
			}
		}
	}]
};

var rank_app = {};
var index = 0;
var typeNameArray;
var numberArray;
rank_app.timeTicket = setInterval(function() {
	if(index == 0) index = 1;
	else if(index == 1) index = 2;
	else if(index == 2) index = 3;
	else if(index == 3) index = 4;
	else if(index == 4) index = 0;
	if(index == 0) {
		typeNameArray = typeNameArray0;
		numberArray = numberArray0;
	} else if(index == 1) {
		typeNameArray = typeNameArray1;
		numberArray = numberArray1;
	} else if(index == 2) {
		typeNameArray = typeNameArray2;
		numberArray = numberArray2;
	} else if(index == 3) {
		typeNameArray = typeNameArray3;
		numberArray = numberArray3;
	} else if(index == 4) {
		typeNameArray = typeNameArray4;
		numberArray = numberArray4;
	}

	rank_option.yAxis.data = typeNameArray;
	rank_option.series[0].data = numberArray;
	rankChart.setOption(rank_option, true);
}, 3000);

// 使用刚指定的配置项和数据显示图表。
rankChart.setOption(rank_option);