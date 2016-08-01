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
						
						var now = getDateTime(new Date());
						var a = Math.floor(Math.random()*10000+1);
						var b = Math.floor(Math.random()*10000+1);
						var c = Math.floor(Math.random()*10000+1);
						var chart = $('#spline_div').highcharts();
	    		        if (chart.lbl) {
	    		            chart.lbl.destroy();
	    		        }
	    		        var label = '时间:'+now+'<br>已激活设备:'+a+'<br>已激活率:'+b+'%';
	    		        chart.lbl = chart.renderer.label(label, 15, 0)
	    	            .attr({
	    	                padding: 5,
	    	                r: 5,
	    	                fill: 'transparent',
	    	                zIndex: 5
	    	            })
	    	            .css({
	    	                color: 'white'
	    	            })
	    	            .add();
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
            max: 2,
            labels: {
                formatter: function () {
                	return '';
                }
            },
            gridLineColor: ''
        }],
        credits: { // 版权信息
            enabled: false
        },
        tooltip: {
        	enabled: false
        },
        legend: { // 图例
        	enabled: false
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