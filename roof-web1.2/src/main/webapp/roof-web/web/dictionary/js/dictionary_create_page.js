$(document).ready(function() {
	ROOF.Utils.ajaxcommon();
	$('#mainForm').validate({
		rules : {
			'dictionary.type' : {
				required : true
			},
			'dictionary.val' : {
				required : true
			},
			'dictionary.text' : {
				required : true
			}
		}
	});
	$('#mainForm').ajaxForm({
		'dataType' : 'json',
		'clearForm' : true,
		'success' : function(d) {
			ROOF.Utils.alert(d.message, function() {
				if (parent.reAsyncChildNodes) {
					parent.reAsyncChildNodes();
				}
				var node = parent.getSeletedNode();
				if (node) {
					window.location.href = "dictionaryAction!detail.action?id=" + node.id;
				}
			});
		}
	});

	$('#saveBtn').click(function() {
		$('#mainForm').submit();
		return false;
	});

});