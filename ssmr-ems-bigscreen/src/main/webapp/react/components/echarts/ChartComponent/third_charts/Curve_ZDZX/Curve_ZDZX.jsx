import React from 'react';
import ReactEcharts from '../../../src/echarts-for-react';

const Curve_ZDZX_ChartComponent = React.createClass({
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
            //backgroundColor:"#26333b",
            color:['#007797'],
            title: {
                text: '堆叠区域图'
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:'搜索引擎'
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : ['周一','周二','周三','周四','周五','周六','周日'],
                    axisLine: {
                        show: true,
                        onZero: true,
                        lineStyle: {
                            color:'#eee'
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    axisLine: {
                        show: true,
                        onZero: true,
                        lineStyle: {
                            color:'#eee'
                        }
                    }
                }
            ],
            series : [
                {
                    name:'搜索引擎',
                    type:'line',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    },
                    areaStyle: {normal: {}},
                    data:[820, 932, 901, 934, 1290, 1330, 1320]
                }
            ]
        };
        return option;
    },
    render: function() {
        return (
            <div className="chart_block">
                <div className="topH">
                    <hr/>
                    <h1>今天终端在线趋势图</h1>
                </div>
                <div className="Echart">
                    <div id="newsShow">
                        <div className="clo-md-7"></div>
                        <div className="clo-md-5"></div>
                    </div>
                    <div id='curve_ZDZX'>
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
export default Curve_ZDZX_ChartComponent;

