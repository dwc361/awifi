$(function() {
		ROOF.Utils.datepicker("theme_create_time");
		ROOF.Utils.datepicker("theme_update_time");
	
	var height = 380;// 高度按元素需要变更
	var width = 925;
	
	$("#reset").click(function() {
		ROOF.Utils.emptyForm($('#bigscreen_theme_search_form'));
		return false;
	});
	
	$("#serchBtn").click(function(){
		search();
		return false;
	});

	var table = new ROOF.SelectableTable($('#bigscreen_theme_table'));
	var page = new com.letv.PageBar($('#bigscreen_theme_search_form'));
	$("#bigscreen_theme_create_btn").click(function() {
		var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName() + "/awifi/themeAction/create_page.action", "主题管理", width, height, true, true);
		return false;
	});
	$("#bigscreen_theme_detail_btn").click(function() {
		var trs = table.getSelectedTrNoClone();
		if (trs.length == 0 || trs.length > 1) {
			alert("请选择一行记录");
			return false;
		}
		var id = trs[0].find(":input[name='id']").val();
		var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName() + "/awifi/themeAction/detail_page.action?id=" + id  , "主题管理", width, height, true);
		return false;
	});
	$("#bigscreen_theme_update_btn").click(function() {
		var trs = table.getSelectedTrNoClone();
		if (trs.length == 0 || trs.length > 1) {
			alert("请选择一行记录");
			return false;
		}
		var id = trs[0].find(":input[name='id']").val();
		var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName() + "/awifi/themeAction/update_page.action?id=" + id  , "主题管理", width, height, true);
		return false;
	});
	$("#bigscreen_theme_delete_btn").click(function() {
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
		    url : basePathConst+"/awifi/themeAction/delete.action",
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