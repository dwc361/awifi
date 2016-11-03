import React from 'react';
import ReactDOM from 'react-dom';
import ReactMixin from 'react-mixin';
import Reflux from 'reflux'

import ReactEcharts from '../../src/echarts-for-react';
import store from '../../../../stores/second-store';
import actions from '../../../../actions/second-actions';

var province = [[]];
var deviceNum = [[]];
const Funnel_SBPM_ChartComponent = React.createClass({
    propTypes: {
    },
    timeTicket: null,
    getInitialState: function() {
        return {option: this.getOption(), deviceNum:[], province:[]};
    },
    componentDidMount: function() {
        actions.getFunnel_sbpm_data();
    },
    getOption: function() {
        const option = {
            //  color: [
            //      '#21c6a5', '#65c7f7', '#096dc5'
            //  ],
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'value',
                boundaryGap: [0, 0.01],
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
                    show: false,
                    interval: 'auto',
                    lineStyle: {
                        color: ['#ccc'],
                        width: 0,
                        type: 'solid',
                    },
                },
            },
            yAxis: {
                type: 'category',
                axisLabel: {
                    //          interval: 0,
                    //          rotate: 45,
                    //          margin: 2,
                    textStyle: {
                        color: "#fff",
                        fontSize: 12
                    }
                },
                data: province[0],
                nameTextStyle: {
                    color: '#fff',
                    fontStyle: 'normal',
                    fontFamily: 'sans-serif',
                    fontSize: 16
                },
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
            },
            series: [{
                type: 'bar',
                data: deviceNum[0],
                itemStyle: {
                    normal: {
                        color: function(params) {
                            var colorList = ['#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5', '#21c6a5', '#65c7f7', '#096dc5'];
                            return colorList[params.dataIndex];
                        }
                    }
                }
            }]
        };

        return option;
    },
    showToolTip: function(echartObj) {
        let option = this.state.option;
        let rank_index = 0;
        let currentIndex = -1;
        setInterval(function() {
            let dataLen = option.series[0].data.length;
            currentIndex++;
            if(currentIndex == dataLen) {
                currentIndex = -1;
                rank_index = (rank_index + 1) % province.length;
                
                // 重新加载图表内容
                option.yAxis.data = province[rank_index];
                option.series[0].data = deviceNum[rank_index];
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
    render: function() {
        if(this.state.province.length > 0) {
            province = this.state.province;
            deviceNum = this.state.deviceNum;
        }
        return (
            <div className="left col-md-12 col-lg-12 col-sm-12">
                <div className="topH">
                    <h1>[ 全省设备排名 ]</h1>
                </div>
                <div className="Echart">
                    <div id="funnel_SBPM" >
                        <ReactEcharts ref='echarts_react' 
                        onChartReady={this.showToolTip} 
                        option={this.state.option} 
                        style={{height: '100%', width: '100%'}}  />
                    </div>
                </div>
            </div>
        );
    }
});

export default Funnel_SBPM_ChartComponent;
// ES6 mixin写法，通过mixin将store的与组件连接，功能是监听store带来的state变化并刷新到this.state
ReactMixin.onClass(Funnel_SBPM_ChartComponent, Reflux.connect(store));