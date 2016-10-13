import React from 'react';
import ReactDOM from 'react-dom';
import ReactMixin from 'react-mixin';
import Reflux from 'reflux'

import store from '../../stores/second-store'
import actions from '../../actions/second-actions'
import BigscreenPart from './BigscreenPart.jsx';
import SourceFillBigscreen from './SourceFillBigscreen.jsx';
import DragChart from './DragChart.jsx';
import AddChart from './AddChart.jsx';

export default class Second extends React.Component {

	constructor(props){
    	super(props);
    	this.state = {chartList:[], relList:[], bigscreen_id:"", theme_id:"", templates_id:"", templates_path:""}
  	}

	addChart() {
		var chartList = this.state.chartList;
		if(typeof(chartList) != 'undefined') {
		    ReactDOM.render(
		        <div style={{width:"100%",height:"100%"}}>
		            {
		                chartList.map(function (obj, i) {
		                    return <DragChart key={i} id={obj.id} path={obj.path} title={obj.name} url={obj.icon} style={{display:"inline-block"}} />
		                })
		            }
		            <AddChart />
		        </div>,
		        document.getElementById('chartCnt'));
		}
	}

	addBigscreenPart() {
		const partIds=[
		    "part_1_1", "map_part_1_2", "map_part_1_3","map_part_1_4","part_1_5",
		    "part_2_1", "map_part_2_2", "map_part_2_3","map_part_2_4","part_2_5",
		    "part_3_1", "map_part_3_2", "map_part_3_3","map_part_3_4","part_3_5",
		    "part_4_1", "map_part_4_2", "map_part_4_3","map_part_4_4","part_4_5"
		];
		ReactDOM.render(
		    <div style={{width:"100%",height:"100%"}}>
		        {
		            partIds.map(function(obj, i) {
		                return <BigscreenPart key={i} index={i} id={obj} />
		            })
		        }
		    </div>,
		    document.getElementById('screen'));
	}

	fillBigscreenPart() {
		var relList = this.state.relList;
		if(typeof(relList) != 'undefined') {
		    for(var i=0; i<relList.length; i++) {
		        if(document.getElementById(relList[i].target) != null) {
		            ReactDOM.render(<SourceFillBigscreen id={relList[i].chart_id} path={relList[i].chart_path} 
		                url={relList[i].chart_icon} target={relList[i].target} title={relList[i].chart_title} />, 
		                    document.getElementById(relList[i].target));
		        }
		    };
		}
	}

	jqueryOnReady() {
		// 配置里的图表移除
	    $("#screen").find("[id^='part']").mouseenter(function(){
	        $(this).find("i").show();
	    });
	    $("#screen").find("[id^='part']").mouseleave(function(){
	        $(this).find("i").hide();
	    });
	    $("#screen").find("[id^='part']").find(".part-clean").click(function () {
	        $(this).parent().remove();
	    });

	    // 修改name属性(SpringMVC里面BigScreenModel.List<BigScreenHandlebarsVo>.target_name接收需要name="vos[i].target_name")
	    $("#screen").find("[id^='part']").each(function(i) {
	        $(this).find("#chart_id").attr("name", "vos["+$(this).attr("value")+"].chart_id");
	        $(this).find("#chart_path").attr("name", "vos["+$(this).attr("value")+"].chart_path");
	        $(this).find("#target_name").attr("name", "vos["+$(this).attr("value")+"].target_name");
	    });

	    // jquery-ui拖拽-源dom设置拖拽属性
	    $(".draggable").draggable({
	        helper:"clone",
	        cursor: "move",
	        revert: "true"
	    });

	    // jquery-ui拖拽-目标dom设置拖拽属性
	    $("#screen").find("[id^='part']").droppable({
	        //activeClass: "ui-state-hover",
	        //hoverClass: "ui-state-active",
	        drop:function(event,ui){
	            // 清空当前容器
	            $(this).children().remove();

	            // 克隆到容器中
	            var source = ui.draggable.clone();
	            
	            // 配置里的图表移除
	            $('<i class="part-clean fa fa-close"/>').appendTo(source);
	            source.mouseenter(function () {
	                $(this).find("i").show();
	            });
	            source.mouseleave(function () {
	                $(this).find("i").hide();
	            });
	            $(source).find(".part-clean").click(function () {
	                $(source).remove();
	            });
	            
	            // 移除编辑和删除图标
	            $(source).find(".edit").remove();
	            
	            // 样式调整
	            $(source).find(".heading").css({"width":"100%","height":"16%","background":"#444","color":"#fff","position":"absolute","top":"0"});
	            $(source).find(".part").css({"width":"100%","height":"84%","position":"absolute","top":"16%"});
	            $(source).css({"width":"100%","padding":"0","height":"100%","position":"relative"});
	            $(source).find(".block").css("border","#fff");
	            $(this).append(source);

	            // 将容器的id放入隐藏域
	            $(this).find("#target_name").attr("value", $(this).attr("id")); // 如:part_1_1
	            
	            // 修改name属性(SpringMVC里面BigScreenModel.List<BigScreenHandlebarsVo>.target_name接收需要name="vos[i].target_name")
	            $(this).find("#chart_id").attr("name", "vos["+$(this).attr("value")+"].chart_id");
	            $(this).find("#chart_path").attr("name", "vos["+$(this).attr("value")+"].chart_path");
	            $(this).find("#target_name").attr("name", "vos["+$(this).attr("value")+"].target_name");
	        }
	    });
	    
	    var height = 380;// 高度按元素需要变更
		var width = 925;
		// 图表新增
	    $("#chart_add").click(function() {
	        var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName() + "/awifi/chartAction/create_page.action", "图表管理", width, height, true);
			return false;
	    });
	    
