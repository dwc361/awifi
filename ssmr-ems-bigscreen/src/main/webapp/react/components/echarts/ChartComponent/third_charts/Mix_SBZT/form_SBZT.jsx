import React from 'react';

const Form_SBZT = React.createClass ({
    render : function(){
        var sbztList = [
            <span><i style={{backgroundColor:'#005580'}}>0</i>严重</span>,
            <span><i style={{backgroundColor:'#9ff0ff'}}>0</i>重要</span>,
            <span><i style={{backgroundColor:'#26c4dd'}}>0</i>次要</span>,
            <span><i style={{backgroundColor:'#007797'}}>0</i>警告</span>,
            <span><i style={{backgroundColor:'#0187e2'}}>2576</i>正常</span>,
            <span><i style={{backgroundColor:'#007797'}}>118</i>未知</span>,
            <span><i style={{backgroundColor:'#b3b3b3'}}>0</i>未管理</span>
        ];

        return(
            <div>
                <ul>
                    <li>{sbztList}</li>
                </ul>
            </div>
        )
    }
});
export default Form_SBZT ;