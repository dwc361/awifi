package com.zjhcsoft.uac.blj.util;



public class BljResultUntils {
	private static String[] messageArr = new String[] {"成功", "资源不存在", "数据类型错误","批处理操作中的某个数据项操作失败",
		"批处理业务流操作成功，但是某个数据项失败","IP地址格式错误","删除数据失败","批处理操作失败","数据格式错误","必填项为空","数据长度错误",
		"密码不符合当前系统密码格式","服务不存在","资源账号不存在","AD域操作失败","策略已存在","操作失败","只能输入数字","取值范围错误",
		"服务已存在","资源已存在","资源系统类型不存在","资源系统分类不存在","组织结构已存在","用户已存在","元数据不匹配","批处理操作的数据大于200",
		"资源账号已经存在","策略不存在"};
	
	public static String getCodeMessage(int code) {
		/*System.out.println(messageArr.length);
		for(int i = 0;i<messageArr.length;i++){
			System.out.println(i+"="+messageArr[i]);
		}*/
		return messageArr[code];
	}

	
}
