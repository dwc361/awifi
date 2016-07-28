now = new Date(); //定义一个时间对象
y = now.getFullYear(); //获取完整的年份(4位,1970-????)
m = now.getMonth(); //获取当前月份(0-11,0代表1月)
d = now.getDate(); //获取当前日(1-31)
$(function () {
	Highcharts.getOptions().colors = "#007799,#434348,#90ed7d,#f7a35c,#8085e9,#f15c80,#e4d354,#2b908f,#f45b5b,#91e8e1".split(",");
    $('#areaspline_div').highcharts({
        chart: {
            type: 'areaspline',
            events: {                                                           
				load: function() {            
					var series = this.series[0];                                
					setInterval(function() {
						var x = (new Date()).getTime(), // current time
							y = Math.random();
						series.addPoint([x, y], true, true);

						var now = getDateTime(new Date());
						var a = Math.floor(Math.random()*10000+1);
						var b = Math.floor(Math.random()*10000+1);
						var c = Math.floor(Math.random()*10000+1);
						var chart = $('#areaspline_div').highcharts();
	    		        if (chart.lbl) {
	    		            chart.lbl.destroy();
	    		        }
	    		        var label = '时间:'+now+'<br>正常:'+a+'<br>故障:'+b+'<br>离线:'+c;
	    		        chart.lbl = chart.renderer.label(label, 15, 40)
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
            text: '[设备状态统计]',
            style: {
                color: 'white', // 标题白色字体
                fontWeight: 'bold' // 标题加粗
            }
        },
        subtitle: {
            text: ''
        },
        xAxis: {
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
        },
        yAxis: {
            title: {
                text: ''
            },
            labels: {
            	formatter: function () {
                    return '';
                }
            },
            max:2,
            gridLineColor: ''
        },
        legend: { // 图例
        	enabled: true
        },
        tooltip: {
        	enabled: false
        },
        credits: { // 版权信息
            enabled: false
        },
        plotOptions: { // 数据点配置
            areaspline: {
                fillColor: {
                    linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},	
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                    ]
                },
                marker: {
                    radius: 2
                },
                lineWidth: 1,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
            }
        },
        series: [{ // 数据列
            type: 'areaspline',
            name: '故障',
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
    var rect = $("#areaspline_div").find("rect");
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