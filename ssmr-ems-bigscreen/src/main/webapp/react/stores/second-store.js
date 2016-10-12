import Reflux from 'reflux'
import actions from '../actions/second-actions'
import $ from 'jquery';

export default Reflux.createStore({
	init () {
       console.log("stores init.");
       // this.listenToMany(actions); 方法前面加on，如onHandleChange
       this.listenTo(actions.getBigscreenSecondData, 'getBigscreenSecondData');
       this.listenTo(actions.saveBigscreenSecondData, 'saveBigscreenSecondData');
       this.listenTo(actions.openAddModal, 'openAddModal');
       this.listenTo(actions.setStateValue, 'setStateValue');
	},
	getBigscreenSecondData () {
    	$.ajax({
    		async: false,
    		type : "post",
    		url : ROOF.Utils.projectName() + "/ems/bigscreen_backstage/SecondBackstageAction/get_bigscreen_second_data.action",
    		//data: {pageNo:0,pageSize:100},
    		datatype : 'json',
    		success : function(d) {
    			this.trigger({chartList:d.data.chartList});
    			this.trigger({relList:d.data.relList});
    			this.trigger({bigscreen_id:d.data.bigscreen.id});
    			this.trigger({theme_id:d.data.theme.id});
    			this.trigger({templates_id:d.data.templates.id});
    			this.trigger({templates_path:d.data.templates.path});
    		}.bind(this)
    	});
    },
    saveBigscreenSecondData (data) {
    	$.ajax({
    		async: false,
    		type: "post",
    		url: ROOF.Utils.projectName() + "/ems/bigscreen_backstage/SecondBackstageAction/save.action",
    		data: data,
    		datatype : 'json',
    		success : function(d) {
    			alert(d.message);
    			this.getBigscreenSecondData();
    		}.bind(this)
    	});
    },
    openAddModal(showFlag){
    	this.trigger({showAddUserModal:showFlag});
    },
    setStateValue(value){
		this.trigger(value);
	}
});
