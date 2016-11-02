import Reflux from 'reflux'
import actions from '../actions/second-actions'
import $ from 'jquery';

export default Reflux.createStore({
	init () {
		// this.listenToMany(actions); 方法前面加on，如onHandleChange
		this.listenTo(actions.getBigscreenSecondData, 'getBigscreenSecondData');
		this.listenTo(actions.saveBigscreenSecondData, 'saveBigscreenSecondData');
		this.listenTo(actions.openAddModal, 'openAddModal');
		this.listenTo(actions.setStateValue, 'setStateValue');
		this.listenTo(actions.getMix_Dzzd_data, 'getMix_Dzzd_data');
		this.listenTo(actions.getFunnel_sbpm_data, 'getFunnel_sbpm_data');
		this.listenTo(actions.getLine_yhrz_data, 'getLine_yhrz_data');
		this.listenTo(actions.getMix_nas_data, 'getMix_nas_data');
		this.listenTo(actions.getMix_jhl_data, 'getMix_jhl_data');
		this.listenTo(actions.getPie_lxfb_data, 'getPie_lxfb_data');
		this.listenTo(actions.getScatter_hotspot_data, 'getScatter_hotspot_data');
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
	},
	setListDate(arr,num){
		var begin = 0,
			end = begin + num;
		var result = [],
			arrLength = arr.length;

		if (end >= arrLength) return result.push(arr);
		while (end < arrLength) {
			result.push(arr.slice(begin, end));
			begin = begin + num;
			end = begin + num;
			if (end > arrLength) {
				end = arrLength;
				result.push(arr.slice(begin, end));
				break;
			}
		}
		return  result;
	},
	// 1.全省设备排名
	getFunnel_sbpm_data(){
		$.ajax({
			async: false,
			type : "post",
			url : ROOF.Utils.projectName() + "/ems/bigscreen_show/dataShowAction/funnel_sbpm_data.action",
			/*data: {x_json:data},*/
			datatype : 'json',
			success : function(d) {
				var device = [];
				var pro = [];
				for(var i = 0;i< d.data.length;i++){
					device.push(d.data[i].deviceNum);
					pro.push(d.data[i].province);
				}
				device = this.setListDate(device,6);
				pro = this.setListDate(pro,6);
				this.trigger({deviceNum:device});
				this.trigger({province:pro});
			}.bind(this)
		});
	},
	// 2.用户认证状态
	getLine_yhrz_data(){
		$.ajax({
			async: false,
			type : "post",
			url : ROOF.Utils.projectName() + "/ems/bigscreen_show/dataShowAction/line_yhrz_data.action",
			/*data: {x_json:data},*/
			datatype : 'json',
			success : function(d) {
				var success = [];
				var datas = [];
				var now;
				for(var i = 0;i< d.length;i++){
					success.push(d[i].successNum);
					if(d[i].createTime==null){
						now = new Date();//定义一个时间对象
					}else{
						now = new Date(d[i].createTime);
					}
					var m = now.getMonth()+1; //获取当前月份(0-11,0代表1月)
					var date = now.getDate(); //获取当前日(1-31)
					datas.push(m+"/"+date);
				}
				this.trigger({successNum:success});
				this.trigger({createTime:datas});
			}.bind(this)
		});
	},
	// 3.定制终端
	getMix_Dzzd_data(){
		$.ajax({
			async: false,
			type : "post",
			url : ROOF.Utils.projectName() + "/ems/bigscreen_show/dataShowAction/mix_dzzd_data.action",
			/*data: {x_json:data},*/
			datatype : 'json',
			success : function(d) {
				var onLine = [];
				var offLine = [];
				var datas = [];
				var now;
				for(var i = 0;i< d.length;i++){
					onLine.push(d[i].onlineNum);
					offLine.push(d[i].offlineNum);
					if(d[i].createTime==null){
						now = new Date();//定义一个时间对象
					}else{
						now = new Date(d[i].createTime);
					}
					var m = now.getMonth()+1; //获取当前月份(0-11,0代表1月)
					var date = now.getDate(); //获取当前日(1-31)
					datas.push(m+"/"+date);
				}
				this.trigger({onlineNum:onLine});
				this.trigger({offlineNum:offLine});
				this.trigger({createTime:datas});
			}.bind(this)
		});
	},
	// 4.NAS设备状态统计
	getMix_nas_data(){
		$.ajax({
			async: false,
			type : "post",
			url : ROOF.Utils.projectName() + "/ems/bigscreen_show/dataShowAction/mix_nas_data.action",
			/*data: {x_json:data},*/
			datatype : 'json',
			success : function(d) {
				var onLine = [];
				var offLine = [];
				var datas = [];
				var now;
				for(var i = 0;i< d.length;i++){
					onLine.push(d[i].onlineNum);
					offLine.push(d[i].offlineNum);
					if(d[i].createTime==null){
						now = new Date();//定义一个时间对象
					}else{
						now = new Date(d[i].createTime);
					}
					var m = now.getMonth()+1; //获取当前月份(0-11,0代表1月)
					var date = now.getDate(); //获取当前日(1-31)
					datas.push(m+"/"+date);
				}
				this.trigger({onlineNum:onLine});
				this.trigger({offlineNum:offLine});
				this.trigger({createTime:datas});
			}.bind(this)
		});
	},
	// 5.胖ap激活率统计
	getMix_jhl_data(){
		$.ajax({
			async: false,
			type : "post",
			url : ROOF.Utils.projectName() + "/ems/bigscreen_show/dataShowAction/mix_jhl_data.action",
			/*data: {x_json:data},*/
			datatype : 'json',
			success : function(d) {
				var num = [];
				var per = [];
				var datas = [];
				var now;
				for(var i = 0;i< d.length;i++){
					num.push(d[i].activateNum);
					per.push(d[i].activatePer);
					if(d[i].createTime==null){
						now = new Date();//定义一个时间对象
					}else{
						now = new Date(d[i].createTime);
					}
					var m = now.getMonth()+1; //获取当前月份(0-11,0代表1月)
					var date = now.getDate(); //获取当前日(1-31)
					datas.push(m+"/"+date);
				}
				this.trigger({activateNum:num});
				this.trigger({activatePer:per});
				this.trigger({createTime:datas});
			}.bind(this)
		});
	},
	// 6.设备类型分布
	getPie_lxfb_data(){
		$.ajax({
			async: false,
			type : "post",
			url : ROOF.Utils.projectName() + "/ems/bigscreen_show/dataShowAction/pie_lxfb_data.action",
			/*data: {x_json:data},*/
			datatype : 'json',
			success : function(d) {
				var res = [];
				if(d.data != null) {
					for (var i=0;i<d.data.length;i++) {
						res.push({"value":d.data[i].deviceNum,"name":d.data[i].deviceName});
					}
				}
				this.trigger({dataList:res});
			}.bind(this)
		});
	},
	// 7.爱wifi热点类型分布 (5个一组)
	getScatter_hotspot_data(){
		$.ajax({
			async: false,
			type : "post",
			url : ROOF.Utils.projectName() + "/ems/bigscreen_show/dataShowAction/scatter_hotspot_data.action",
			/*data: {x_json:data},*/
			datatype : 'json',
			success : function(d) {
				var name = [];
				var num = [];
				for(var i = 0;i< d.data.length;i++){
					name.push(d.data[i].typeName);
					num.push(d.data[i].hotareaNum);
				}
				name = this.setListDate(name,6);
				num = this.setListDate(num,6);
				this.trigger({typeName:name});
				this.trigger({hotareaNum:num});
			}.bind(this)
		});
	}
});