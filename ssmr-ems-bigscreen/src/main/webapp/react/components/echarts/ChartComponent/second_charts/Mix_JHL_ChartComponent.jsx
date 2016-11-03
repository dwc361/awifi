import React from 'react';
import ReactDOM from 'react-dom';
import ReactMixin from 'react-mixin';
import Reflux from 'reflux'

import ReactEcharts from '../../src/echarts-for-react';
import store from '../../../../stores/second-store';
import actions from '../../../../actions/second-actions';

const Mix_JHL_ChartComponent = React.createClass({
    propTypes: {
    },
    timeTicket: null,
    getInitialState: function() {
        return {activateNum:[], activatePer:[], createTime:[], option:this.getOption([], [], [])};
    },
    componentDidMount: function() {
        actions.getMix_jhl_data();
    },
    getOption: function(NumList, perList, timeList) {
        const option = {
            //backgroundColor:'#333',
            color: [
                '#21c6a5', '#096dc5'
            ],
            tooltip: {
                trigger: 'axis',
                formatter: function (params) {
                    var show_content = params[0].name + '<br/>';
                    for(var i = 0; i < params.length; i++) {
                        show_content += "<span style='display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:"+params[i].color+"'></span>"
                            +params[i].seriesName + ' : ' + params[i].value;
                        if(params[i].seriesName == "激活率") {
                            show_content += ' %<br/>';
                        } else {
                            show_content += '<br/>';
                        }
                    }
                    return show_content;
               }
            },
            xAxis: [{
                type: 'category',
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
                data: timeList
            }],
            yAxis: [{
                type: 'value',
                min: 0,
                //max: 20,
                interval: 1,
                show: false,
                axisLabel: {
                    formatter: '{value} %',
                    show: false
                },
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

            }, {
                type: 'value',
                min: 0,
                //max: 2,
                interval: 1000,
                show: false,
                axisLabel: {
                    show: false
                }
            }],
            series: [{
                name: '激活率',
                type: 'bar',
                data: perList
            }, {
                name: '激活量',
                type: 'line',
                yAxisIndex: 1,
                data: NumList
            }]
        };

        return option;
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
    render: function() {
        if(this.state.createTime.length > 0) {
            this.state.option.xAxis[0].data = this.state.createTime;
            this.state.option.series[0].data = this.state.activatePer;
            this.state.option.series[1].data = this.state.activateNum;
        }
        return (
            <div className="right col-md-12 col-lg-12 col-sm-12">
                <div className="topH">
                    <h1>[ 胖ap激活率统计 ]</h1>
                </div>
                <div className="Hchart">
                    <div id="mix_JHL">
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

export default Mix_JHL_ChartComponent;

// ES6 mixin写法，通过mixin将store的与组件连接，功能是监听store带来的state变化并刷新到this.state
ReactMixin.onClass(Mix_JHL_ChartComponent, Reflux.connect(store));