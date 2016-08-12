now = new Date(); //定义一个时间对象
y = now.getFullYear(); //获取完整的年份(4位,1970-????)
m = now.getMonth(); //获取当前月份(0-11,0代表1月)
d = now.getDate(); //获取当前日(1-31)
h = now.getHours(); //时  
$(function () {
	var x_arr = new Array();
	for (i = h-11; i <= h; i++) {
		x_arr.push(Date.UTC(y, m, d, i));
	}
	
	$.ajax({ 
	    type: 'post', 
	    dataType : "json", 
	    url: ROOF.Utils.projectName() + "/ems/bigscreen_show/dataShowAction/areaspline_chart_data.action",
	    data: {'x_json':$.toJSON(x_arr)},
	    success: function(d) {
	    	if(d.data != null) {
	    		var data_a = [];
	    		var data_b = [];
	    		var data_c = [];
	    		$.each(d.data, function(i, n) {
	    			data_a.push({
						x: n.x,
						y: n.a
					});
	    			
	    			data_b.push({
						x: n.x,
						y: n.b
					});
	    			
	    			data_c.push({
						x: n.x,
						y: n.c
					});
	    	    });
	    	}
	    	show_many_areaspline_chart(data_a, data_b, data_c);
	    }, 
	    error:function(d) {
	    	alert(d.statusText);
	    }
	});
});

function show_many_areaspline_chart(data_a, data_b, data_c) {     
	Highcharts.getOptions().colors = "#007799,#434348,#90ed7d,#f7a35c,#8085e9,#f15c80,#e4d354,#2b908f,#f45b5b,#91e8e1".split(",");
    $('#many_areaspline_chart').highcharts({
        chart: {
            type: 'areaspline',
            events: {                                                           
				load: function() {
					var series_a = this.series[0];
					series_a.points[series_a.points.length-1].setState('hover');
					var series_b = this.series[1];
					series_b.points[series_b.points.length-1].setState('hover');
					var series_c = this.series[2];
					series_c.points[series_c.points.length-1].setState('hover');
					
					setInterval(function() {
						h++;
						var x = Date.UTC(y, m, d, h);
						var x_arr = new Array();
						x_arr.push(x);
						
						$.ajax({ 
						    type: 'post', 
						    dataType : "json", 
						    url: ROOF.Utils.projectName() + "/ems/bigscreen_show/dataShowAction/areaspline_chart_data.action",
						    data: {'x_json':$.toJSON(x_arr)},
						    success: function(d) {
						    	if(d.data != null) {
						    		$.each(d.data, function(i, n) {
						    			var x = n.x;
							    		var y = n.y;
							    		var a = n.a;
							    		var b = n.b;
							    		var c = n.c;
							    		
							    		series_a.points[series_a.points.length-1].setState();
							    		series_a.addPoint([x, a], true, true);
							    		series_a.points[series_a.points.length-1].setState('hover');
							    		
							    		series_b.addPoint([x, b], true, true);
							    		series_b.points[series_b.points.length-1].setState('hover');
							    		series_b.points[series_b.points.length-1].setState('hover');
							    		
							    		series_c.points[series_c.points.length-1].setState();
							    		series_c.addPoint([x, c], true, true);
							    		series_c.points[series_c.points.length-1].setState('hover');

										var now_time = getDateTime(new Date());
										var chart = $('#many_areaspline_chart').highcharts();
					    		        if (chart.lbl) {
					    		            chart.lbl.destroy();
					    		        }
					    		        var label = '时间:'+now_time+'<br>正常:'+a+'<br>故障:'+b+'<br>离线:'+c;
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
						    	    });
						    	}
						    }, 
						    error:function(d) {
						    	alert(d.statusText);
						    }
						});
						
						
					}, 3000);
				}                                                               
			} 
        },
        title: {
            text: '',
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
            	day: '%e. %b %H:%M',
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
            max:3,
            gridLineColor: ''
        },
        legend: { // 图例
        	enabled: false
        },
        tooltip: {
        	enabled: false
        },
        credits: { // 版权信息
            enabled: false
        },
        plotOptions: { // 数据点配置
            areaspline: {
//                fillColor: {
//                    linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},	
//                    stops: [
//                        [0, Highcharts.getOptions().colors[0]],
//                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
//                    ]
//                },
                marker: {
                	enabled: false,
                    radius: 4
                },
                lineWidth: 1,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
            },
            series: {
                lineColor: '#FF0000'
            }
        },
        series: [{ // 数据列
            type: 'areaspline',
            name: '故障',
            pointStart: Date.UTC(y, m, d, 0), // 开始时间：y年m月d日0时
            pointInterval: 3600 * 1000, // 每隔1小时
            data: (function() {
				return data_a;
			})(),
			fillColor: {
                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},	
                stops: [
                    [0, Highcharts.getOptions().colors[0]],
                    [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                ]
            },
            lineWidth: 0,
			marker: {
				symbol: 'circle',
                lineWidth: 1,
                lineColor: 'red',
                fillColor: 'white'
            }
        },{ // 数据列
            type: 'areaspline',
            name: '故障',
            pointStart: Date.UTC(y, m, d, 0), // 开始时间：y年m月d日0时
            pointInterval: 3600 * 1000, // 每隔1小时
            data: (function() {
				return data_b;
			})(),
			fillColor: {
                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},	
                stops: [
                    [0, Highcharts.getOptions().colors[3]],
                    [1, Highcharts.Color(Highcharts.getOptions().colors[3]).setOpacity(0).get('rgba')]
                ]
            },
            lineWidth: 0,
			marker: {
				symbol: 'circle',
                lineWidth: 1,
                lineColor: 'red',
                fillColor: 'white'
            }
        },{ // 数据列
            type: 'areaspline',
            name: '故障',
            pointStart: Date.UTC(y, m, d, 0), // 开始时间：y年m月d日0时
            pointInterval: 3600 * 1000, // 每隔1小时
            data: (function() {
				return data_c;
			})(),
			fillColor: {
                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},	
                stops: [
                    [0, Highcharts.getOptions().colors[2]],
                    [1, Highcharts.Color(Highcharts.getOptions().colors[2]).setOpacity(0).get('rgba')]
                ]
            },
            lineWidth: 0,
			marker: {
				symbol: 'circle',
                lineWidth: 1,
                lineColor: 'red',
                fillColor: 'white'
            }
        }]
    });
    
    // 图标div宽度和高度定义
    //$("#many_areaspline_chart").css("width", $(window).width() / 2 + 'px');
    //$("#many_areaspline_chart").css("height", $(window).height() / 2 + 'px');

 	// 图表本身的背景设置成透明
    var rect = $("#many_areaspline_chart").find("rect");
    rect.attr("fill", "transparent");
    //console.log(rect);

 	// body背景颜色设置成透明
    document.body.style.backgroundColor="transparent";
}

function getDateTime(now) {     
	var year=now.getFullYear(); //年
  	var month=now.getMonth()+1; //月
  	var date=now.getDate(); //日
  	var hour=now.getHours(); //时  
  	var minute=now.getMinutes(); //分     
  	var second=now.getSeconds(); //秒
  	return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
}