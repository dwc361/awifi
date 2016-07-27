$(function() {
		ROOF.Utils.datepicker("chart_create_time");
	ROOF.Utils.datepicker("chart_update_time");

	$('#bigscreen_chart_form').validate({
		rules : {
			'id' : {
				number : true,
				required : true
			}, 
			'icon' : {
				required : true
			}, 
			'name' : {
				required : true
			}, 
			'enabled' : {
				required : true
			}, 
			're_times' : {
				number : true,
				required : true
			}, 
			'configure' : {
				required : true
			}, 
			'path' : {
				required : true
			}, 
			'create_time' : {
				required : true
			}, 
			'create_by' : {
				required : true
			}, 
			'update_time' : {
				required : true
			}, 
			'update_by' : {
				required : true
			}
		},
		messages : {
			'id' : {
				required : "主键必填"
			}, 
			'icon' : {
				required : "图标必填"
			}, 
			'name' : {
				required : "图表名称必填"
			}, 
			'enabled' : {
				required : "是否可用必填"
			}, 
			're_times' : {
				required : "刷新时间必填"
			}, 
			'configure' : {
				required : "配置json必填"
			}, 
			'path' : {
				required : "代码路径必填"
			}, 
			'create_time' : {
				required : "新建时间必填"
			}, 
			'create_by' : {
				required : "所属用户必填"
			}, 
			'update_time' : {
				required : "更新时间必填"
			}, 
			'update_by' : {
				required : "更新用户必填"
			}
		},
		errorPlacement : com.letv.errorpacement
	});
	$("#bigscreen_chart_form").ajaxForm({
		'type' : 'post',
		'cache' : false,
		'dataType' : 'json',
		'clearForm' : true,
		'success' : function(d) {
			alert(d.message);
			$("#bigscreen_chart_update_close_btn").click();
		},
		'error' : function(d) {
			alert(d.statusText);
		}
	});

	$("#bigscreen_chart_detail_close_btn").click(function() {
		reloadFun();		
		return false;
	});

	$("#bigscreen_chart_update_close_btn").click(function() {
		reloadFun();		
		return false;
	});
});