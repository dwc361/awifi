<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>WebSocket/SockJS Echo Sample</title>
		<style type="text/css">
			#connect-container {
				float: left;
				width: 400px
			}
			
			#connect-container div {
				padding: 5px;
			}
			
			#console-container {
				float: left;
				margin-left: 15px;
				width: 400px;
			}
			
			#console {
				border: 1px solid #CCCCCC;
				border-right-color: #999999;
				border-bottom-color: #999999;
				height: 170px;
				overflow-y: scroll;
				padding: 5px;
				width: 100%;
			}
			
			#console p {
				padding: 0;
				margin: 0;
			}
		</style>
		
		<script src="http://cdn.bootcss.com/jquery/2.2.3/jquery.js"></script>
		<script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
		<script type="text/javascript" src="${basePath}/common/js/ROOF.Utils.js"></script>
		<script type="text/javascript">
			var ws = null;
		
			$(function() {
				connect();
				
				console.log(isDigit(1));
			});
		
			function setConnected(connected) {
				document.getElementById('connect').disabled = connected;
				document.getElementById('disconnect').disabled = !connected;
				document.getElementById('echo').disabled = !connected;
			}
		
			// 建立连接
			function connect() {
				//ws = new WebSocket("ws://127.0.0.1:8080/ems-chinanet-bigscreen/WebSocketServlet/websocket");
				ws = new SockJS(ROOF.Utils.projectName() + "/WebSocketServlet/websocket/sockjs");
		
				ws.onopen = function() {
					setConnected(true);
					log('Info: connection opened.');
				};
				ws.onmessage = function(event) {
					console.log(event);
					log('Received: ' + event.data);
					var url = event.data;
					alert(isUrl(url));
					if(isUrl(url)) {
						// 直接跳转
						window.location.href = url;
						// 定时跳转
						//setTimeout("javascript:location.href='" + url + "'", 5000); 
					}
				};
				ws.onclose = function(event) {
					setConnected(false);
					log('Info: connection closed.');
					log(event);
				};
			}
		
			// 断开连接
			function disconnect() {
				if (ws != null) {
					ws.close();
					ws = null;
				}
				setConnected(false);
			}
		
			// 发送消息
			function echo() {
				if (ws != null) {
					var message = document.getElementById('message').value;
					log('Sent: ' + message);
					ws.send(message);
				} else {
					alert('connection not established, please connect.');
				}
			}
		
			// 显示消息
			function log(message) {
				var console = document.getElementById('console');
				var p = document.createElement('p');
				p.style.wordWrap = 'break-word';
				p.appendChild(document.createTextNode(message));
				console.appendChild(p);
				while (console.childNodes.length > 25) {
					console.removeChild(console.firstChild);
				}
				console.scrollTop = console.scrollHeight;
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
		<div>
			<br>
			<div id="connect-container">
				<div>
					<button id="connect" onclick="connect();">Connect</button>
					<button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
				</div>
				<div>
					<textarea id="message" style="width: 350px">Here is a message!</textarea>
				</div>
				<div>
					<button id="echo" onclick="echo();">Echo message</button>
				</div>
			</div>
			<div id="console-container">
				<div id="console"></div>
			</div>
		</div>
	</body>
</html>