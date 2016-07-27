$(function() {
		ROOF.Utils.datepicker("bigscreen_create_time");
	ROOF.Utils.datepicker("bigscreen_update_time");

	$('#bigscreen_bigscreen_form').validate({
		rules : {
			'id' : {
				number : true,
				required : true
			}, 
			'name' : {
				required : true
			}, 
			'template_id' : {
				number : true,
				required : true
			}, 
			'theme_id' : {
				number : true,
				required : true
			}, 
			'publish' : {
				required : true
			}, 
			're_time' : {
				number : true,
				required : true
			}, 
			're_type' : {
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
			'name' : {
				required : "名称必填"
			}, 
			'template_id' : {
				required : "模板id必填"
			}, 
			'theme_id' : {
				required : "主题id必填"
			}, 
			'publish' : {
				required : "是否发布必填"
			}, 
			're_time' : {
				required : "刷新时间必填"
			}, 
			're_type' : {
				required : "re_type必填"
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
	$("#bigscreen_bigscreen_form").ajaxForm({
		'type' : 'post',
		'cache' : false,
		'dataType' : 'json',
		'clearForm' : true,
		'success' : function(d) {
			alert(d.message);
			$("#bigscreen_bigscreen_update_close_btn").click();
		},
		'error' : function(d) {
			alert(d.statusText);
		}
	});

	$("#bigscreen_bigscreen_detail_close_btn").click(function() {
		reloadFun();		
		return false;
	});

	$("#bigscreen_bigscreen_update_close_btn").click(function() {
		reloadFun();		
		return false;
	});
});