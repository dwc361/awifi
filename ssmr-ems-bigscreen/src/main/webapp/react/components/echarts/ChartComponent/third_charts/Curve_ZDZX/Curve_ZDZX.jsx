import React from 'react';
import ReactEcharts from '../../../src/echarts-for-react';

export default class Curve_ZDZX_ChartComponent extends React.Component({
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
            tooltip: {
                trigger: 'axis',
                position: function (pt) {
                    return [pt[0], '10%'];
                }
            },
            title: {
                left: 'right',
                text: '终端数',
            },
            legend: {
                top: 'bottom',
                data:['意向']
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: date
            },
            yAxis: {
                type: 'value',
                boundaryGap: [0, '100%']
            },
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 10
            }],
            series: [
                {
                    name:'当前时段终端在线数量',
                    type:'line',
                    smooth:true,
                    symbol: 'none',
                    sampling: 'average',
                    itemStyle: {
                        normal: {
                            color: 'none'
                        }
                    },
                    areaStyle: {
                        normal: {
                            color: '#007797'
                        }

                    },
                    data: data
                }
            ]
        };
        return option;
    },
    render: function() {
        return (
            <div className="left_top">
                <div className="topH">
                    <h1>[ 今天终端在线趋势图 ]</h1>
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
