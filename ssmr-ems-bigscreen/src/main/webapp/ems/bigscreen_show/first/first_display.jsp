<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <title>一层监控</title>
    <%@include file="/ems_common/ems_head_boot.jsp"%>
    <!-- bootstrap样式 -->
    <link rel="stylesheet" href="${basePath}/ems/bigscreen_backstage/main/css/bootstrap.min.css">
    <!-- 自定义样式和js -->
    <link rel="stylesheet" href="${basePath}/ems/bigscreen_show/first/css/firstPro.css">
    <script type="text/javascript">
        var wordList = ${wordList};
        $(function() {
            var src =ROOF.Utils.projectName() + "/ems/bigscreen_show/first/svg/"+wordList[0].name;
            $("#welobject").append("<object data='"+src+"' type='image/svg+xml' width='100%' height='100%' />");
            for(var i=0;i<wordList.length;i++){
                if(wordList[i].type == "1"){
                    $("#welWord_div").show();
                    $("#welWordid").val(wordList[i].wordid);
                    $("#celWord_div").hide();
                    $("#annWord_div").hide();
                }else if(wordList[i].type == "2"){
                    $("#celWord_div").show();
                    $("#celWordid").val(wordList[i].wordid);
                    $("#welWord_div").hide();
                    $("#annWord_div").hide();
                }else{
                    $("#annWord_div").show();
                    $("#annWordid").val(wordList[i].wordid);
                    $("#welWord_div").hide();
                    $("#celWord_div").hide();
                }
            }
            document.getElementsByTagName('textarea')[0].onkeypress = function () {
                var value = this.value;
                var allLine = value.split('\n');
                var lastLine = allLine.pop();

                if (lastLine.length >= 15) {
                    lastLine = lastLine.substr(0, 15) + '\n' + lastLine.substr(15);
                    allLine.push(lastLine);
                    this.value = allLine.join('\n');
                }
            };
        });

    </script>
</head>
<body>
    <div id="welobject"></div>
    <div id="welWord_div"><input id="welWordid" type="text" /></div>
    <div id="celWord_div"><input id="celWordid" type="text" /></div>
    <div id="annWord_div"><textarea id="annWordid" ></textarea></div>
</body>
</html>