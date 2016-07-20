package org.roof.excel.support.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.roof.excel.support.IExcelReader;
import org.roof.excel.utils.IdGenerator;
import org.roof.excel.utils.SheetRW;
import org.roof.excel.vo.Column;
import org.roof.excel.vo.XslDb;

/**
 * 
 * @author liuxin 2011-4-13
 * 
 */
public class PoiExcelReader implements IExcelReader {
	private InputStream is;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private SheetRW sheetRW;

	private XslDb xslDb;

	private int currentRowNum;
	private int lastRowNum;
	private Logger logger = Logger.getLogger(this.getClass());

	private PoiExcelReader() {
		super();
	}

	public PoiExcelReader(InputStream is, XslDb xslDb) {
		this(is, xslDb, 0);
	}
	
	public PoiExcelReader(InputStream is, XslDb xslDb, int iSheet) {
		this();
		this.is = is;
		this.xslDb = xslDb;
		init(iSheet);
	}

	private void init(int iSheet) {
		if (is == null) {
			throw new IllegalArgumentException("InputStream 不能为空!");
		}
		try {
			wb = new HSSFWorkbook(is);
		} catch (IOException e) {
			logger.error("工作表错误！", e);
		}
		// TODO 为实现多工作表
		if(!((iSheet >= 0) && (iSheet < wb.getNumberOfSheets()))){
			return;
		}
		sheet = wb.getSheetAt(iSheet);
		currentRowNum = xslDb.getIgnore() - 1;
		lastRowNum = sheet.getLastRowNum();
		sheetRW = new SheetRW(sheet);
	}

	public boolean hasNext() {
		return (currentRowNum + 1) <= lastRowNum ? true : false;
	}

	public Map<String, Object> next() {
		Map<String, Object> resVal = new LinkedHashMap<String, Object>();
		if (xslDb != null && StringUtils.isNotBlank(xslDb.getIdColumn())) {
			Object value = IdGenerator.getId(xslDb.getIdGenerator(), xslDb.getIdGeneratorValue());
			resVal.put(xslDb.getIdColumn(), value);
		}
		currentRowNum += 1;
		List<Column> columns = xslDb.getColumns();
		Object o = null;
		for (Column column : columns) {
			o = sheetRW.read(currentRowNum, column.getXslcol());
			resVal.put(column.getDbcol(), o);
		}
		return resVal;
	}

	public void remove() {
		throw new UnsupportedOperationException("不支持 remove方法");
	}

	public int getCurrentRowNum() {
		return currentRowNum;
	}

	public int getLastRowNum() {
		return lastRowNum;
	}
}