	    // 图表编辑
	    $(".chart_edit").click(function() {
	        var id = $(this).attr("value");
	        var ref = ROOF.Utils.openwindow(ROOF.Utils.projectName() + "/awifi/chartAction/update_page.action?id=" + id, "图表管理", width, height, true);
			return false;
	        alert(id);
	    });
	    
	    // 图表删除
	    $(".chart_delete").click(function() {
			if(!confirm("确定删除该条记录吗？")){
				return false;
			}
			var id = $(this).attr("value");
			$.ajax({
			    async: false,
			    url : basePathConst + "/awifi/chartAction/delete.action",
				data: {"id":id},
				    type: 'post',
				    dataType: 'json',
				    cache: false,
				    beforeSubmit: function(formData){
			   	},
				beforeSend: function() {},
			  	complete: function() {},
				error: function(){
				    alert('Ajax信息加载出错，请重试！');
				},
				success: function(d){
					alert(d.message);
					window.location.reload();
				}
			});
	    });
	}
	
	// 保存配置
	saveBigscreenSecondData() {
	    $('#bigscreen').validate({
			rules : {
				'id' : {
					required : true
				}
			},
			messages : {
				'id' : {
					required : "主键不能为空"
				}
			},
			errorPlacement : com.letv.errorpacement
		});
		
    	actions.saveBigscreenSecondData($("#bigscreen").serialize());
  	}
	
	// 预览
  	preview() {
  		$("#bigscreen").attr("action", ROOF.Utils.projectName() + "/ems/bigscreen_backstage/SecondBackstageAction/preview.action");	
		$("#bigscreen").attr("target", "_blank");	
		$("#bigscreen").submit();
		$("#bigscreen").attr("action", "");	
		$("#bigscreen").attr("target", "_self");
  	}

	// 在第一次渲染后调用，只在客户端。之后组件已经生成了对应的DOM结构，可以通过this.getDOMNode()来进行访问。
	// 如果你想和其他JavaScript框架一起使用，可以在这个方法中调用setTimeout, setInterval或者发送AJAX请求等操作(防止异部操作阻塞UI)。
	componentDidMount() {
		actions.getBigscreenSecondData();
	}
	
	// 在组件接收到新的props或者state但还没有render时被调用。在初始化时不会被调用。
	componentWillUpdate() {}
	
	// 在组件完成更新后立即调用。在初始化时不会被调用。
	componentDidUpdate() {
		this.addChart();

		this.addBigscreenPart();

		this.fillBigscreenPart();
		
		this.jqueryOnReady();
	}

	render() {
		return (
			<div id="content">
				<form id="bigscreen" className="form-inline" method="post" action="" target="_self">
					<input type="hidden" name="id" value={this.state.bigscreen_id} />
					<input type="hidden" name="theme.id" value={this.state.theme_id} />
					<input type="hidden" name="templates.id" value={this.state.templates_id} />
					<input type="hidden" name="templates.path" value={this.state.templates_path} />
					<div id="right">
						<div className="handle">
			               <span>
			                  <i id="save" className="fa fa-folder-open" onClick={this.saveBigscreenSecondData}></i>
			                  <i id="preview" className="fa fa-eye" onClick={this.preview}></i>
			               </span>
			            </div>
						<div id="screen" className="col-sm-12 col-md-12 col-lg-12"></div>
					</div>
				</form>
				<div id="left">
					<div id="title">
						<h2>配置图表</h2>
					</div>
					<div id="chartCnt"></div>
				</div>
			</div>
		);
	}
}

// ES6 mixin写法，通过mixin将store的与组件连接，功能是监听store带来的state变化并刷新到this.state
ReactMixin.onClass(Second, Reflux.connect(store));