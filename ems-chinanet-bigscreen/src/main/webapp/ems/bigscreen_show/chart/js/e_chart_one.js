var chart = echarts.init(document.getElementById('main'), 'macarons');

var opt = {
    title: {
        text: '访问来源',
        x: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
    },
    series: [{
        name: '访问来源',
        type: 'pie',
        radius: '55%',
        center: ['50%', '60%'],
        data: [{
            value: 335,
            name: '直接访问'
        }, {
            value: 310,
            name: '邮件营销'
        }, {
            value: 234,
            name: '联盟广告'
        }, {
            value: 135,
            name: '视频广告'
        }, {
            value: 1548,
            name: '搜索引擎'
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

var app = {};
app.currentIndex = -1;
app.timeTicket = setInterval(function() {
    var dataLen = opt.series[0].data.length;
    // 取消之前高亮的图形
    chart.dispatchAction({
        type: 'downplay',
        seriesIndex: 0,
        dataIndex: app.currentIndex
    });
    app.currentIndex = (app.currentIndex + 1) % dataLen;
    // 高亮当前图形
    chart.dispatchAction({
        type: 'highlight',
        seriesIndex: 0,
        dataIndex: app.currentIndex
    });
    // 显示 tooltip
    chart.dispatchAction({
        type: 'showTip',
        seriesIndex: 0,
        dataIndex: app.currentIndex
    });
}, 1000);

chart.setOption(opt);

if (opt && typeof opt === "object") {
    var startTime = +new Date();
    chart.setOption(opt, true);
    var endTime = +new Date();
    var updateTime = endTime - startTime;
    console.log("Time used:", updateTime);
}