
$(document).ready(function(){
	EcharTest_2();
});

var now = new Date(); //定义一个时间对象
//var y = now.getFullYear(); //获取完整的年份(4位,1970-????)
var m = now.getMonth()+1; //获取当前月份(0-11,0代表1月)
var d = now.getDate(); //获取当前日(1-31) 
var dataList = (function (){
	var datas = [];
	for (var i = d-5; i <= d; i++) {
		datas.push(m+"/"+i);
	}
    return datas;
})();

function EcharTest_2(){
	var stateChart_2=echarts.init(document.getElementById('state_2'),deviceTheme);
	opt_state_2 = {
		    title: {
		        text: '',
		        subtext: ''
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    xAxis: [
		        {
		        	type: 'category',
		        	boundaryGap: true,
		    		data: dataList,
		            /*axisLabel:{            //坐标轴数据样式
                        interval:10,
	                    rotate:45,
	                    margin:2,
	                    textStyle:{
	                    	color: '#fff',
		                	fontFamily: '微软雅黑, Arial, Verdana, sans-serif',
		                	fontSize: '19px',
	                    }
	                },*/
	                axisLine: {
		    			show: true,
		    			onZero: true,
		    			boundaryGap: true,
		    			lineStyle: {
		    				color: '#aaa',
		    				width: 1,
		    				type: 'solid',
		    				opacity: 1
		    			}
		    		}
		        },
		        {
		            type: 'category',
		            boundaryGap: true,
		            data: []
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            scale: true,
		            name: '',
		            max: 1000,
		            min: 0,
		            boundaryGap: [0.2, 0.2],
		            axisLine: {
		    			show: true,
		    			onZero: true,
		    			lineStyle: {
		    				color: '#aaa',
		    				width: 1,
		    				type: 'solid',
		    				opacity: 1,
		    			}
		    		}
		        },
		        {
		            type: 'value',
		            show: false,
		            name: '',
		            max: 1000,
		            min: 0,
		            boundaryGap: [0.2, 0.2]
		        }
		    ],
		    series: [
		        {
		            name:'正常',
		            type:'bar',
		            data:(function (){
		                var res = [];
		                var len = 6;
		                while (len--) {
		                    res.push(Math.round(Math.random() * 1000));
		                }
		                return res;
		            })(),
		            itemStyle:{normal:{
	                    color:'#21c6a5'
	                }}
		        },
		        {
		            name:'故障',
		            type:'bar',
		            data:(function (){
		                var res = [];
		                var len = 6;
		                while (len--) {
		                    res.push(Math.round(Math.random() * 1000));
		                }
		                return res;
		            })(),
		            itemStyle:{normal:{
	                    color:'#65c7f7'
	                }}
		        },
		        {
		            name:'离线',
		            type:'bar',
		            data:(function (){
		                var res = [];
		                var len = 6;
		                while (len--) {
		                    res.push(Math.round(Math.random() * 1000));
		                }
		                return res;
		            })(),
		            itemStyle:{normal:{
	                    color:'#096dc5'
	                }}
		        },
		        {
	                name: '设备状态百分比',
	                type: 'pie',
	                center: ['65%', '35%'],
	                radius: '18%',
	                tooltip : {
	                    trigger: 'item',
	                    formatter: '{a} <br/>{b} : {c} ({d}%)'
	                },
	                label: {
	                	normal: {
		                	formatter: '{b} : {d}%',
		                	textStyle: {
			                	color: '#fff',
			                	fontFamily: '微软雅黑, Arial, Verdana, sans-serif',
			                	fontSize: 19,
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
	                        var colorList=['#21c6a5', '#65c7f7', '#096dc5'];
	                        return colorList[params.dataIndex];
	                    }
	                }},
	                data: [
		                    {value:100, name:'正常'},
		                    {value:100, name:'故障'},
		                    {value:100, name:'离线'}
		            ]
		        }
		    ]
		};
		var app_state_2={};
		app_state_2.currentIndex = -1;
		clearInterval(app_state_2.timeTicket);
		app_state_2.timeTicket = setInterval(function (){
		    var dataLen = opt_state_2.series[0].data.length;
		    // 取消之前高亮的图形
		    stateChart_2.dispatchAction({
		        type: 'downplay',
		        seriesIndex: 0,
		        dataIndex: app_state_2.currentIndex
		    });
		    app_state_2.currentIndex = (app_state_2.currentIndex + 1) % dataLen;
		    // 高亮当前图形
		    stateChart_2.dispatchAction({
		        type: 'highlight',
		        seriesIndex: 0,
		        dataIndex: app_state_2.currentIndex
		    });
		    // 显示 tooltip
		    stateChart_2.dispatchAction({
		        type: 'showTip',
		        seriesIndex: 0,
		        dataIndex: app_state_2.currentIndex
		    });
		    opt_state_2.series[3].data[0].value = opt_state_2.series[0].data[app_state_2.currentIndex];
		    opt_state_2.series[3].data[1].value = opt_state_2.series[1].data[app_state_2.currentIndex];
		    opt_state_2.series[3].data[2].value = opt_state_2.series[2].data[app_state_2.currentIndex];
		    stateChart_2.setOption(opt_state_2,true);
		}, 5000);
		stateChart_2.setOption(opt_state_2);
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