function selectfrom(lowValue, highValue) {
	var choice = highValue - lowValue + 1;
	return Math.floor(Math.random() * choice + lowValue);
}
$(document).ready(function() {
	var typeNameArray = [
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

	var numberArray[0] = [
		1000, 899, 890, 880, 870, 850
	];
	var numberArray[1] = [
		812, 809, 779, 760, 710, 670
	];
	var numberArray[2] = [
		660, 600, 520, 570, 490, 460
	];
	var numberArray[3] = [
		410, 390, 360, 230, 200, 190
	];
	var numberArray[4] = [
		160, 150, 130, 120, 100, 99
	];
	var numberArray[5] = [
		80, 70
	];

	var index = 0;
	showHotareaDistribution(typeNameArray, numberArray);
	setInterval(function() {
		if(index == 0) index = 1;
		else if(index == 1) index = 2;
		else if(index == 2) index = 3;
		else if(index == 3) index = 4;
		else if(index == 4) index = 5;
		else if(index == 5) index = 0;
		
		if(index == 0) {
			showHotareaDistribution(typeNameArray[0], numberArray[0]);
		} else if(index == 1) {
			showHotareaDistribution(typeNameArray[1], numberArray[1]);
		}else if(index == 2) {
			showHotareaDistribution(typeNameArray[2], numberArray[2]);
		}else if(index == 3) {
			showHotareaDistribution(typeNameArray[3], numberArray[3]);
		}else if(index == 4) {
			showHotareaDistribution(typeNameArray[4], numberArray[4]);
		}else if(index == 5) {
			showHotareaDistribution(typeNameArray[5], numberArray[5]);
		}
	}, 3000);
});

function showHotareaDistribution(typeNameArray, numberArray) {
	var funnel_div = echarts.init(document.getElementById('funnel_div'));
	option = {
		title: {
			text: '',
			subtext: ''
		},
		calculable: false,
		series: [{
			name: '排名',
			type: 'funnel',
			x: '10%',
			y: 60,
			//x2: 80,
			y2: 60,
			width: '80%',
			// height: {totalHeight} - y - y2,
			min: 0,
			max: 1000,
			minSize: '0%',
			maxSize: '100%',
			sort: 'descending', //数据排序，如果是：ascending，则是金字塔状
			gap: 0.1, //数据图像间距
			data: (function() {
				var res = [];
				for(var i = 0; i < 32; i++) {
					res.push({
						"value": numberArray[i],
						"name": typeNameArray[i]
					});
				}
				return res;
			})(),
			itemStyle: {
				normal: {
					borderColor: '#fff',
					borderWidth: 0,
					label: {
						show: true,
						position: 'inside',
						textStyle: {
							color: '#fff'
						}
					},
					labelLine: {
						show: false,
						length: 10,
						lineStyle: {
							// color: 各异,
							width: 1,
							type: 'solid'
						}
					},
					color: function(params) {
						var colorList = ['#367bdb', '#367bdb', '#367bdb', '#367bdb', '#367bdb', '#367bdb', '#367bdb', '#367bdb',
							'#d9a831', '#d9a831', '#d9a831', '#d9a831', '#d9a831', '#d9a831', '#d9a831', '#d9a831',
							'#42aeb8', '#42aeb8', '#42aeb8', '#42aeb8', '#42aeb8', '#42aeb8', '#42aeb8', '#42aeb8',
							'#df5964', '#df5964', '#df5964', '#df5964', '#df5964', '#df5964', '#df5964', '#df5964'
						];
						return colorList[params.dataIndex];
					}
				}
			}
		}]
	};
//	setInterval(function() {
//		numberArray[0] = selectfrom(960, 1000);
//		numberArray[1] = selectfrom(960, 900);
//		numberArray[2] = selectfrom(960, 1000);
//		numberArray[3] = selectfrom(960, 1000);
//		numberArray[4] = selectfrom(960, 1000);
//		numberArray[5] = selectfrom(960, 1000);
//		for(var i = 0; i < 32; i++) {
//			option.series[0].data[i].value = numberArray[i];
//		}
//		funnel_div.setOption(option, true);
//	}, 3000);
}