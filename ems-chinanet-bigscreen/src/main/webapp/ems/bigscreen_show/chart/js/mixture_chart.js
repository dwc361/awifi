var colorArr = ['#7cb5ec', '#434348', '#90ed7d', '#f7a35c', '#8085e9','#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1','#000000','#560f23'];

$(function () {
    $('#column_div').highcharts({
    	chart: {
            zoomType: 'xy',
            events: {                                                         
				load: function() {            
					
				}                                                               
			}
        },
        title: {
            text: '混合图'
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: [
                '一月',
                '二月',
                '三月',
                '四月',
                '五月',
                '六月',
                '七月',
                '八月',
                '九月',
                '十月',
                '十一月',
                '十二月'
            ]
        },
        yAxis: {
            min: 0,
            title: {
                text: '数量 (个)'
            }
        },
        tooltip: {
            headerFormat: '{point.key}',
            pointFormat: '' + '',
            footerFormat: '{series.name}: 	{point.y:.1f} mm',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        legend: { // 图例
        	enabled: true
        },
        credits: { // 版权信息
            enabled: false
        },
        series: [{
            name: 'BeiJing',
            type: 'column',
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
        },{
            name: 'Tokyo',
            type: 'column',
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
        },{
            name: 'NewYork',
            type: 'column',
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
        },{
            name: 'HongKong',
            type: 'column',
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
        },{
            name: 'HangZhou',
            type: 'column',
            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
        },{
            type: 'spline',
            name: 'Average',
            data: [66, 77, 55, 33, 22, 66, 77, 155, 33, 88, 66, 77],
            color: 'black',
            dashStyle: 'shortdot',
            marker: {
                lineWidth: 2,
                lineColor: 'red',
                fillColor: 'white'
            }
        },{
            type: 'spline',
            name: 'Average',
            data: [166, 177, 155, 133, 122, 166, 177, 255, 133, 188, 166, 177],
            color: 'blue',
            marker: {
                lineWidth: 2,
                lineColor: 'red',
                fillColor: 'white'
            }
        }]
    }, function (chart) {
    	SetEverySeriesColor(chart);
    });
});

//设置每一块series的颜色值
function SetEverySeriesColor(chart) {
	//遍历设置每一个数据点颜色
    for (var i = 0; i < chart.series.length; i++) {
    	if(chart.series[i].type == 'column') {
    		chart.series[i].update({
                color: {
                    linearGradient: { x1: 0, y1: 0, x2: 1, y2: 0 }, //横向渐变效果 如果将x2和y2值交换将会变成纵向渐变效果
                    stops: [
                        [0, Highcharts.Color(colorArr[i]).setOpacity(1).get('rgba')],
                        [0.5, 'rgb(255, 255, 255)'],
                        [1, Highcharts.Color(colorArr[i]).setOpacity(1).get('rgba')]
                    ]
                }
            });
    	}
    }
}