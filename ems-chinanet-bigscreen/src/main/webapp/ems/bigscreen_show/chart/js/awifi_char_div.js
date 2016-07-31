now = new Date(); //定义一个时间对象
y = now.getFullYear(); //获取完整的年份(4位,1970-????)
m = now.getMonth(); //获取当前月份(0-11,0代表1月)
d = now.getDate(); //获取当前日(1-31)
$(function () {
    $('#awifi_div').highcharts({
        chart: {
        	type: 'column',
            zoomType: 'xy',
            backgroundColor: 'rgba(0,0,0,0)',
            events: {                                                         
				load: function() {            
					var series = this.series[0];
					setInterval(function() {
						var x = (new Date()).getTime(), // current time
							y = Math.random();
						series.addPoint([x, y], true, true);
					}, 1000);
				}                                                               
			}
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: [{
        	type: 'datetime',
        	lineColor: 'rgba(0,0,0,0)',
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
                text: ''
            },
            min: 0,
            max: 1,
            labels: {
            	enabled:false
            },
            gridLineColor: 'rgba(0,0,0,0)'//设置y轴颜色
        }],
        plotOptions: {
        	column: {
        		borderRadius:5,
                pointPadding: 0.2,
                borderWidth: 0,
                pointWidth: 12//柱子宽度
            }
        },
        credits: { // 版权信息
            enabled: false
        },
        tooltip: {
        	enabled: false
        },
        legend: { 
        	enabled: false
        },
        series: [{
            name: '爱WiFi热点',
            type: 'column',
            color:'#fff',
            yAxis: 0,
            pointStart: Date.UTC(y, m, d, 0), // 开始时间：y年m月d日0时
            pointInterval: 3600 * 1000, // 每隔1小时
            data: (function() {
				var data = [],                                                  
					time = (new Date()).getTime(),                              
					i;                                                          
																				
				for (i = -11; i <= 0; i++) {                                    
					data.push({                                                 
						x: time + i * 1000+1000,                                     
						y: Math.random()+1000                                        
					});                                                         
				}                                                               
				return data;                                                    
			})()
        }]
    });
	/////////////////////////
    $('#awifi_div2').highcharts({
        chart: {
        	type: 'column',
            zoomType: 'xy',
            events: {                                                         
				load: function() {            
					var series = this.series[0];
					setInterval(function() {
						var x = (new Date()).getTime(), // current time
							y = Math.random();
						series.addPoint([x, y], true, true);
					}, 1000);
				}                                                               
			}
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: [{
        	type: 'datetime',
        	lineColor: 'rgba(0,0,0,0)',
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
                text: ''
            },
            min: 0,
            max: 1,
            labels: {
            	enabled:false
            },
            gridLineColor: 'rgba(0,0,0,0)'//设置y轴颜色
        }],
        plotOptions: {
        	column: {
        		borderRadius:5,
                pointPadding: 0.2,
                borderWidth: 0,
                pointWidth: 12//柱子宽度
            },
            series: {
                colorByPoint: true//单个柱子不同颜色
            }
        },
        credits: { // 版权信息
            enabled: false
        },
        tooltip: {
        	formatter: function () {
                var s = '<b>' + this.x + '</b>';
                $.each(this.points, function () {
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
        legend: { 
        	enabled: false
        },
        series: [{
            name: '爱WiFi热点',
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
			})()
        }]
    });

 	// 图表本身的背景设置成透明
    var rect = $("#awifi_div2").find("rect");
    rect.attr("fill", "transparent");

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
