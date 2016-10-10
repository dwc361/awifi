/**
 * Created by xuqiying on 16/9/30.
 */

$(function() {


    // 配置里的图表移除
    $("#screen").find("[id^='part']").mouseenter(function(){
        $(this).find("i").show();
    });
    $("#screen").find("[id^='part']").mouseleave(function(){
        $(this).find("i").hide();
    });
    $("#screen").find("[id^='part']").find(".part-clean").click(function () {
        $(this).parent().empty();
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

    // 保存配置
    $('#bigscreen').validate({
        rules : {
            'name' : {
                required : true
            }
        },
        messages : {
            'name' : {
                required : "名称必填"
            }
        },
        errorPlacement : com.letv.errorpacement
    });
    var ajaxFormOption = {
        url: ROOF.Utils.projectName() + "/ems/bigscreen_backstage/MainBackstageAction/save.action",
        type : 'post',
        cache : false,
        dataType : 'json',
        clearForm : true,
        success : function(d) {
            alert(d.message);
            window.location.reload();
        }
    }
    $("#save").click(function() {
        $("#bigscreen").ajaxSubmit(ajaxFormOption);
        return false;
    })

    // 预览
    $("#preview").click(function() {
        $("#bigscreen").attr("action", ROOF.Utils.projectName() + "/ems/bigscreen_backstage/MainBackstageAction/preview.action");
        $("#bigscreen").attr("target", "_blank");
        $("#bigscreen").submit();
        $("#bigscreen").attr("action", "");
        $("#bigscreen").attr("target", "_self");
        return false;
    })
})