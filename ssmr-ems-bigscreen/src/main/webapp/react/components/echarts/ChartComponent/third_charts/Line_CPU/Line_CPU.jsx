import React from 'react';
import ReactEcharts from '../../../src/echarts-for-react';
import Table_CPU from './table-CPU.jsx';

const Line_CPU_ChartComponent = React.createClass ({

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
        $(function(){
            var canvas = $('canvas');
            $('#pie_CPU').find('canvas').css('top','-16%');
        })
    },
    componentWillUnmount: function() {
    },
    getOption: function() {
        const option = {
            color: [
                '#005580', '#9ff0ff', '#26c4dd', '#007797','#0187e2', '#808080'
            ],
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },

            xAxis: {
                type: 'category',
                boundaryGap: false,
                axisLine: {
                    show: true,
                    onZero: true,
                    lineStyle: {
                        color:'#eee'
                    }
                },
                data: ['周一','周二','周三','周四','周五','周六','周日']
            },
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
            series: [
                {
                    name:'邮件营销',
                    type:'line',
                    stack: '总量',
                    data:[120, 132, 101, 134, 90, 230, 210]
                },
                {
                    name:'联盟广告',
                    type:'line',
                    stack: '总量',
                    data:[220, 182, 191, 234, 290, 330, 310]
                },
                {
                    name:'视频广告',
                    type:'line',
                    stack: '总量',
                    data:[150, 232, 201, 154, 190, 330, 410]
                },
                {
                    name:'直接访问',
                    type:'line',
                    stack: '总量',
                    data:[320, 332, 301, 334, 390, 330, 320]
                },
                {
                    name:'搜索引擎',
                    type:'line',
                    stack: '总量',
                    data:[820, 932, 901, 934, 1290, 1330, 1320]
                },
                {
                    name:'间接访问',
                    type:'line',
                    stack: '总量',
                    data:[340, 322, 201, 234, 300, 230, 310]
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
                    <h1>CPU利用率(%)-TOP5</h1>
                </div>
                <div className="Echart">
                    <div className="data-CPU">
                        <Table_CPU/>
                    </div>
                    <div id='pie_CPU' className="chart_part">
                        <ReactEcharts ref='echarts_react'
                                      onChartReady={this.showToolTip}
                                      option={this.state.option}
                                      style={{height:'100%'}}
                        />
                    </div>
                </div>
            </div>
        );
    }
});

export default Line_CPU_ChartComponent;