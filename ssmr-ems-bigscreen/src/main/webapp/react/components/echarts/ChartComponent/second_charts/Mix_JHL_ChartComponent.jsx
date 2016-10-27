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
        return {activateNum:[],activatePer:[],createTime:[],option: this.getOption([],[],[])};
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
        actions.getMix_jhl_data();
    },
    componentDidUpdate: function(){
        console.log(this.state.activatePer+"*jhl*"+this.state.createTime);
        let option = this.state.option;
        option.xAxis[0].data = this.state.createTime;
        option.series[0].data = this.state.activatePer;
        option.series[1].data = this.state.activateNum;
        this.option = option;
        this.getOption(this.state.activateNum,this.state.activatePer,this.state.createTime);
    },
    componentWillUnmount: function() {
    },
    getOption: function(activateNum,activatePer,createTime) {
        const option = {
            //  backgroundColor:'#333',
            color: [
                '#21c6a5', '#65c7f7'
            ],
            tooltip: {
                trigger: 'axis'
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
                data: createTime
            }],
            yAxis: [{
                type: 'value',
                min: 0,
                max: 250,
                interval: 50,
                axisLabel: {
                    show:false
        //          formatter: '{value} ml'
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
                max: 25,
                interval: 5,
                axisLabel: {
        //          formatter: '{value} °C'
        show:false
                }
            }],
            series: [{
                    name: '激活率',
                    type: 'bar',
                    data: activatePer
                },

                {
                    name: '激活量',
                    type: 'line',
                    yAxisIndex: 1,
                    data: activateNum
                }
            ]
        };

        return option;
    },
    render: function() {
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