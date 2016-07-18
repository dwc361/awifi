package org.roof.excel.service;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * Excel公式服务类
 * @author hongzc
 *
 */
public class ExcelFormulaService {

	public String getExcelHead() {
		String head = "";
		for (int i = 0; i < 26; i++) {
			char x = (char) (i + 65);
			head += x;
		}
		// for (int i = 0; i < 26; i++) {
		// char x = (char) (i + 65);
		// head += "A" + x;
		// }
		return head;
	}

	private String calcNewFormula(int startRow, int rowNum, int lastColNum, String formula) {
		String head = this.getExcelHead();
		String[] arr = new String[head.length()];
		for (int x = 0; x < lastColNum; x++) {
			arr[x] = head.substring(x, x + 1) + (startRow + 1);
		}
		for (int x = 0; x < lastColNum; x++) {
			formula = formula.replace(arr[x], head.substring(x, x + 1) + (rowNum + 1));
		}
		return formula;
	}

	public HSSFSheet createNewExcel(HSSFSheet sheet, int startRow, int length) {
		HSSFRow row = (HSSFRow) sheet.getRow(startRow);
		if (row == null) {
			row = sheet.createRow(startRow);
		}
		int lastColNum = row.getLastCellNum();
		for (int rowNum = startRow; rowNum < (startRow + length); rowNum++) {
			HSSFRow rowNew = sheet.createRow(rowNum);
			for (int colNum = 0; colNum < lastColNum; colNum++) {
				HSSFCell cellNew = rowNew.createCell(colNum);
				HSSFCell cell = row.getCell(colNum);
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_FORMULA:
					// 读取公式
					String formula = cell.getCellFormula();
					cellNew.setCellFormula(this.calcNewFormula(startRow, rowNum, lastColNum, formula));
					break;
				}
			}
		}
		return sheet;
	}

}
