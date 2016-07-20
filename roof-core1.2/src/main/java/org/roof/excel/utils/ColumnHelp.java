package org.roof.excel.utils;

public class ColumnHelp {

	/**
	 * 从0开始
	 * 
	 * @param columnName
	 *            A
	 * @return
	 */
	public static int change(String columnName) {
		columnName = columnName.trim().toUpperCase();
		int resVal = -1;
		for (int i = 0; i < columnName.length(); i++) {
			int b = (int) Math.pow(26, columnName.length() - 1 - i);
			resVal += (columnName.charAt(i) - 64) * b;
		}
		return resVal;
	}

	/**
	 * 从0开始
	 * 
	 * @param columnIndex
	 *            0
	 * @return
	 */
	public static String change(int columnIndex) {
		int times = columnIndex / 26;
		String t = "";
		if (times > 0) {
			for (int i = 0; i < 1; i++) {
				t += String.valueOf((char) (times - i - 1 + 65));
			}
		}
		t += String.valueOf((char) ((columnIndex - 26 * times) + 65));
		return t;
	}

}
