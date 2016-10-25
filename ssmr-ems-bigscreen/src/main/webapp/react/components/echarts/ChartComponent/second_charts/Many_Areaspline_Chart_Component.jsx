import React from 'react';

require('./js/many_areaspline_chart.js');

const Many_Areaspline_Chart_Component = React.createClass({
    propTypes: {
    },

    render: function() {
        return (
            <div className="left col-md-12 col-lg-12 col-sm-12">
                <div className="topH">
                    <h1>[ 平台用户总量 ]</h1>
                </div>
                <div className="Hchart" style={{position: 'relative'}}>
                    <div id="many_areaspline_chart"></div>
                </div>
            </div>
        );
    }
});

export default Many_Areaspline_Chart_Component;