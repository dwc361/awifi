import React from 'react';

const Table_AC = React.createClass({
    componentDidMount: function() {
        $(function(){
            $('li:eq(0)>i').css('border','0.68rem solid #005580');
            $('li:eq(1)>i').css('border','0.68rem solid #0187e2');
            $('li:eq(2)>i').css('border','0.68rem solid #007797');
            $('li:eq(3)>i').css('border','0.68rem solid #26c4dd');
            $('li:eq(4)>i').css('border','0.68rem solid #9ff0ff');
            $('li:eq(5)>i').css('border','0.68rem solid #808080');
        })
    },
    render(){
        //var styles = [
        //    "border:'0.68rem solid #005580'",
        //    "border:'0.68rem solid #0187e2'",
        //    "border:'0.68rem solid #007797'",
        //    "border:'0.68rem solid #26c4dd'",
        //    "border:'0.68rem solid #9ff0ff'",
        //    "border:'0.68rem solid #808080'"
        //];
        var newsLists = [
            "主AC101435",
            "jingjiang-AC-24575-42342545",
            "jingjiang-AC-24575-42342545",
            "jingjiang-AC-24575-42342545",
            "jingjiang-AC-24575-42342545",
            "jingjiang-AC-24575-42342545"
        ];

        return(
            <div className="table_AC">
                <ul>
                    {
                        newsLists.map(function(news){
                            return <li><i/>{news}</li>
                            })
                      }
                </ul>
            </div>
        );
    }
});
export default Table_AC;