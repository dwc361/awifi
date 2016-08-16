// 基于准备好的dom，初始化echarts实例
var spreadChart = echarts.init(document.getElementById('spread'));

// 指定图表的配置项和数据
opt_spread = {
	color: [
		'#21c6a5', '#65c7f7', '#096dc5', '#4246ef'
	],
	//	backgroundColor: '#2c343c',
	tooltip: {
		trigger: 'item',
		formatter: "{b} : {c}"
	},
	series: [{
		name: '类型分布',
		type: 'pie',
		label: {
			normal: {
				show: true,
				position: 'outside',
				textStyle: {
					color: '#fff',
					fontStyle: 'normal',
					fontWeight: 'normal',
					fontFamily: 'sans-serif',
					fontSize: 18
				},
			},
		},
		radius: '65%',
		center: ['50%', '60%'],
		data: [{
			value: 335,
			name: '胖ap'
		}, {
			value: 310,
			name: 'AC'
		}, {
			value: 234,
			name: '瘦AP'
		}, {
			value: 135,
			name: '热点'
		}],
		itemStyle: {
			emphasis: {
				shadowBlur: 10,
				shadowOffsetX: 0,
				shadowColor: 'rgba(0, 0, 0, 0.5)'
			}
		}
	}]
};
                    

var spread_app = {};

spread_app.currentIndex = -1;

spread_app.timeTicket = setInterval(function() {
	var dataLen = opt_spread.series[0].data.length;
	// 取消之前高亮的图形
	spreadChart.dispatchAction({
		type: 'downplay',
		seriesIndex: 0,
		dataIndex: spread_app.currentIndex
	});
	spread_app.currentIndex = (spread_app.currentIndex + 1) % dataLen;
	// 高亮当前图形
	spreadChart.dispatchAction({
		type: 'highlight',
		seriesIndex: 0,
		dataIndex: spread_app.currentIndex
	});
	// 显示 tooltip
	spreadChart.dispatchAction({
		type: 'showTip',
		seriesIndex: 0,
		dataIndex: spread_app.currentIndex
	});
}, 5000);

// 使用刚指定的配置项和数据显示图表。
spreadChart.setOption(opt_spread);