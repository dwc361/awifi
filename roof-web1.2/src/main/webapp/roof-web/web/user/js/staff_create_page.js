var treeObj = null;
$(document).ready(function() {
	$('#mainForm').validate({
		rules : {
			'staff.name' : {
				required : true
			},
			'staff.username' : {
				required : true,
				remote : {
					url : "staffAction!sameUsername.ajax",
					type : "post",
					dataType : "json",
					data : {
						'id' : function() {
							return $('input[name="staff.id"]').val();
						},
						'username' : function() {
							return $('input[name="staff.username"]').val();
						}
					}
				}
			},
			'staff.password' : {
				required : true
			},
			'repassword' : {
				required : true,
				equalTo : 'input[name="repassword"]'
			}
		},
		messages : {
			"staff.username" : {
				remote : "<b>该用户名已被注册</b>"
			}
		}
	});

	$('#mainForm').ajaxForm({
		'dataType' : 'json',
		'clearForm' : true,
		'success' : function(d) {
			ROOF.Utils.hideBlock();
			ROOF.Utils.alert(d.message, function() {
				if($('#backBtn').attr('href')){
					window.location.href = $('#backBtn').attr('href');
				}
			});
		},
		'beforeSerialize' : function() {
			var password = $('input[name="staff.password"]');
			var repassword = $('input[name="repassword"]');
			if (!hex_md5_tip(password.val()).toUpperCase()) {
				password.focus();
				return false;
			} else if (!hex_md5_tip(repassword.val()).toUpperCase()) {
				repassword.focus();
				return false;
			} else if (password.val() != repassword.val()) {
				alert("密码和重复密码不一致");
				repassword.select();
				return false;
			}
			password.val(hex_md5_tip(password.val()).toUpperCase());
			repassword.val(hex_md5_tip(repassword.val()).toUpperCase());
		},
		'beforeSubmit' : function(arr, $form, options) {
			$('#staff_role_table :checkbox').each(function(i, n) {
				var id = 'staff.roles[' + i + '].id';
				arr.push({
					name : id,
					value : $(n).val()
				});
			});
			ROOF.Utils.showBlock();
		}
	});

	$('#saveBtn').click(function() {
		$('#mainForm').submit();
		return false;
	});

	var setting = {
		async : {
			enable : true,
			url : "orgAction!read.ajax",
			autoParam : [ "id" ]
		},
		view : {
			selectedMulti : false
		}
	};
	treeObj = $.fn.zTree.init($('.ztree'), setting);

	$("#org_select_btn").button().click(function(event) {
		$("#org_select_dialog").dialog("open");
	});

	$("#org_select_dialog").dialog({
		autoOpen : false,
		width : 400,
		height : 300,
		modal : true,
		resizable : false,
		buttons : {
			"确定" : function() {
				var selectNode = getSelected();
				if (selectNode) {
					$('input[name="staff.org.org_name"]').val(selectNode.name);
					$('input[name="staff.org.org_id"]').val(selectNode.id);
					$(this).dialog("close");
				}
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		}
	});

});

function getSelected() {
	var nodes = treeObj.getSelectedNodes();
	if (nodes.length > 0) {
		return nodes[0];
	}
	return null;
}