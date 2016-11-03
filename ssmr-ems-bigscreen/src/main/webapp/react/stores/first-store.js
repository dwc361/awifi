import Reflux from 'reflux'
import actions from '../actions/first-actions.js'
import $ from 'jquery';

export default Reflux.createStore({
    init () {
        //console.log("stores init.");
        // this.listenToMany(actions); 方法前面加on，如onHandleChange
        this.listenTo(actions.getBigscreenFirstData, 'getBigscreenFirstData');
    },
    //一层监控
    getBigscreenFirstData () {
        $.ajax({
            async: false,
            type : "post",
            url : ROOF.Utils.projectName() + "/ems/bigscreen_backstage/FirstBackstageAction/get_bigscreen_first_data.action",
            //data: {pageNo:0,pageSize:100},
            datatype : 'json',
            success : function(d) {
                this.trigger({wordList:d.data.wordList});
                this.trigger({id:d.data.wordList[0].id});
                this.trigger({name:d.data.wordList[0].name});
                this.trigger({wordid:d.data.wordList[0].wordid});
                this.trigger({type:d.data.wordList[0].type});
                this.trigger({wordpath:d.data.wordpath[0]});
            }.bind(this)
        });
    }
});