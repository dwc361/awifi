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
	    	show_many_spline_chart(data_a, data_b, data_c);
	    }, 
	    error:function(d) {
	    	alert(d.statusText);
	    }
	});
});

function show_many_spline_chart(data_a, data_b, data_c) {     
	$('#many_spline_div').highcharts({
    	chart: {
            zoomType: 'xy',
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
							    		
							    		series_b.points[series_b.points.length-1].setState();
							    		series_b.addPoint([x, b], true, true);
							    		series_b.points[series_b.points.length-1].setState('hover');
							    		
							    		series_c.points[series_c.points.length-1].setState();
							    		series_c.addPoint([x, c], true, true);
							    		series_c.points[series_c.points.length-1].setState('hover');
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
            text: ''
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
            },
            labels: {
            	style: {
            		color: 'black',
            		fontWeight: 'bold'
            	}
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            },
            lineWidth: 1,
            gridLineColor: '',
            minorTickInterval: 50,
            labels: {
            	style: {
            		color: 'black',
            		fontWeight: 'bold'
            	}
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
        	spline: {
            	marker: {
                	enabled: false,
                    radius: 4
                }
            }
        },
        legend: { // 图例
        	enabled: false
        },
        credits: { // 版权信息
            enabled: false
        },
        series: [{
            type: 'spline',
            name: 'Average',
            data: (function() {
				return data_a;
			})(),
            color: 'white',
            dashStyle: 'shortdot',
            marker: {
                lineWidth: 2,
                lineColor: 'red',
                fillColor: 'white'
            }
        },{
            type: 'spline',
            name: 'Average',
            data: (function() {
				return data_b;
			})(),
            color: 'red',
            dashStyle: 'shortdash',
            marker: {
                lineWidth: 2,
                lineColor: 'red',
                fillColor: 'white'
            }
        },{
            type: 'spline',
            name: 'Average',
            data: (function() {
				return data_c;
			})(),
            color: 'blue',
            marker: {
                lineWidth: 2,
                lineColor: 'red',
                fillColor: 'white'
            }
        }]
    });
    
	// 图表本身的背景设置成透明
    var rect = $("#many_spline_div").find("rect");
    rect.attr("fill", "transparent");

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