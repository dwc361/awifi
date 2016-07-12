function loginFavorites(obj) {
	$("#inputPassword").val("");
	$("#sub_href").val("");
	$("#subUser_name").val("");
	var aObj = $(obj);
	if (aObj.attr("rel") == "true") {
		$("#subUser_name").val(aObj.attr("lang"));
		$("#sub_href").val(aObj.attr("title"));
		$("#apptip").html("本次登录账号："+aObj.attr("lang"));
		$("#pwdip").html(aObj.attr("media")+"的密码：");
		$.blockUI({
			message : $('#fillPassword'),
			css : {
				top : "30%",
				left : "35%",
				width : "",
				height : "",
				border : "0"
			}
		});
		return false;
	}
	loginSubmit(obj);
}

function loginByPwd() {
	var app_path = $("#sub_href").val();
	var sub_user_no = $("#subUser_name").val();
	var userPwd = $("#inputPassword").val();
	if (!userPwd) {
		alert("请输入密码!");
		return false;
	}
	$.unblockUI();
	if (app_path && sub_user_no) {
		var a = "<a title=" + app_path + " lang=" + sub_user_no + "></a>";
		loginSubmit(a);
	}
}

function loginSubmit(obj) {
	var aObj = $(obj);
	var userPwd = $("#inputPassword").val();
	if ((aObj.attr("rel") == "true") && (!userPwd)) {
		alert("请输入密码!");
		return false;
	}
	$("#inputPassword").val("");
	$("#sub_href").val("");
	$("#subUser_name").val("");
	$.ajax({
		type : "POST",
		url : "uacAction!putSubUser.ajax",
		dataType : "json",
		data : {
			"subUser.name" : aObj.attr("lang"),
			"href" : aObj.attr("title"),
			"userPwd" : userPwd
		},
		error : function(msg) {
			alert("Ajax调用失败");
		},
		success : function(result) {
			if (result.state == "success") {
				// window.open(aObj.attr("title"));
				//window.top.location.href = aObj.attr("title");
				var isIe=(document.all)?true:false;
				if(isIe) {
				var linka = document.createElement('a');
				linka.href=aObj.attr("title");
				document.body.appendChild(linka);
				linka.target="_top";
				linka.click();
				}else {
					window.top.location.href = aObj.attr("title");
				}
			}
		}
	});
}