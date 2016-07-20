package org.roof.safety.service;

import java.io.File;
import java.io.IOException;

import org.roof.commons.RoofMD5Utils;

/**
 * 请调用 RoofMD5Utils
 * 
 */
@Deprecated
public class MD5Util {

	/**
	 * 适用于上G大的文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file) throws IOException {
		return RoofMD5Utils.getFileMD5String(file);
	}

	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	public static String getMD5String(byte[] bytes) {
		return RoofMD5Utils.getMD5String(bytes);
	}

	public static boolean checkPassword(String password, String md5PwdStr) {
		return RoofMD5Utils.checkPassword(password, md5PwdStr);
	}
}