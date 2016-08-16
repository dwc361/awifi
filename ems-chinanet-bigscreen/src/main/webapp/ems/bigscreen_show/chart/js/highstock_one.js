$(function () {
    $.getJSON('http://www.hcharts.cn/datas/jsonp.php?filename=aapl-c.json&callback=?', function (data) {

        // Create the chart
        $('#highstock_one').highcharts('StockChart', {
        	chart: {
        		events: {                                                           
    				load: function() {
    					var series = this.series[0];
    					series.points[series.points.length-1].setState('hover');
    					console.log(this.pointer);
    					var event = document.createEvent('MouseEvents');
    					event.initEvent( 'mousemove', true, true );
    					event.clientX = 361;
    					event.clientY = 138;
    					event.movementX = 0;
    					event.movementY = 0;
    					event.offsetX = 353;
    					event.offsetY = 130;
    					event.pageX = 361;
    					event.pageY = 138;
    					event.screenX = 0;
    					event.screenY = 0;
    					event.x = 361;
    					event.y = 138;
    					event.type = "mousemove";
    					
    					console.log(this.pointer);
    					//this.pointer.dispatchEvent(event);
    					//console.log(event);
    					//this.pointer.runPointActions(event);
    					this.pointer.onContainerMouseMove(event);
    				}                                                               
    			}
            },
            rangeSelector : {
                selected : 1
            },
            title : {
                text : 'AAPL Stock Price'
            },
            series : [{
                name : 'AAPL Stock Price',
                data : data,
                type : 'areaspline',
                threshold : null,
                tooltip : {
                    valueDecimals : 2
                },
                fillColor : {
                    linearGradient : {
                        x1: 0,
                        y1: 0,
                        x2: 0,
                        y2: 1
                    },
                    stops : [
                        [0, Highcharts.getOptions().colors[0]],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                    ]
                }
            }]
        });
    });
});