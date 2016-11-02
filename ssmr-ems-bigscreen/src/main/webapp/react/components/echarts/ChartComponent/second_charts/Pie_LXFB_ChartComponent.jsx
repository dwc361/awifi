import React from 'react';
import ReactDOM from 'react-dom';
import ReactMixin from 'react-mixin';
import Reflux from 'reflux'

import ReactEcharts from '../../src/echarts-for-react';
import store from '../../../../stores/second-store';
import actions from '../../../../actions/second-actions';

const Pie_LXFB_ChartComponent = React.createClass({
    propTypes: {
    },
    timeTicket: null,
    currentIndex: -1,
    getInitialState: function() {
        return {option: this.getOption([]), dataList:[]};
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
    fetchNewDate: function() {
    },
    componentDidMount: function() {
        actions.getPie_lxfb_data();
    },
    getOption: function(res) {
        const option = {
            color: [
                '#21c6a5', '#65c7f7', '#096dc5', '#4246ef'
            ],
            //  backgroundColor: '#2c343c',
            tooltip: {
                trigger: 'item',
                textStyle: {
                    color: '#fff',
                    fontSize: 18
                },
                axisPointer: {
                    lineStyle: {
                        color: '#fff',
                        width: 1
                    }
                },
                formatter: "{b} : {c}"
            },
            series: [{
                name: '类型分布',
                type: 'pie',
                label: {
                    normal: {
                        show: true,
                        position: 'outside',
                        textStyle: {
                            color: '#fff',
                            fontStyle: 'normal',
                            fontWeight: 'normal',
                            fontFamily: 'sans-serif',
                            fontSize: 18
                        },
                    },
                },
                radius: '65%',
                center: ['50%', '60%'],
                data: res,
                /*data: [{
                    value: 335,
                    name: '胖ap'
                }, {
                    value: 310,
                    name: '光猫'
                }, {
                    value: 234,
                    name: '瘦AP'
                }, {
                    value: 135,
                    name: '融合终端'
                }],*/
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }]
        };
        return option;
    },
    render: function() {
        this.state.option.series[0].data = this.state.dataList;
        return (
            <div className="left col-md-12 col-lg-12 col-sm-12">
                <div className="topH">
                    <h1>[ 设备类型分布 ]</h1>
                </div>
                <div className="Echart">
                    <div id='pie_LXFB'>
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

export default Pie_LXFB_ChartComponent;

// ES6 mixin写法，通过mixin将store的与组件连接，功能是监听store带来的state变化并刷新到this.state
ReactMixin.onClass(Pie_LXFB_ChartComponent, Reflux.connect(store));