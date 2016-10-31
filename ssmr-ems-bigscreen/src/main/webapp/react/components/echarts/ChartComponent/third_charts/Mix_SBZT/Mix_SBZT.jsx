import React from 'react';
import ReactEcharts from '../../../src/echarts-for-react';
import Table_SBZT from  './table_SBZT.jsx';
import Form_SBZT from './form_SBZT.jsx';


const Mix_SBZT_ChartComponent = React.createClass ({
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
                '#0187e2','#26c4dd'
            ],

            series: [
                {
                    name:'设备状态概览',
                    type:'pie',
                    radius: ['50%', '80%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            formatter: "{b}",
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
                    data:[
                        {value:2576, name:'在线设备数'},
                        {value:118, name:'不在线设备数'}
                    ]
                }

            ]
        };
        return option;
    },
    render :function(){
        return(
            <div className="chart_block">
                <div className="topH">
                    <hr/>
                    <h1>设备状态概览</h1>
                </div>
                <div className="Echart">
                    <div className="data-SBZT">
                        <Table_SBZT/>
                    </div>
                    <div id='mix_SBZT' className="chart_part">
                        <ReactEcharts ref='echarts_react'
                                      onChartReady={this.showToolTip}
                                      option={this.state.option} style={{width:'60%'}}/>
                        <Form_SBZT/>
                    </div>
                </div>
            </div>
        )
    }
});
export default Mix_SBZT_ChartComponent;