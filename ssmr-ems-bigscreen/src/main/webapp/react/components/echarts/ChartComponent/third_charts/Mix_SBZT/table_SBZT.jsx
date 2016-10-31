import React from 'react';

const Table_SBZT = React.createClass({
    render : function(){
        var h1List=[
            <div className="sbzt_block" style={{backgroundColor:'#005580'}}><span><i>2694</i></span><span style={{backgroundColor:'#184766'}}><i style={{fontSize:'3.2rem'}}>总数</i></span></div>,
            <div className="sbzt_block" style={{backgroundColor:'#0187e2'}}><span><i>2576</i></span><span style={{backgroundColor:'#0264A6'}}><i style={{fontSize:'3.2rem'}}>在线</i></span></div>,
            <div className="sbzt_block" style={{backgroundColor:'#26c4dd'}}><span><i>118</i></span><span style={{backgroundColor:'#1A8899'}}><i style={{fontSize:'3.2rem'}}>不在线</i></span></div>
        ];

        return(
            <div>{h1List}</div>
        )
    }
});

export default Table_SBZT ;