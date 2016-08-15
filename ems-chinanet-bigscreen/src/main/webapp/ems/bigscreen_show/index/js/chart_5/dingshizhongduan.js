// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('zhongduan'));

// 指定图表的配置项和数据
option = {
	color: [
		'#21c6a5', '#65c7f7', '#096dc5'
	],
 
    calculable : true,
    xAxis : [
        {
        	axisLine: {
		show: false,
		onZero: true,
		lineStyle: {
			color: '#aaa',width: 1,
				type: 'solid',
				opacity: 1,
			},
		},
            type : 'category',
            boundaryGap : false,
            data : ['周一','周二','周三','周四','周五','周六','周日']
        }
    ],
    yAxis : [
        {
        	splitNumber:0,
        			axisLabel: {
			show:false,
			inside:'true'
//			formatter: '{value} ml'
		},
            type : 'value',
            axisLine: {
		show: false,
		onZero: true,
		lineStyle: {
			color: '#aaa',width: 1,
				type: 'solid',
				opacity: 1,
			},
		},
        }
    ],
    series : [
        {
            name:'成交',
            type:'line',
            smooth:true,
            itemStyle: {normal: {areaStyle: {type: 'default'}}},
            data:[10, 12, 21, 54, 260, 830, 710]
        },
        {
            name:'预购',
            type:'line',
            smooth:true,
            itemStyle: {normal: {areaStyle: {type: 'default'}}},
            data:[30, 182, 434, 791, 390, 30, 10]
        },
        {
            name:'意向',
            type:'line',
            smooth:true,
            itemStyle: {normal: {areaStyle: {type: 'default'}}},
            data:[1320, 1132, 601, 234, 120, 90, 20]
        }
    ]
};
 // 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);                   