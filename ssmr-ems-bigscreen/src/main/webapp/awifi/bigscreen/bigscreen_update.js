$(function() {
		ROOF.Utils.datepicker("bigscreen_create_time");
	ROOF.Utils.datepicker("bigscreen_update_time");

	$('#bigscreen_bigscreen_form').validate({
		rules : {
			'template_id' : {
				number : true,
				required : true
			}, 
			'theme_id' : {
				number : true,
				required : true
			}
		},
		messages : {
			'template_id' : {
				required : "模板id必填"
			}, 
			'theme_id' : {
				required : "主题id必填"
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