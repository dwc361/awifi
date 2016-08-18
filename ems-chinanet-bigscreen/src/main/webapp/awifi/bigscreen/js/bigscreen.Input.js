ROOF.Utils.ns("bigscreen.Input");

bigscreen.Input = ROOF.Class(bigscreen.Control, {
	text : null,
	name : null,
	sign : null,
	index :null,
	initialize : function(name, text, sign,index) {
		this.name = name;
		this.text = text;
		this.sign = sign;
		this.index = index;
		this.dom = $('<input type="hidden" sign="' + sign + '" value="' + text
				+ '" name="' + name + '" />');
	},
	getText : function() {
		return this.text;
	},
	getName : function() {
		return this.name;
	}
});