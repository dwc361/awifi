$(function() {
		ROOF.Utils.datepicker("templates_create_time");
		ROOF.Utils.datepicker("templates_update_time");
	
	var height = 380;// 高度按元素需要变更
	var width = 925;
	
	$("#reset").click(function() {
		ROOF.Utils.emptyForm($('#bigscreen_templates_search_form'));
		return false;
	});
	
	$("#serchBtn").click(function(){
		search();
		return false;
	});

	var table = new ROOF.SelectableTable($('#bigscreen_templates_table'));
	var page = new com.letv.PageBar($('#bigscreen_templates_search_form'));
	$("#bigscreen_templates_create_btn").click(function() {
		var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName() + "/awifi/templatesAction/create_page.action", "e_templates管理", width, height, true, true);
		return false;
	});
	$("#bigscreen_templates_detail_btn").click(function() {
		var trs = table.getSelectedTrNoClone();
		if (trs.length == 0 || trs.length > 1) {
			alert("请选择一行记录");
			return false;
		}
		var id = trs[0].find(":input[name='id']").val();
		var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName() + "/awifi/templatesAction/detail_page.action?id=" + id  , "e_templates管理", width, height, true);
		return false;
	});
	$("#bigscreen_templates_update_btn").click(function() {
		var trs = table.getSelectedTrNoClone();
		if (trs.length == 0 || trs.length > 1) {
			alert("请选择一行记录");
			return false;
		}
		var id = trs[0].find(":input[name='id']").val();
		var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName() + "/awifi/templatesAction/update_page.action?id=" + id  , "e_templates管理", width, height, true);
		return false;
	});
	$("#bigscreen_templates_delete_btn").click(function() {
		var trs = table.getSelectedTrNoClone();
		if (trs.length == 0 || trs.length > 1) {
			alert("请选择一行记录");
			return false;
		}
		if(!confirm("确定删除该条记录吗？")){
			return false;
		}
		var id = trs[0].find(":input[name='id']").val();
		$.ajax({
		    async: false,
		    url : basePathConst+"/awifi/templatesAction/delete.action",
			data: {"id":id},
			    type: 'post',
			    dataType: 'json',
			    cache: false,
			    beforeSubmit: function(formData){
		   	},
			beforeSend: function(){
			},
		  	complete: function(){
		   	},
			error: function(){
			    alert('Ajax信息加载出错，请重试！');
			},
			success: function(d){
				alert(d.message);
				window.location.reload();
			}
		});
	});
	
});