import Reflux from 'reflux'
import actions from '../actions/seventh-actions'
import $ from 'jquery';

export default Reflux.createStore({
	init () {
		// this.listenToMany(actions); 方法前面加on，如onHandleChange
		this.listenTo(actions.switch_to_bigscreen_first_display_page, 'switch_to_bigscreen_first_display_page');
		this.listenTo(actions.switch_to_bigscreen_second_display_page, 'switch_to_bigscreen_second_display_page');
		this.listenTo(actions.switch_to_bigscreen_third_display_page, 'switch_to_bigscreen_third_display_page');
		this.listenTo(actions.switch_to_bigscreen_forth_display_page, 'switch_to_bigscreen_forth_display_page');
		this.listenTo(actions.switch_to_bigscreen_fifth_display_page, 'switch_to_bigscreen_fifth_display_page');
	},
	// 大屏切换到一层架构
	switch_to_bigscreen_first_display_page() {
		$.ajax({
			async: false,
			type : "post",
			url : ROOF.Utils.projectName() + "/ems/bigscreen_backstage/MainBackstageAction/switch_to_bigscreen_first_display_page.action",
			//data: {pageNo:0,pageSize:100},
			datatype : 'json',
			success : function(d) {
				alert(d.message);
			}.bind(this)
		});
	},
	// 大屏切换到二层架构
	switch_to_bigscreen_second_display_page() {
		$.ajax({
			async: false,
			type: "post",
			url: ROOF.Utils.projectName() + "/ems/bigscreen_backstage/MainBackstageAction/switch_to_bigscreen_second_display_page.action",
			//data: "",
			datatype : 'json',
			success : function(d) {
				alert(d.message);
			}.bind(this)
		});
	},
	// 大屏切换到三层架构
	switch_to_bigscreen_third_display_page() {
		$.ajax({
			async: false,
			type: "post",
			url: ROOF.Utils.projectName() + "/ems/bigscreen_backstage/MainBackstageAction/switch_to_bigscreen_third_display_page.action",
			//data: "",
			datatype : 'json',
			success : function(d) {
				alert(d.message);
			}.bind(this)
		});
	},
	// 大屏切换到四层架构
	switch_to_bigscreen_forth_display_page() {
		$.ajax({
			async: false,
			type: "post",
			url: ROOF.Utils.projectName() + "/ems/bigscreen_backstage/MainBackstageAction/switch_to_bigscreen_forth_display_page.action",
			//data: "",
			datatype : 'json',
			success : function(d) {
				alert(d.message);
			}.bind(this)
		});
	},
	// 大屏切换到五层架构
	switch_to_bigscreen_fifth_display_page() {
		$.ajax({
			async: false,
			type: "post",
			url: ROOF.Utils.projectName() + "/ems/bigscreen_backstage/MainBackstageAction/switch_to_bigscreen_fifth_display_page.action",
			//data: "",
			datatype : 'json',
			success : function(d) {
				alert(d.message);
			}.bind(this)
		});
	}
});