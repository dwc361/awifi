import React from 'react';
import ReactEcharts from '../../../src/echarts-for-react';
import Table_YDZD from './table-YDZD.jsx';


const Pie_YDZD_ChartComponent = React.createClass({
    propTypes: {
    },
    timeTicket: null,
    currentIndex: -1,
    getInitialState: function() {
        return {option: this.getOption()};
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
        }, 3000);
    },
    fetchNewDate: function() {
    },
    componentDidMount: function() {
    },
    componentWillUnmount: function() {
    },
    getOption: function() {
        const option = {
            color: [
                '#005580', '#9ff0ff', '#26c4dd', '#007797','#0187e2', '#007797'
            ],
            //  backgroundColor: '#2c343c',
            tooltip: {
                trigger: 'item',
                textStyle: {
                    color: '#fff',
                    fontSize: 20
                },
                axisPointer: {
                    lineStyle: {
                        color: '#fff',
                        width: 2
                    }
                },
                formatter: "{b} <br/>{c}"
            },
            series: [{
                name: '类型分布',
                type: 'pie',
                label: {
                    normal: {
                        label : {
                            position : 'inner',
                            formatter : function (params) {
                                return (params.percent - 0).toFixed(0) + '%'
                            }
                        },
                        show: false,
                        position: 'inside',
                        textStyle: {
                            color: '#fff',
                            fontStyle: 'normal',
                            fontWeight: 'normal',
                            fontFamily: 'sans-serif',
                            fontSize: 16
                        },
                    },
                },
                radius: '80%',
                center: ['50%', '50%'],
                data: [{
                    value: 39.92,
                    name: 'apple'
                }, {
                    value: 22.8,
                    name: '未知'
                }, {
                    value: 5.54,
                    name: 'Huaewi'
                }, {
                    value: 5.21,
                    name: 'Sangxing'
                }, {
                    value: 4.66,
                    name: 'Xiaomi'
                }, {
                    value: 21.87,
                    name: '其他'
                }],
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
        return (
            <div className="chart_block">
                <div className="topH">
                    <hr/>
                    <h1>移动终端</h1>
                </div>
                <div className="Echart">
                    <div className="data-YDZD">
                        <Table_YDZD/>
                    </div>
                    <div id='pie_YDZD' className="chart_part">
                        <ReactEcharts ref='echarts_react'
                                      onChartReady={this.showToolTip}
                                      option={this.state.option}/>
                    </div>
                </div>
            </div>
        );
    }
});

export default Pie_YDZD_ChartComponent;
