$(function () {
    $.ajax({ 
        type: 'post', 
        dataType : "json", 
        url: ROOF.Utils.projectName() + "/ems/bigscreen_show/dataShowAction/areaspline_chart_data.action",
        data: {'countStr':8},
        success: function(d) {
            if(d != null) {
                var data_a = [];
                $.each(d, function(i, n) {
                    var x_date = new Date(n.createTime);
                    data_a.push({
                        x: Date.UTC(x_date.getFullYear(), x_date.getMonth(), x_date.getDate(), x_date.getHours(), x_date.getMinutes()),
                        y: parseInt(n.userClickNum)
                    });
                });
            }
            show_many_areaspline_chart(data_a);
            showLabel(d[d.length-1]);
        }, 
        error:function(d) {
            alert(d.statusText);
        }
    });
});

/**
 * HighCharts图表显示
 */
function show_many_areaspline_chart(data_a) {
    Highcharts.getOptions().colors = "#007799,#434348,#90ed7d,#f7a35c,#8085e9,#f15c80,#e4d354,#2b908f,#f45b5b,#91e8e1".split(",");
    $('#many_areaspline_chart').highcharts({
        chart: {
            type: 'areaspline',
            backgroundColor: 'transparent',
            height:  $(window).height() / 5,
            events: {
                load: function() {
                    var series_a = this.series[0];
                    series_a.points[series_a.points.length-1].setState('hover');
                    
                    setInterval(function() {
                        $.ajax({
                            type: 'post', 
                            dataType : "json", 
                            url: ROOF.Utils.projectName() + "/ems/bigscreen_show/dataShowAction/areaspline_chart_data.action",
                            data: {'countStr':1},
                            success: function(d) {
                                if(d != null) {
                                    $.each(d, function(i, n) {
                                        var x_date = new Date(n.createTime);
                                        var x = Date.UTC(x_date.getFullYear(), x_date.getMonth(), x_date.getDate(), x_date.getHours(), x_date.getMinutes());
                                        var y = parseInt(n.userClickNum);
                                        
                                        series_a.points[series_a.points.length-1].setState();
                                        series_a.addPoint([x, y], true, true);
                                        series_a.points[series_a.points.length-1].setState('hover');

                                        showLabel(n);
                                    });
                                }
                            }, 
                            error:function(d) {
                                alert(d.statusText);
                            }
                        });
                        
                        
                    }, 60*1000);
                }                                                               
            } 
        },
        title: {
            text: '',
            style: {
                color: 'white', // 标题白色字体
                fontWeight: 'bold' // 标题加粗
            }
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            type: 'datetime',
            dateTimeLabelFormats: {
                millisecond: '%H:%M:%S.%L',
                second: '%H:%M:%S',
                minute: '%H:%M',
                hour: '%H:%M',
                day: '%e. %b %H:%M',
                week: '%e. %b',
                month: '%b \'%y',
                year: '%Y'
            }
        },
        yAxis: {
            title: {
                text: ''
            },
            labels: {
                formatter: function () {
                    return '';
                }
            },
            gridLineColor: ''
        },
        legend: { // 图例
            enabled: false
        },
        tooltip: {
            enabled: false
        },
        credits: { // 版权信息
            enabled: false
        },
        plotOptions: { // 数据点配置
            areaspline: {
                fillColor: {
                    linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                    ]
                },
                marker: {
                    enabled: false,
                    radius: 4
                },
                lineWidth: 1,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
            },
            series: {
                lineColor: '#FF0000'
            }
        },
        series: [{ // 数据列
            type: 'areaspline',
            name: '故障',
            //pointStart: Date.UTC(y, m, d, 0), // 开始时间：y年m月d日0时
            //pointInterval: 3600 * 1000, // 每隔1小时
            data: (function() {                                                            
                return data_a;
            })(),
            fillColor: {
                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},   
                stops: [
                    [0, Highcharts.getOptions().colors[0]],
                    [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                ]
            },
            lineWidth: 0,
            lineColor: 'cyan',
            marker: {
                symbol: 'circle',
                lineWidth: 1,
                lineColor: 'red',
                fillColor: 'white'
            }
        }]
    });
}

/**
 * 某个Point的Label显示
 */
function showLabel(d) {
    var chart = $('#many_areaspline_chart').highcharts();
    if (chart.lbl) {
        chart.lbl.destroy();
    }
    var label = '时间:' + getDateTime(d.createTime) + '<br>用户点击量(/时)：' + d.userClickNum;
    chart.lbl = chart.renderer.label(label, 15, 0)
    .attr({
        padding: 5,
        r: 5,
        fill: 'transparent',
        zIndex: 5
    })
    .css({
        color: 'white'
    })
    .add();
}

/**
 * 时间戳转换成自定义格式的String
 */
function getDateTime(long_time) {
    var date = new Date(parseInt(long_time));
    return dateTimeFormat(date);
}
function dateTimeFormat(obj) {
    var year=obj.getFullYear(); //年
    var month=obj.getMonth()+1; //月
    var date=obj.getDate(); //日
    var hour=obj.getHours(); //时  
    var minute=obj.getMinutes(); //分     
    var second=obj.getSeconds(); //秒
    return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
}