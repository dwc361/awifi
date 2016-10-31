import React from 'react';
import ReactEcharts from '../../../src/echarts-for-react';

const Bar_YYS_ChartComponent = React.createClass ({
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
                '#005580','#0187e2',  '#007797', '#26c4dd', '#9ff0ff'
            ],
            tooltip : {
                trigger: 'axis'
            },
            toolbox: {
                show : false,
                y: 'bottom',
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },

            xAxis : [
                {
                    type : 'category',
                    splitLine : {show : false},
                    axisLine: {
                        show: true,
                        onZero: true,
                        lineStyle: {
                            color:'#eee'
                        }
                    },
                    data : ['移动', '联通', '电信', '国内其他', '国外']
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    position: 'left',
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
                    type:'bar',
                    itemStyle: {
                        normal: {
                            color: function(params) {
                                // build a color map as your need.
                                var colorList = [
                                    '#005580','#0187e2',  '#007797', '#26c4dd', '#9ff0ff'
                                ];
                                return colorList[params.dataIndex]
                            },
                            label: {
                                show: true,
                                position: 'top',
                                formatter: '{b}\n{c}'
                            }
                        }
                    },
                    data:[320, 332, 301, 334, 390]
                },
                {
                    name:'搜索引擎细分',
                    type:'pie',
                    tooltip : {
                        trigger: 'name',
                        formatter: '{b} : {c} )'
                    },
                    center: ['84%', '20%'],
                    radius : [0, 50],
                    itemStyle :　{
                        normal : {
                            labelLine : {
                                length : 20
                            }
                        }
                    },
                    data:[
                        {value:320, name:'移动'},
                        {value:332,name:'联通'},
                        {value:301, name:'电信'},
                        {value:334,  name:'国内其他'},
                        {value:320, name:'国外其他'}
                    ]
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
                    <h1>运营商类型占比</h1>
                </div>
                <div className="Echart">
                    <div id='pie_YYS' className="chart_part">
                        <ReactEcharts ref='echarts_react'
                                      onChartReady={this.showToolTip}
                                      option={this.state.option}
                                      style={{height:"100%"}}
                        />
                    </div>
                </div>
            </div>
        );
    }

});
export default Bar_YYS_ChartComponent;