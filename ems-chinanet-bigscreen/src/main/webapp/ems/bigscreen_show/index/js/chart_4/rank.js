var typeNameArray0 = [
	'浙江', '河北', '陜西', '河南', '山东', '甘肃'
];
var typeNameArray1 = [
	'山西', '辽宁', '吉林', '黑龙江', '云南', '贵州'
];
var typeNameArray2 = [
	'福建', '广东', '海南', '台湾', '四川', '湖北'
];
var typeNameArray3 = [
	'湖南', '江西', '安徽', '江苏', '青海', '新疆'
];
var typeNameArray4 = [
	'内蒙古', '宁夏', '西藏', '广西', '北京', '天津'
];
var typeNameArray5 = [
	'重庆', '上海'
];

var numberArray0 = [
	1000, 899, 890, 880, 870, 850
];
var numberArray1 = [
	812, 809, 779, 760, 710, 670
];
var numberArray2 = [
	660, 600, 520, 570, 490, 460
];
var numberArray3 = [
	410, 390, 360, 230, 200, 190
];
var numberArray4 = [
	160, 150, 130, 120, 100, 99
];
var numberArray5 = [
	80, 70
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

var app = {};
var index = 0;
var typeNameArray;
var numberArray;
app.timeTicket = setInterval(function() {
	if(index == 0) index = 1;
	else if(index == 1) index = 2;
	else if(index == 2) index = 3;
	else if(index == 3) index = 4;
	else if(index == 4) index = 5;
	else if(index == 5) index = 0;
	if(index == 0) {
		typeNameArray = typeNameArray0;
		numberArray = numberArray0;
	} else if(index == 1) {
		typeNameArray = typeNameArray1;
		numberArray = numberArray1;
	}else if(index == 2) {
		typeNameArray = typeNameArray2;
		numberArray = numberArray2;
	}else if(index == 3) {
		typeNameArray = typeNameArray3;
		numberArray = numberArray3;
	}else if(index == 4) {
		typeNameArray = typeNameArray4;
		numberArray = numberArray4;
	}else if(index == 5) {
		typeNameArray = typeNameArray5;
		numberArray = numberArray5;
	}
	
	rank_option.yAxis.data = typeNameArray;
	rank_option.series[0].data = numberArray;
    rankChart.setOption(rank_option, true);
}, 1000);

// 使用刚指定的配置项和数据显示图表。
rankChart.setOption(rank_option);