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
    <%@include file="/ems/bigscreen_show/bigscreen_show_head.jsp"%>
    <link rel="stylesheet" href="${basePath}/ems/bigscreen_backstage/main/css/bootstrap.min.css">
    <link rel="stylesheet" href="${basePath}/ems/bigscreen_show/first/css/firstPro.css">
    <script type="text/javascript">
        var wordList = ${wordList};
        $(function() {
            connect();
            
            var src =ROOF.Utils.projectName() + "/ems/bigscreen_show/first/svg/"+wordList[0].name;
            $("#object").append("<object data='"+src+"' type='image/svg+xml' width='100%' height='100%' />");
            $("#wordid").val(wordList[0].wordid);
        });

        // 建立连接
        function connect() {
            //ws = new WebSocket("ws://127.0.0.1:8080/ssmr-ems-bigscreen/WebSocketServlet/websocket");
            ws = new SockJS(ROOF.Utils.projectName() + "/WebSocketServlet/websocket/sockjs");
    
            ws.onopen = function() {
                //console.log('Info: connection opened.');
            };

            ws.onmessage = function(event) {
                //console.log('Received: ' + event.data);
                var url = event.data;
                if(isUrl(url)) {
                    echo("切换成功！");
                    // 直接跳转
                    window.location.href = url;
                    // 定时跳转
                    //setTimeout("javascript:location.href='" + url + "'", 5000); 
                }
            };

            ws.onclose = function(event) {
                //console.log('Info: connection closed.');
            };
        }
    
        // 断开连接
        function disconnect() {
            if (ws != null) {
                ws.close();
                ws = null;
            }
        }
    
        // 发送消息
        function echo(message) {
            if (ws != null) {
                ws.send(message);
            } else {
                alert('connection not established, please connect.');
            }
        }
        
        // 使用正则表达式判断是否为URL
        function isUrl(url) {
            var reg = /^(\w+):\/\/([^\/:]*)(?::(\d+))?\/([^\/]*)(\/.*)/;
            if (!reg.exec(url))
                return false
            return true
        }
    </script>
</head>
<body>

    <div id="object"></div>
    <div id="word_div"><input id="wordid" type="text" /></div>
</body>
</html>