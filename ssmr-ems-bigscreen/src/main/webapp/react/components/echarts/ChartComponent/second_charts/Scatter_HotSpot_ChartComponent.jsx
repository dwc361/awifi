import React from 'react';
import ReactDOM from 'react-dom';
import ReactMixin from 'react-mixin';
import Reflux from 'reflux'

import ReactEcharts from '../../src/echarts-for-react';
import store from '../../../../stores/second-store';
import actions from '../../../../actions/second-actions';

// 指定图表的配置项和数据
var typeName = [[]];
var hotareaNum = [[]];
const Scatter_HotSpot_ChartComponent = React.createClass({
    propTypes: {
    },
    timeTicket: null,
    getInitialState: function() {
        return {option: this.getOption(), typeName:[], hotareaNum:[]};
    },
    showToolTip: function(echartObj) {
        let option = this.state.option;
        let hotspot_index = 0;
        let currentIndex = -1;
        setInterval(function() {
            let dataLen = option.series[0].data.length;
            currentIndex++;
            if(currentIndex == dataLen) {
                currentIndex = -1;
                hotspot_index = (hotspot_index + 1) % typeName.length;
                
                // 重新加载图表内容
                option.xAxis.data = typeName[hotspot_index];
                option.series[0].data = hotareaNum[hotspot_index];
                echartObj.setOption(option, true);
                
                // 取消之前高亮的图形
                echartObj.dispatchAction({
                    type: 'downplay',
                    seriesIndex: 0,
                    dataIndex: currentIndex
                });
                
                // 隐藏 tooltip
                echartObj.dispatchAction({
                    type: 'hideTip',
                    seriesIndex: 0,
                    dataIndex: currentIndex
                });
            } else {
                // 高亮当前图形
                echartObj.dispatchAction({
                    type: 'highlight',
                    seriesIndex: 0,
                    dataIndex: currentIndex
                });
                
                // 显示 tooltip
                echartObj.dispatchAction({
                    type: 'showTip',
                    seriesIndex: 0,
                    dataIndex: currentIndex
                });
            }
        }, 2000);
    },
    componentDidMount: function() {
        actions.getScatter_hotspot_data();
    },
    getOption: function() {
        const option = {
            tooltip: {
                itemGap: '  0',
                axisPointer: {
                    type: 'shadow'
                }
            },
            //  backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
            //      offset: 0,
            //      color: '#333'
            //  }, {
            //      offset: 1,
            //      color: '#333'
            //  }]),
            xAxis: {
                type : 'category',
                data : typeName[0],
                axisLine: {
                    show: true,
                    onZero: true,
                    lineStyle: {
                        color: '#aaa',
                        width: 1,
                        type: 'solid',
                        opacity: 1,
                    },
                },
                splitLine: {
                    lineStyle: {
                        type: 'dashed'
                    }
                }
            },
            yAxis: {
                axisLine: {
                    show: true,
                    onZero: true,
                    lineStyle: {
                        color: '#aaa',
                        width: 1,
                        type: 'solid',
                        opacity: 1,
                    },
                },
                splitLine: {
                    lineStyle: {
                        type: 'dashed'
                    }
                },
                scale: true
            },
            series: [{
                name: '',
                data: hotareaNum[0],
                type: 'scatter',
                symbolSize: function(data) {
                    return 10 + Math.pow(data, 1/2);
                },
                label: {
                    emphasis: {
                        show: true,
                        formatter: function(param) {
                            return param.data;
                        },
                        position: 'top'
                    }
                },
                itemStyle: {
                    normal: {
                        shadowBlur: 10,
                        shadowColor: 'rgba(120, 36, 50, 0.5)',
                        shadowOffsetY: 5,
                        color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                            offset: 0,
                            color: 'rgb(129, 227, 238)'
                        }, {
                            offset: 1,
                            color: 'rgb(25, 183, 207)'
                        }])
                    }
                }
            }]
        };

        return option;
    },
    render: function() {
        if(this.state.typeName.length > 0) {
            typeName = this.state.typeName;
            hotareaNum = this.state.hotareaNum;
        }
        return (
            <div className="right col-md-12 col-lg-12 col-sm-12">
                <div className="topH">
                    <h1>[ 爱wifi热点类型分布 ]</h1>
                </div>
                <div className="Echart" >
                    <div id="scatter_HotSpot">
                        <ReactEcharts ref='echarts_react' 
                        onChartReady={this.showToolTip} 
                        option={this.state.option} 
                        style={{height: '100%', width: '100%'}} />
                    </div>
                </div>
            </div>
        );
    }
});

export default Scatter_HotSpot_ChartComponent;

// ES6 mixin写法，通过mixin将store的与组件连接，功能是监听store带来的state变化并刷新到this.state
ReactMixin.onClass(Scatter_HotSpot_ChartComponent, Reflux.connect(store));