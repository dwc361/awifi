ROOF.SelectableTable = ROOF.Class({
	target : null,
	checkbox : false,
	initialize : function(table, checkbox) {
		this.target = table;
		if (checkbox) {
			this.checkbox = checkbox;
		}
		table.find('tr:gt(0)').live("click",function(e) {
			if (!checkbox) {
				table.find("tr").each(function() {
					$(this).find('input:eq(0)').removeAttr('checked');
				});
				table.find('tr:gt(0)').not($(this)).find('td').css('background', '#FFFFFF');
			}
			var $checkbox = $(this).find(':checkbox:eq(0)');
			if ($checkbox.attr("checked")) {
				$(this).find('td').css('background', '#FFFFFF');
				$checkbox.removeAttr("checked");
			} else {
				$(this).find('td').css('background', '#E7EEF7');
				$checkbox.attr("checked", "checked");
			}
			table.trigger("trSelectedEvent");
		});
	},
	getSeleted : function() {
		if (!this.checkbox) {
			return this.target.find(':checked').val();
		}
		var result = new Array();
		this.target.find(':checked').each(function() {
			result.push($(this).val());
		});
		return result;
	},
	getSelectedTr : function() {
		var result = new Array();
		this.target.find(':checked').each(function() {
			result.push($(this).parent().parent().clone(true));
		});
		return result;
	},
	getSelectedData : function() {
		var result = new Array();
		var trs = this.getSelectedTrNoClone();
		$.each(trs, function(i, n) {
			var o = {};
			$.each($(n).find("td"), function(j, m){
				var name = $(m).attr("name");
				if(name) {
					o[name] = $(m).html();
				}
			});
			result.push(o);
		});
		return result;
	},
	getSelectedTrNoClone : function() {
		var result = new Array();
		this.target.find(':checked').each(function() {
			result.push($(this).parent().parent());
		});
		return result;
	}
});