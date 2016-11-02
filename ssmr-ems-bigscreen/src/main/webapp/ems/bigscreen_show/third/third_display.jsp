<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <title>大屏前台三层监控</title>
    <%@include file="/ems/bigscreen_show/bigscreen_show_head.jsp"%>
    <link rel="stylesheet" href="${basePath}/ems/bigscreen_show/third/css/screen_3.css">
    <link href="//cdn.bootcss.com/tether/1.3.6/css/tether.min.css" rel="stylesheet">
    <script src="//cdn.bootcss.com/tether/1.3.6/js/tether.min.js"></script>
    <script type="text/javascript">
        $(function() {
            connect();
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
    <div id="thirdScreen_show" class="container"></div>
    <script src="${basePath}/react/build/thirdScreen.js"></script>
</body>
</html>
