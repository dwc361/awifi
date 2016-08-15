// 基于准备好的dom，初始化echarts实例
var hotspotChart = echarts.init(document.getElementById('hotspot'));

// 指定图表的配置项和数据

opt_hotspot = {

    tooltip : {
        trigger: 'item',
        formatter: '{a} : {b}'
    },
    toolbox: {
        show : true,
        feature : {
            restore : {show: true},
            magicType: {show: true, type: ['force', 'chord']},
            saveAsImage : {show: true}
        }
    },
    legend: {
        x: 'left',
        data:['家人','朋友']
    },
    series : [
        {
            type:'force',
            name : "人物关系",
            ribbonType: false,
            categories : [
                {
                    name: '人物'
                },
                {
                    name: '家人'
                },
                {
                    name:'朋友'
                }
            ],
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        textStyle: {
                            color: '#333'
                        }
                    },
                    nodeStyle : {
                        brushType : 'both',
                        borderColor : 'rgba(255,215,0,0.4)',
                        borderWidth : 1
                    },
                    linkStyle: {
                        type: 'curve'
                    }
                },
                emphasis: {
                    label: {
                        show: false
                        // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
                    },
                    nodeStyle : {
                        //r: 30
                    },
                    linkStyle : {}
                }
            },
            useWorker: false,
            minRadius : 15,
            maxRadius : 25,
            gravity: 1.1,
            scaling: 1.1,
            roam: 'move',
            nodes:[
                {category:0, name: '乔布斯', value : 10, label: '乔布斯\n（主要）'},
                {category:2, name: '丽乔布斯',value : 2},
                {category:3, name: '保罗-乔斯',value : 3},
                {category:4, name: '克拉乔布斯',value : 10},
                {category:5, name: '劳伦-鲍尔',value : 7},
                {category:6, name: '史蒂夫-沃兹尼艾克',value : 5},
                {category:7, name: '奥马',value : 8},
                {category:8, name: '比尔-盖茨',value : 9},
                {category:9, name: '乔纳-艾夫',value : 4},
                {category:10, name: '蒂-库克',value : 4},
                {category:11, name: '龙-恩',value : 1},
                 {category:12, name: '史蒂-沃兹尼艾克',value : 5},
                {category:13, name: '奥巴马',value : 8},
                {category:14, name: '比-盖茨',value : 9},
                {category:15, name: '乔纳森艾夫',value : 4},
                {category:16, name: '蒂姆-克',value : 4},
                {category:17, name: '恩',value : 1},
            ],
            links : [
                {source : '丽萨-乔布斯', target : '乔布斯', weight : 1, name: '女儿'},
                {source : '保罗-乔布斯', target : '乔布斯', weight : 2, name: '父亲'},
                {source : '克拉拉-乔布斯', target : '乔布斯', weight : 1, name: '母亲'},
                {source : '劳伦-鲍威尔', target : '乔布斯', weight : 2},
                {source : '史蒂夫-沃兹尼艾克', target : '乔布斯', weight : 3, name: '合伙人'},
                {source : '奥巴马', target : '乔布斯', weight : 1},
                {source : '比尔-盖茨', target : '乔布斯', weight : 6, name: '竞争对手'},
                {source : '乔纳森-艾夫', target : '乔布斯', weight : 1, name: '爱将'},
                {source : '蒂姆-库克', target : '乔布斯', weight : 1},
                {source : '韦恩', target : '乔布斯', weight : 1},
                {source : '克拉拉-乔布斯', target : '乔布斯', weight : 1},
                {source : '奥巴马', target : '乔布斯', weight : 1},
                {source : '奥巴马', target : '乔布斯', weight : 1},
                {source : '奥巴马', target : '乔布斯', weight : 1},
                {source : '奥巴马', target : '乔布斯', weight : 1},
                {source : '比尔-盖茨', target : '乔布斯', weight : 6},
                {source : '比尔-盖茨', target : '乔布斯', weight : 1},
                {source : '蒂姆-库克', target : '乔布斯', weight : 1}
            ]
        }
    ]
};
var ecConfig = require('echarts/config');
function focus(param) {
    var data = param.data;
    var links = option.series[0].links;
    var nodes = option.series[0].nodes;
    if (
        data.source !== undefined
        && data.target !== undefined
    ) { //点击的是边
        var sourceNode = nodes.filter(function (n) {return n.name == data.source})[0];
        var targetNode = nodes.filter(function (n) {return n.name == data.target})[0];
        console.log("选中了边 " + sourceNode.name + ' -> ' + targetNode.name + ' (' + data.weight + ')');
    } else { // 点击的是点
        console.log("选中了" + data.name + '(' + data.value + ')');
    }
}
hotspotChart.on(ecConfig.EVENT.CLICK, focus)

hotspotChart.on(ecConfig.EVENT.FORCE_LAYOUT_END, function () {
    console.log(hotspotChart.chart.force.getPosition());
});
// 使用刚指定的配置项和数据显示图表。
hotspotChart.setOption(opt_hotspot);

                    