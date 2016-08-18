$(function() {
	
	$('#manage_bigscreenChartRel_form').validate({
		rules : {
			'id' : {
				number : true,
				required : true
			}, 
			'chart_id' : {
				number : true,
				required : true
			}, 
			'screen_id' : {
				number : true,
				required : true
			}, 
			'target' : {
				required : true
			}
		},
		messages : {
			'id' : {
				required : "id必填"
			}, 
			'chart_id' : {
				required : "chart_id必填"
			}, 
			'screen_id' : {
				required : "screen_id必填"
			}, 
			'target' : {
				required : "target必填"
			}
		},
		errorPlacement : com.letv.errorpacement
	});
	$("#manage_bigscreenChartRel_form").ajaxForm({
		'type' : 'post',
		'cache' : false,
		'dataType' : 'json',
		'clearForm' : true,
		'success' : function(d) {
			alert(d.message);
			$("#manage_bigscreenChartRel_update_close_btn").click();
		},
		'error' : function(d) {
			alert(d.statusText);
		}
	});

	$("#manage_bigscreenChartRel_detail_close_btn").click(function() {
		reloadFun();		
		return false;
	});

	$("#manage_bigscreenChartRel_update_close_btn").click(function() {
		reloadFun();		
		return false;
	});
});