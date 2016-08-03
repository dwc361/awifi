$(function () {
 if (!Highcharts.theme) {
        Highcharts.setOptions({
            chart: {
                backgroundColor: 'black'
            },
            colors: ['#367bdb', '#d9a831', '#df5964'],
            title: {
                style: {
                    color: 'silver'
                }
            },
            tooltip: {
                style: {
                    color: 'silver'
                }
            }
        });
    }
    $('#round_div').highcharts({
        chart: {
            type: 'pie',
            backgroundColor: 'rgba(0,0,0,0)',
            options3d: {
                enabled: true,
                alpha: 20
            }
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        legend: {
            enabled: false
        },
        credits: {
            enabled: false
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y:.0f}/台</b>'
        },
        plotOptions: {
            pie: {
            	size: 180,
            	 center: [200, 200],
                innerSize: 50,
                depth: 20,
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.0f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            name: '胖AP激活率',
            data: [
                ['光猫', 8],
                ['二合一', 3],
                ['三合一', 6]
            ]
        }]
    });
setInterval(function () {
		        // 光 猫
		        var chart = $('#round_div').highcharts(),
		            point,
		            newVal,
		            inc;
		        if (chart) {
		            point = chart.series[0].points[0];
		            inc = Math.round((Math.random() - 0.5) * 50);
		            newVal = point.y + inc;
		            if (newVal < 0 || newVal > 200) {
		                newVal = point.y - inc;
		            }
		            point.update(newVal);
		        }
		        //二合一
		        chart = $('#round_div').highcharts();
		        if (chart) {
		            point = chart.series[0].points[1];
		            inc = Math.random() - 0.5;
		            newVal = point.y + inc;
		            if (newVal < 200 || newVal > 1000) {
		                newVal = point.y - inc;
		            }
		            point.update(newVal);
		        }
		        //三合一
		        chart = $('#round_div').highcharts();
		        if (chart) {
		            point = chart.series[0].points[2];
		            inc = Math.random() - 0.5;
		            newVal = point.y + inc;
		            if (newVal < 200 || newVal > 1000) {
		                newVal = point.y - inc;
		            }
		            point.update(newVal);
		        }
		    }, 1000);
});