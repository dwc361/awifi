package com.zjhcsoft.uac.cxf.impl;

public class BljConstants {
	public static int code1 = 1;
	public static int code2 = 2;
	public static int code3 = 3;
	public static int code4 = 4;
	public static String SERVICEID = "AIUAP";
	public static int sussces = 0;
	public static int error = -1;
	private static String[] messageArr = new String[] { "", "成功", "失败","000","000" };

	public static String getCodeMessage(int code) {
		return messageArr[code];
	}

}
