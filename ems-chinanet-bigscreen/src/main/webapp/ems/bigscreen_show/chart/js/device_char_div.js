$(document).ready(function(){
	//左下角的设备类型分布的临时数据
	showDeviceDis(null);
	deviceTopName();
});  
function showDeviceDis(dataArray){
	$('#deviceDistributeChart').highcharts({                                           
		chart: {
            type: 'bar',
            animation: false,
            backgroundColor: 'rgba(0,0,0,0)'
        },
        title: {
            text: null
        },
        xAxis: {
        	min:0,
        	lineColor: 'rgba(0,0,0,0)',//设置X轴颜色
            labels: {
                formatter: function () {
                    return '';
                }
            },
            title: {
                text: null
            }
        },
        yAxis: {
        	min:0,
        	max: 100,
        	gridLineColor: 'rgba(0,0,0,0)',//设置y轴颜色
            labels: {
                formatter: function () {
                    return '';
                }
            },
            title: {
                text: null
            }
        },
        tooltip: {
            headerFormat: '<span style="color:white;">{series.name}</span><br>',
            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b style="color:white;">{point.y:.0f}%</b><br/>',
            backgroundColor: 'black'
        },
        plotOptions: {
        	bar: { 
                dataLabels: {                                              
                    enabled: true                                          
                }                                                          
            },
            series:{
            	pointPadding: 0,
            	groupPadding: 0,
                borderWidth: 0,
                shadow: false,
                pointWidth:12, //柱子宽度
                dataLabels: {
                    enabled: true,
                    format: '<span style="color:white;">{series.name}</span>: <b style="color:white;">{point.y:.1f}%</b><br/>'
                }
            }
        },
        legend: {
            enabled: false
        },
        credits: {
            enabled: false
        },
        series: [{
            name: '<span style="color:white;font-family:simsun;">胖AP</span>',
            color:'#367bdb',
            data: [10]
        },
         {
             name: '<span style="color:white;font-family:simsun;">瘦AP</span>',
             color:'#d9a831',
             data: [20]
         },
         {
             name: '<span style="color:white;font-family:simsun;">AC</span>',
             color:'#42aeb8',
             data: [20]
         },
         {
             name: '<span style="color:white;font-family:simsun;">热点</span>',
             color:'#df5964',
             data: [20]
         }]                              
    }); 
}
function deviceTopName(){
	$('#topname').highcharts({                                           
		chart: {
            type: 'bar',
            animation: false,
            backgroundColor: 'rgba(0,0,0,0)',
            events: {                                                           
				load: function() {            
					setInterval(function() {
						var a = Math.floor(Math.random()*10000+1);
						var b = Math.floor(Math.random()*10000+1);
						var c = Math.floor(Math.random()*10000+1);
						var d = Math.floor(Math.random()*10000+1);
						var chart = $('#topname').highcharts();
	    		        if (chart.lbl) {
	    		            chart.lbl.destroy();
	    		        }
	    		        var label = '胖AP:'+a+'<br>瘦AP:'+b+'<br>AC:'+c+'<br>热点:'+d;
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
            text: null
        },
        xAxis: {
        	min:0,
        	lineColor: 'rgba(0,0,0,0)',//设置X轴颜色
            labels: {
                formatter: function () {
                    return '';
                }
            },
            title: {
                text: null
            }
        },
        yAxis: {
        	min:0,
        	max: 100,
        	gridLineColor: 'rgba(0,0,0,0)',//设置y轴颜色
            labels: {
                formatter: function () {
                    return '';
                }
            },
            title: {
                text: null
            }
        },
        tooltip: {
        	enabled:false
        },
        plotOptions: {
        	bar: { 
                dataLabels: {                                              
                    enabled: true                                          
                }                                                          
            },
            series:{
            	pointPadding: 0,
            	groupPadding: 0,
                borderWidth: 0,
                shadow: false,
                pointWidth:12, //柱子宽度
                dataLabels: {
                    enabled: true,
                    format: '<span style="font-size:0px;">胖AP</span>'
                }
            }
        },
        legend: {
            enabled: false
        },
        credits: {
            enabled: false
        },
        series: [{
            name: '<span style="font-size:0px;">胖AP</span>',
            color:'rgba(0,0,0,0)',
            data: [10]
        },
         {
             name: '<span style="font-size:0px;">瘦AP</span>',
             color:'rgba(0,0,0,0)',
             data: [20]
         },
         {
             name: '<span style="font-size:0px;">AC</span>',
             color:'rgba(0,0,0,0)',
             data: [20]
         },
         {
             name: '<span style="font-size:0px;">热点</span>',
             color:'rgba(0,0,0,0)',
             data: [20]
         }]                              
    }); 
}
