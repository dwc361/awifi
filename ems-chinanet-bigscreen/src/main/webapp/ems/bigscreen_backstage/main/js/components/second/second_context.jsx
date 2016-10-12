import React from 'react';
import ReactDOM from 'react-dom';
var BS = require('react-bootstrap');

import BigscreenPart from './BigscreenPart.jsx';
import SourceFillBigscreen from './SourceFillBigscreen.jsx';
import DragChart from './DragChart.jsx';
import AddChart from './AddChart.jsx';



if(typeof(chartList) != 'undefined') {
    ReactDOM.render(
        <div style={{width:"100%",height:"100%"}}>
            {
                chartList.map(function (obj, i) {
                    return <DragChart key={i} id={obj.id} path={obj.path} title={obj.name} url={obj.icon} style={{display:"inline-block"}} />
                })
            }
            <AddChart/>
        </div>,
        document.getElementById('chartCnt'));
}



const partIds=[
    "part_1_1", "map_part_1_2", "map_part_1_3","map_part_1_4","part_1_5",
    "part_2_1", "map_part_2_2", "map_part_2_3","map_part_2_4","part_2_5",
    "part_3_1", "map_part_3_2", "map_part_3_3","map_part_3_4","part_3_5",
    "part_4_1", "map_part_4_2", "map_part_4_3","map_part_4_4","part_4_5"
];
ReactDOM.render(
    <div style={{width:"100%",height:"100%"}}>
        {
            partIds.map(function(obj, i) {
                return <BigscreenPart key={i} index={i} id={obj} />
            })
        }
    </div>,
    document.getElementById('screen'));



if(typeof(relList) != 'undefined') {
    for(var i=0; i<relList.length; i++) {
        if(document.getElementById(relList[i].target) != null) {
            ReactDOM.render(<SourceFillBigscreen id={relList[i].chart_id} path={relList[i].chart_path}
                                                 url={relList[i].chart_icon} target={relList[i].target} title={relList[i].chart_title} />,
                document.getElementById(relList[i].target));
        }
    };
}