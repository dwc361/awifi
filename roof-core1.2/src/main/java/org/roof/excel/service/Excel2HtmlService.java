package org.roof.excel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Excel表格转换成html代码
 * 
 * @author hongzc
 * 
 */
public class Excel2HtmlService {

	private HSSFWorkbook workbook = null;

	public void initExcel(String path) {
		try {
			File sourcefile = new File(path);
			InputStream is = new FileInputStream(sourcefile);
			POIFSFileSystem fs = new POIFSFileSystem(is);
			workbook = new HSSFWorkbook(fs);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过Excel的全路径，得到转换之后的HTML table<br/>
	 * 适用于直接将Excel转换
	 * 
	 * @param path
	 * @return
	 */
	public String getExcelHtml(String path) {
		String result = "";
		try {
			this.initExcel(path);
			result = this.getExcelInfo(workbook);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 适用于填充数据之后转换
	 * 
	 * @return
	 */
	public String getExcelHtml() {
		String result = "";
		try {
			if (workbook == null) {
				return "";
			}
			result = this.getExcelInfo(workbook);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 适用于填充数据之后转换
	 * 
	 * @return
	 */
	public String getExcelHtml(Sheet sheet) {
		String result = "";
		try {
			if (sheet == null) {
				return "";
			}
			result = this.getExcelInfo(sheet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 往Excel填充数据
	 * 
	 * @param rowIndex
	 *            行从0开始
	 * @param cellnum
	 *            列从0开始
	 * @param value
	 */
	public void setExcelCellNumberValue(String rowIndex, String cellnum, String value) {
		HSSFRow row = this.getSheet0().getRow(Integer.valueOf(rowIndex));// 行从0开始
		if (row == null) {
			row = this.getSheet0().createRow(Integer.valueOf(rowIndex));
		}
		HSSFCell cell = row.getCell(Integer.valueOf(cellnum));// 列从0开始
		if (cell == null) {
			cell = row.createCell(Integer.valueOf(cellnum));
		}
		try {
			cell.setCellValue(Double.valueOf(value));
		} catch (Exception e) {
			cell.setCellValue(value);
		}
	}

	/**
	 * @param rowIndex
	 *            行从0开始
	 * @param cellnum
	 *            列从0开始
	 * @param value
	 */
	public void setExcelCellStringValue(int rowIndex, int cellnum, String value) {
		this.setExcelCellStringValue(rowIndex + "", cellnum + "", value);
	}

	/**
	 * @param rowIndex
	 *            行从0开始
	 * @param cellnum
	 *            列从0开始
	 * @param value
	 */
	public void setExcelCellStringValue(String rowIndex, String cellnum, String value) {
		HSSFRow row = this.getSheet0().getRow(Integer.valueOf(rowIndex));// 行从0开始
		if (row == null) {
			row = this.getSheet0().createRow(Integer.valueOf(rowIndex));
		}
		HSSFCell cell = row.getCell(Integer.valueOf(cellnum));// 列从0开始
		if (cell == null) {
			cell = row.createCell(Integer.valueOf(cellnum));
		}
		cell.setCellValue(value);
	}

	public HSSFSheet getSheet0() {
		if (workbook == null) {
			return null;
		}
		return workbook.getSheetAt(0);
	}

	private String getExcelInfo(Sheet sheet) throws Exception {
		int cellH = 30;
		int cellW = 100;

		StringBuffer sb = new StringBuffer();
		int lastRowNum = sheet.getLastRowNum();

		Map<String, String> map[] = getRowSpanColSpanMap(sheet);

		sb.append("<table class='personTable' border='0' cellspacing='0' width='100%' bgcolor='#000000'>");

		HSSFRow row = null;

		HSSFCell cell = null;

		// System.out.println(sheet.getPhysicalNumberOfRows());
		for (int rowNum = sheet.getFirstRowNum(); rowNum <= lastRowNum; rowNum++) {

			row = (HSSFRow) sheet.getRow(rowNum);

			if (row == null) {

				sb.append("<tr id='Masterplate_row_" + rowNum + "'><td> &nbsp;</td></tr>");

				continue;
			}

			sb.append("<tr id='Masterplate_row_" + rowNum + "' bgcolor='#ffffff'>");

			int lastColNum = row.getLastCellNum();

			for (int colNum = 0; colNum < lastColNum; colNum++) {

				cell = row.getCell(colNum);

				if (cell == null) {

					sb.append("<td id='Masterplate_cell_" + rowNum + "_" + colNum + "' >&nbsp;</td>");

					continue;
				}

				String stringValue = getCellValue(cell);

				String tdStr = "<td id='Masterplate_cell_" + rowNum + "_" + colNum + "' ";

				if (map[0].containsKey(rowNum + "," + colNum)) {

					String pointString = map[0].get(rowNum + "," + colNum);

					map[0].remove(rowNum + "," + colNum);

					int bottomeRow = Integer.valueOf(pointString.split(",")[0]);

					int bottomeCol = Integer.valueOf(pointString.split(",")[1]);

					int rowSpan = bottomeRow - rowNum + 1;

					int colSpan = bottomeCol - colNum + 1;

					if (cellW > 0) {
						sb.append(tdStr + "rowspan= '" + rowSpan + "' colspan= '" + colSpan + "' height='" + cellH
								* rowSpan + "' width='" + cellW * colSpan + "' ");
					} else {
						sb.append(tdStr + "rowspan= '" + rowSpan + "' colspan= '" + colSpan + "' ");
					}

				} else if (map[1].containsKey(rowNum + "," + colNum)) {

					map[1].remove(rowNum + "," + colNum);

					continue;

				} else {
					if (cellW > 0) {
						sb.append(tdStr + "height='" + cellH + "' width='" + cellW + "' ");
					} else {
						sb.append(tdStr);
					}
				}

				HSSFCellStyle cellStyle = cell.getCellStyle();

				if (cellStyle != null) {

					short alignment = cellStyle.getAlignment();

					sb.append("align='" + convertAlignToHtml(alignment) + "' ");

					short verticalAlignment = cellStyle.getVerticalAlignment();

					sb.append("valign='" + convertVerticalAlignToHtml(verticalAlignment) + "' ");

					HSSFFont hf = cellStyle.getFont(workbook);

					short boldWeight = hf.getBoldweight();

					short fontColor = hf.getColor();

					sb.append("style='");

					HSSFPalette palette = workbook.getCustomPalette(); // 类HSSFPalette用于求的颜色的国际标准形式

					HSSFColor hc = palette.getColor(fontColor);

					sb.append("font-weight:" + boldWeight + ";"); // 字体加粗

					// System.out.println(hf.getFontHeight());

					sb.append("font-size: " + hf.getFontHeight() / 2 + "%;"); // 字体大小

					String fontColorStr = convertToStardColor(hc);

					if (fontColorStr != null && !"".equals(fontColorStr.trim())) {

						sb.append("color:" + fontColorStr + ";"); // 字体颜色
					}

					short bgColor = cellStyle.getFillForegroundColor();

					hc = palette.getColor(bgColor);

					String bgColorStr = convertToStardColor(hc);

					if (bgColorStr != null && !"".equals(bgColorStr.trim())) {

						sb.append("background-color:" + bgColorStr + ";"); // 背景颜色
					}

					short borderColor = cellStyle.getBottomBorderColor();

					hc = palette.getColor(borderColor);

					String borderColorStr = convertToStardColor(hc);

					if (borderColorStr != null && !"".equals(borderColorStr.trim())) {

						sb.append("border-color:" + borderColorStr + ";"); // 边框颜色
					}

					// boolean borderBoolean = cellStyle.getWrapText();
					//
					// if(borderBoolean){
					// sb.append("border-style: inset;");
					// }

					sb.append("' ");
				}

				sb.append(">");

				if (stringValue == null || "".equals(stringValue.trim())) {

					sb.append("<span>&nbsp;</span>");
				} else {

					// 将ascii码为160的空格转换为html下的空格（&nbsp;）
					sb.append("<span>" + stringValue.replace(String.valueOf((char) 160), "&nbsp;") + "</span>");

				}

				sb.append("</td>");

			}

			sb.append("</tr>");
		}

		sb.append("</table>");

		return sb.toString();

	}

	private String getExcelInfo(HSSFWorkbook workbook) throws Exception {
		Sheet sheet = this.getSheet0();
		return this.getExcelInfo(sheet);
	}

	@SuppressWarnings("unchecked")
	private Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {

		Map<String, String> map0 = new HashMap<String, String>();
		Map<String, String> map1 = new HashMap<String, String>();

		int mergedNum = sheet.getNumMergedRegions();

		CellRangeAddress range = null;

		for (int i = 0; i < mergedNum; i++) {

			range = sheet.getMergedRegion(i);

			int topRow = range.getFirstRow();

			int topCol = range.getFirstColumn();

			int bottomRow = range.getLastRow();

			int bottomCol = range.getLastColumn();

			map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);

			// System.out.println(topRow + "," + topCol + "," + bottomRow + ","
			// + bottomCol);

			int tempRow = topRow;

			while (tempRow <= bottomRow) {

				int tempCol = topCol;

				while (tempCol <= bottomCol) {

					map1.put(tempRow + "," + tempCol, "");

					tempCol++;
				}

				tempRow++;
			}

			map1.remove(topRow + "," + topCol);

		}

		Map[] map = { map0, map1 };

		return map;
	}

	private String convertAlignToHtml(short alignment) {

		String align = "left";

		switch (alignment) {

		case HSSFCellStyle.ALIGN_LEFT:
			align = "left";
			break;
		case HSSFCellStyle.ALIGN_CENTER:
			align = "center";
			break;
		case HSSFCellStyle.ALIGN_RIGHT:
			align = "right";
			break;

		default:
			break;
		}

		return align;
	}

	private String convertVerticalAlignToHtml(short verticalAlignment) {

		String valign = "middle";

		switch (verticalAlignment) {

		case HSSFCellStyle.VERTICAL_BOTTOM:
			valign = "bottom";
			break;
		case HSSFCellStyle.VERTICAL_CENTER:
			valign = "middle";
			break;
		case HSSFCellStyle.VERTICAL_TOP:
			valign = "top";
			break;
		default:
			break;
		}

		return valign;
	}

	private String convertToStardColor(HSSFColor hc) {

		StringBuffer sb = new StringBuffer("");

		if (hc != null) {

			if (HSSFColor.AUTOMATIC.index == hc.getIndex()) {

				return null;
			}

			sb.append("#");

			for (int i = 0; i < hc.getTriplet().length; i++) {

				sb.append(fillWithZero(Integer.toHexString(hc.getTriplet()[i])));
			}
		}

		return sb.toString();
	}

	private String fillWithZero(String str) {

		if (str != null && str.length() < 2) {

			return "0" + str;
		}
		return str;
	}

	private String getCellValue(HSSFCell cell) {

		switch (cell.getCellType()) {

		case HSSFCell.CELL_TYPE_NUMERIC:

			DecimalFormat format = new DecimalFormat("#0.##");
			// if (cell.getNumericCellValue() <= 1) {// 小于等于1的数字转换成百分比
			// return format.format(cell.getNumericCellValue() * 100) + "%";
			// }
			return format.format(cell.getNumericCellValue());
		case HSSFCell.CELL_TYPE_STRING:

			return cell.getStringCellValue();

		case HSSFCell.CELL_TYPE_FORMULA:

			// return cell.getCellFormula();
			return this.calcFormulaValue(cell).toString();

		default:
			return "";
		}
	}

	private Object calcFormulaValue(HSSFCell cell) {
		Object rs = "0";
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		CellValue cellValue = evaluator.evaluate(cell);
		switch (cellValue.getCellType()) {
		case HSSFCell.CELL_TYPE_BOOLEAN:
			rs = (cellValue.getBooleanValue());
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			DecimalFormat format = new DecimalFormat("#0.##");
			// if (cellValue.getNumberValue() <= 1) {// 小于等于1的数字转换成百分比
			// rs = format.format(cellValue.getNumberValue() * 100) + "%";
			// }
			rs = format.format(cellValue.getNumberValue());
			break;
		case HSSFCell.CELL_TYPE_STRING:
			rs = (cellValue.getStringValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			break;
		// CELL_TYPE_FORMULA will never happen
		case HSSFCell.CELL_TYPE_FORMULA:
			break;
		default:
			return rs;
		}
		return rs;
	}

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

}
