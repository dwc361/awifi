import React from 'react';
import ReactEcharts from '../../../src/echarts-for-react';
import Table_AC from './table-AC.jsx';


const Pie_AC_ChartComponent = React.createClass({
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
            $('#pie_AC').find('canvas').css('top','-10%');
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
                trigger: 'item',
                formatter: "{b} <br/>{c} ({d}%)"
            },
            series: [
                {
                    name:'与AC关联的移动终端信息',
                    type:'pie',
                    radius: ['50%', '80%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '20',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data: [{
                        value: 39.92,
                        name: '主AC'
                    }, {
                        value: 22.8,
                        name: 'WuZheng1'
                    }, {
                        value: 5.54,
                        name: 'JingQuJi1'
                    }, {
                        value: 5.21,
                        name: '备AC'
                    }, {
                        value: 4.66,
                        name: 'WuZheng2'
                    }, {
                        value: 21.87,
                        name: 'JingQuJi2'
                    }],
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
                    <h1>与AC关联的移动终端信息</h1>
                </div>
                <div className="Echart">
                    <div className="data-AC">
                        <Table_AC/>
                    </div>
                    <div id='pie_AC' className="chart_part">
                        <ReactEcharts ref='echarts_react'
                                      onChartReady={this.showToolTip}
                                      option={this.state.option}/>
                    </div>
                </div>
            </div>
        );
    }
});

export default Pie_AC_ChartComponent;
