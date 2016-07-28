now = new Date(); //定义一个时间对象
y = now.getFullYear(); //获取完整的年份(4位,1970-????)
m = now.getMonth(); //获取当前月份(0-11,0代表1月)
d = now.getDate(); //获取当前日(1-31)
$(function () {
    $('#spline_div').highcharts({
        chart: {
            zoomType: 'xy',
            events: {                                                         
				load: function() {            
					var series = this.series[0];
					var series1 = this.series[1];
					setInterval(function() {
						var x = (new Date()).getTime(), // current time
							y = Math.random();
						series.addPoint([x, y], true, true);
						series1.addPoint([x, y], true, true);
					}, 1000);
				}                                                               
			}
        },
        title: {
            text: '胖AP激活率统计图'
        },
        subtitle: {
            text: ''
        },
        xAxis: [{
        	type: 'datetime',
            dateTimeLabelFormats: {
            	millisecond: '%H:%M:%S.%L',
            	second: '%H:%M:%S',
            	minute: '%H:%M',
            	hour: '%H:%M',
            	day: '%e. %b',
            	week: '%e. %b',
            	month: '%b \'%y',
            	year: '%Y'
            }
        }],
        yAxis: [{ // Primary yAxis
        	title: {
                text: '已激活设备'
            },
            min: 0,
            max: 1,
            labels: {
                formatter: function () {
                	return this.value;
                }
            },
            gridLineColor: '',
            lineWidth: 1,
        }, { // Secondary yAxis
        	title: {
                text: '已激活率'
            },
            min: 0,
            max: 1,
            labels: {
                formatter: function () {
                    return this.value;
                }
            },
            gridLineColor: '',
            lineWidth: 1,
            opposite: true
        }],
        credits: { // 版权信息
            enabled: false
        },
        tooltip: {
        	formatter: function () {
                var s = '<b>' + this.x + '</b>';
                $.each(this.points, function () {
	                //console.log(this);
                    s += '<br/>' + this.series.name + ': ' + this.y + this.series.tooltipOptions.valueSuffix;
                });
                return s;
            },
            positioner: function () {
                return { x: 100, y: 120 };
            },
            shared: true,
            shadow: false,
            borderWidth: 0,
            backgroundColor: 'transparent'
        },
        legend: { // 图例
            layout: 'vertical',
            align: 'left',
            x: 80,
            verticalAlign: 'top',
            y: 55,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
        },
        series: [{
            name: '已激活设备',
            type: 'column',
            yAxis: 0,
            pointStart: Date.UTC(y, m, d, 0), // 开始时间：y年m月d日0时
            pointInterval: 3600 * 1000, // 每隔1小时
            data: (function() {
				var data = [],                                                  
					time = (new Date()).getTime(),                              
					i;                                                          
																				
				for (i = -11; i <= 0; i++) {                                    
					data.push({                                                 
						x: time + i * 1000,                                     
						y: Math.random()                                        
					});                                                         
				}                                                               
				return data;                                                    
			})(),
			color: {
				linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                stops: [
                    [0, '#333'],
                    [1, '#FFF']
                ]
			},
            tooltip: {
                valueSuffix: ' 件'
            }
        }, {
            name: '已激活率',
            type: 'spline',
            yAxis: 1,
            pointStart: Date.UTC(y, m, d, 0), // 开始时间：y年m月d日0时
            pointInterval: 3600 * 1000, // 每隔1小时
            data: (function() {
				var data = [],                                                  
					time = (new Date()).getTime(),                              
					i;                                                          
																				
				for (i = -11; i <= 0; i++) {                                    
					data.push({                                                 
						x: time + i * 1000,                                     
						y: Math.random()                                        
					});                                                         
				}                                                               
				return data;                                                    
			})(),
            marker: {
                lineWidth: 2,
                lineColor: 'red',
                fillColor: 'white'
            },
            color: Highcharts.getOptions().colors[1],
            dashStyle: 'shortdot',
            tooltip: {
                valueSuffix: ' %'
            }
        }]
    });

 	// 图表本身的背景设置成透明
    var rect = $("#spline_div").find("rect");
    rect.attr("fill", "transparent");
    //console.log(rect);

    // body背景颜色设置成透明
    document.body.style.backgroundColor="transparent";
});

function getDateTime(now) {     
	var year=now.getFullYear(); //年
  	var month=now.getMonth()+1; //月
  	var date=now.getDate(); //日
  	var hour=now.getHours(); //时  
  	var minute=now.getMinutes(); //分     
  	var second=now.getSeconds(); //秒
  	return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
}