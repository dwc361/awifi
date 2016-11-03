import React from 'react';
import ReactDOM from 'react-dom';
import ReactMixin from 'react-mixin';
import Reflux from 'reflux'

import ReactEcharts from '../../src/echarts-for-react';
import store from '../../../../stores/second-store';
import actions from '../../../../actions/second-actions';

const Line_YHRZ_ChartComponent = React.createClass({
    propTypes: {
    },
    timeTicket: null,
    currentIndex: -1,
    getInitialState: function() {
        return {createTime:[], successNum:[], option:this.getOption([], [])};
    },
    showToolTip: function(echartObj) {
        let option = this.state.option;
        let currentIndex = -1;
        setInterval(function() {
            let dataLen = option.series[0].data.length;
            // 取消之前高亮的图形
            echartObj.dispatchAction({
                type: 'downplay',
                seriesIndex: 0,
                dataIndex: currentIndex
            });
            currentIndex = (currentIndex + 1) % dataLen;
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
        }, 2000);
    },
    componentDidMount: function() {
        actions.getLine_yhrz_data();
    },
    getOption: function(successNum,createTime) {
        const option = {
            color: [
                '#21c6a5', '#65c7f7', '#096dc5'
            ],
            tooltip: {
                trigger: 'axis',
                textStyle: {
                    color: '#fff',
                    fontSize: 18
                },
                axisPointer: {
                    lineStyle: {
                        color: '#fff',
                        width: 1
                    }
                }
            },
            calculable: true,
            xAxis: [{
                axisLine: {
                    show: false,
                    onZero: true,
                    lineStyle: {
                        color: '#aaa',
                        width: 1,
                        type: 'solid',
                        opacity: 1,
                    },
                },
                type: 'category',
                boundaryGap: false,
                data: createTime
            }],
            yAxis: [{
                splitNumber: 0,
                axisLabel: {
                    show: false,
                    inside: 'true'
                    // formatter: '{value} ml'
                },
                type: 'value',
                axisLine: {
                    show: false,
                    onZero: true,
                    lineStyle: {
                        color: '#aaa',
                        width: 1,
                        type: 'solid',
                        opacity: 1,
                    },
                },
            }],
            series: [{
                name: '成功认证',
                type: 'line',
                smooth: true,
                itemStyle: {
                    normal: {
                        areaStyle: {
                            type: 'default'
                        }
                    }
                },
                data: successNum
            }
               /* {
                name: '胖AP',
                type: 'line',
                smooth: true,
                itemStyle: {
                    normal: {
                        areaStyle: {ren
                            type: 'default'
                        }
                    }
                },
                data: [30, 182, 434, 791, 390, 30, 10]
            }, {
                name: '三合一',
                type: 'line',
                smooth: true,
                itemStyle: {
                    normal: {
                        areaStyle: {
                            type: 'default'
                        }
                    }
                },
                data: [1320, 1132, 601, 234, 120, 90, 20]
            }*/
            ]
        };

        return option;
    },
    render: function() {
        if(this.state.successNum.length > 0) {
            this.state.option.series[0].data = this.state.successNum;
            this.state.option.xAxis[0].data = this.state.createTime;
        }
        return (
            <div className="right col-md-12 col-lg-12 col-sm-12">
                <div className="topH">
                    <h1>[ 用户认证状态 ]</h1>
                </div>
                <div className="Echart">
                    <div id="line_YHRZ">
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

export default Line_YHRZ_ChartComponent;
ReactMixin.onClass(Line_YHRZ_ChartComponent, Reflux.connect(store));