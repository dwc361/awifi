$(document).ready(function(){
	//左下角的设备类型分布的临时数据
	showDeviceDis(null)
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
            headerFormat: '<span style="color:white;font-size:16px;">{series.name}</span><br>',
            pointFormat: '<span style="color:{series.color};font-size:16px;">{series.name}</span>: <b style="color:white;">{point.y:.0f}%</b><br/>',
            backgroundColor: 'black',
            style: {
                    	fontFamily: '微软雅黑',
                    	fontSize: '16px'
                    }
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
                    format: '<span style="color:white;font-size:16px;">{series.name}</span>: <b style="color:white;">{point.y:.0f}%</b><br/>',
                    style: {
                    	fontFamily: '微软雅黑',
                    	fontSize: '16px',
                    	color:"#fff",
                    
                    	
                    }
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
            name: '<span style="color:white;font-size:16px;fontWeight:bold;">胖AP</span>',
            color:'#21c6a5',
            data: [30],
            
        },
         {
             name: '<span style="color:white;font-size:16px;">瘦AP</span>',
             color:'#65c7f7',
             data: [60]
         },
         {
             name: '<span style="color:white;font-size:16px;">AC</span>',
             color:'#096dc5',
             data: [80]
         },
         {
             name: '<span style="color:white;font-size:16px;">热点</span>',
             color:'#5434de',
             data: [40]
         }]                              
    }); 


	 setInterval(function () {
	        // 胖AP
	        var chart = $('#deviceDistributeChart').highcharts(),
	            point,
	            newVal,
	            inc;
	        if (chart) {
	            point = chart.series[0].points[0];
	            inc = Math.round((Math.random() - 0.5) * 50);
	            newVal = point.y + inc;
	            if (newVal < 0 || newVal > 99) {
	                newVal = point.y - inc;
	            }
	            point.update(newVal);
	        }
	        // 瘦AP
	        chart = $('#deviceDistributeChart').highcharts();
	        if (chart) {
	            point = chart.series[1].points[0];
	            inc = Math.random() - 0.5;
	            newVal = point.y + inc;
	            if (newVal < 0 || newVal > 5) {
	                newVal = point.y - inc;
	            }
	            point.update(newVal);
	        }
	        //AC
	         chart = $('#deviceDistributeChart').highcharts();
	        if (chart) {
	            point = chart.series[2].points[0];
	            inc = Math.random() - 0.5;
	            newVal = point.y + inc;
	            if (newVal < 0 || newVal > 5) {
	                newVal = point.y - inc;
	            }
	            point.update(newVal);
	        }
	        //热点
	        chart = $('#deviceDistributeChart').highcharts();
	        if (chart) {
	            point = chart.series[3].points[0];
	            inc = Math.random() - 0.5;
	            newVal = point.y + inc;
	            if (newVal < 0 || newVal > 5) {
	                newVal = point.y - inc;
	            }
	            point.update(newVal);
	        }
	    }, 1000);
}
