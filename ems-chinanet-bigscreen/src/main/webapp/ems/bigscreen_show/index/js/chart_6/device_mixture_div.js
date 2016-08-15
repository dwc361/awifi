
$(document).ready(function(){
	EcharTest();
});
function EcharTest(){
	var stateChart=echarts.init(document.getElementById('state'),deviceTheme);
	opt_state = {
		    title: {
		        text: '',
		        subtext: ''
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    dataZoom: {
		        show: false,
		        start: 0,
		        end: 100
		    },
		    xAxis: [
		        {
		            type: 'time',
		            splitNumber:12,
		            splitLine: {           // 分隔线
		                show: false
		            },
		            data: (function (){
		                var now = new Date();
		                var res = [];
		                var len = 10;
		                while (len--) {
		                    res.unshift(now.toLocaleTimeString().replace(/^\D*/,''));
		                    now = new Date(now - 2000);
		                }
		                return res;
		            })(),
		            textStyle: {
	                	color: '#fff',
	                	fontFamily: '微软雅黑, Arial, Verdana, sans-serif',
	                	fontSize: '19px',
            	    }
		        },
		        {
		            type: 'category',
		            boundaryGap: true,
		            data: [],
		            splitLine: {           // 分隔线
		                show: false
		            }
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            scale: true,
		            name: '',
		            max: 1000,
		            min: 0,
		            boundaryGap: [0.2, 0.2]
		        },
		        {
		            type: 'value',
		            show: false,
		            name: '',
		            max: 1200,
		            min: 0,
		            boundaryGap: [0.2, 0.2],
		            splitLine: {           // 分隔线
		                show: false
		            }
		        }
		    ],
		    series: [
		        {
		            name:'正常',
		            type:'bar',
		            xAxisIndex: 1,
		            yAxisIndex: 1,
		            data:(function (){
		                var res = [];
		                var len = 10;
		                while (len--) {
		                    res.push(Math.round(Math.random() * 1000));
		                }
		                return res;
		            })(),
		            itemStyle:{normal:{
	                    color:'#42aeb8'
	                }}
		        },
		        {
		            name:'故障',
		            type:'bar',
		            xAxisIndex: 1,
		            yAxisIndex: 1,
		            data:(function (){
		                var res = [];
		                var len = 10;
		                while (len--) {
		                    res.push(Math.round(Math.random() * 1000));
		                }
		                return res;
		            })(),
		            itemStyle:{normal:{
	                    color:'#367bdb'
	                }}
		        },
		        {
		            name:'离线',
		            type:'bar',
		            xAxisIndex: 1,
		            yAxisIndex: 1,
		            data:(function (){
		                var res = [];
		                var len = 10;
		                while (len--) {
		                    res.push(Math.round(Math.random() * 1000));
		                }
		                return res;
		            })(),
		            itemStyle:{normal:{
	                    color:'#d9a831'
	                }}
		        },
		        {
		                name: '设备状态百分比',
		                type: 'pie',
		                center: ['75%', '35%'],
		                radius: '20%',
		                tooltip : {
		                    trigger: 'item',
		                    formatter: '{a} <br/>{b} : {d}%'
		                },
		                label: {
		                	normal: {
			                	formatter: function(params){
			                        var nameList=['正常','故障','离线'];
			                        return nameList[params.dataIndex];
			                    },
			                	textStyle: {
				                	color: '#fff',
				                	fontFamily: '微软雅黑, Arial, Verdana, sans-serif',
				                	fontSize: '19rem',
		                	    }
		                    }
		                },
		                itemStyle:{normal:{
		                	label: {show:true},
		                	labelLine : {
		                		show:true,
		                        length : 20
		                    },
		                    color:function(params){
		                        var colorList=['#42aeb8','#367bdb','#d9a831'];
		                        return colorList[params.dataIndex];
		                    }
		                }},
		                data: [
			                    {value:100, name:'正常'},
			                    {value:100, name:'故障'},
			                    {value:100, name:'离线'}
			            ],
		        }
		    ]
		};
		var app={};
		clearInterval(app.timeTicket);
		app.timeTicket = setInterval(function (){
		    axisData = (new Date()).toLocaleTimeString().replace(/^\D*/,'');

		    var data0 = opt_state.series[0].data;
		    var data1 = opt_state.series[1].data;
		    var data2 = opt_state.series[2].data;
		    var data3 = opt_state.series[3].data;
		    data0.shift();
		    data0.push(Math.round(Math.random() * 1000));
		    data1.shift();
		    data1.push(Math.round(Math.random() * 1000));
		    data2.shift();
		    data2.push(Math.round(Math.random() * 1000));
		    data3.shift();
		    data3.push(Math.round(Math.random() * 1000));
		    
		    opt_state.xAxis[0].data.shift();
		    opt_state.xAxis[0].data.push(axisData);

		    stateChart.setOption(opt_state);
		}, 1000);
}
var deviceTheme = {
        // 全图默认背景
        backgroundColor: 'rgba(0,0,0,0)',
        // 类目轴
        categoryAxis: {
            axisLine: {            // 坐标轴线
                show: false
            },
            axisTick: {            // 坐标轴小标记
                show: false
            },
            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: '#fff'
                }
            },
            splitLine: {           // 分隔线
                show: false
            }
        },

        // 数值型坐标轴默认参数
        valueAxis: {
    	 	axisLine: {            // 坐标轴线
                show: false
            },
            axisTick: {            // 坐标轴小标记
                show: false
            },
            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: '#fff'
                }
            },
            splitLine: {           // 分隔线
                show: false
            }
        },
        textStyle: {
            fontFamily: '微软雅黑, Arial, Verdana, sans-serif'
        }
    };