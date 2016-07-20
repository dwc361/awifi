package com.zjhcsoft.uac.cxf.impl;

public class CxfConstants {
	public static int code1 = 1;
	public static int code2 = 2;
	private static String[] messageArr = new String[] { "", "成功", "失败" };

	public static String getCodeMessage(int code) {
		return messageArr[code];
	}

}
