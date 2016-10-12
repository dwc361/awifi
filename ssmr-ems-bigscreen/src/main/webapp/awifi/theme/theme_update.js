$(function() {
		ROOF.Utils.datepicker("theme_create_time");
	ROOF.Utils.datepicker("theme_update_time");

	$('#bigscreen_theme_form').validate({
		rules : {
			'id' : {
				number : true,
				required : true
			}, 
			'name' : {
				required : true
			}, 
			'picture' : {
				required : true
			}, 
			'enabled' : {
				required : true
			}, 
			'remark' : {
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
			'picture' : {
				required : "背景图片必填"
			}, 
			'enabled' : {
				required : "是否可用必填"
			}, 
			'remark' : {
				required : "备注必填"
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
	$("#bigscreen_theme_form").ajaxForm({
		'type' : 'post',
		'cache' : false,
		'dataType' : 'json',
		'clearForm' : true,
		'success' : function(d) {
			alert(d.message);
			$("#bigscreen_theme_update_close_btn").click();
		},
		'error' : function(d) {
			alert(d.statusText);
		}
	});

	$("#bigscreen_theme_detail_close_btn").click(function() {
		reloadFun();		
		return false;
	});

	$("#bigscreen_theme_update_close_btn").click(function() {
		reloadFun();		
		return false;
	});
});