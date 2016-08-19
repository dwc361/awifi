ROOF.Utils.ns("bigscreen.InputList");

bigscreen.InputList = ROOF.Class(bigscreen.Control, {
	inputList : new Array(),
	addInput : null,
	index : 0, // 计数器
	length : 0,
	chart_id : null,
	chart_path : null,
	sign : null,
	initialize : function(dom) {
		this.dom = dom;
	},
	addChartInput : function(chart_id,chart_path, sign) {
		this.chart_id = chart_id;
		this.chart_path = chart_path;
		// 新增chart的
		var inputId = new bigscreen.Input("vos[" + this.index + "].chart_id",chart_id, sign,this.index);
		var inputPath = new bigscreen.Input("vos[" + this.index + "].chart_path",
				chart_path, sign,this.index);
		var inputTarget = new bigscreen.Input("vos[" + this.index + "].target_name",
				"part_" + sign, sign,this.index);
		this.dom.append(inputId.getDom());
		this.dom.append(inputPath.getDom());
		this.dom.append(inputTarget.getDom());
		this.inputList.push(inputId);
		this.inputList.push(inputPath);
		this.inputList.push(inputTarget);
		this.index++;
		this.length++;
	},
	delChartInput : function(sign) {
		var inputs = this.dom.find('input[sign="'+sign+'"]');
		console.log(inputs);
		if($.isEmptyObject(inputs)){
			return false;
		}
		$.each(inputs, $.proxy(function(i, n) {
			n.remove();
//			this.inputList.
		}, this));
		this.length--;
	},
	getInputList(){
		return this.inputList;
	}
});