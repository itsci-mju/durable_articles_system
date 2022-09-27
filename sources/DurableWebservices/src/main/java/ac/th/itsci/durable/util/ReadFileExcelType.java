package ac.th.itsci.durable.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.poi.ss.usermodel.Cell;

public class ReadFileExcelType {

	public static final ReadFileExcelType readfile = new ReadFileExcelType();
	
	public String cellType(Cell cell) {
		// TODO Auto-generated method stub
		String value = "";

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			value = Boolean.toString(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			BigDecimal big = new BigDecimal(cell.getNumericCellValue());
			value = (big.setScale(0, RoundingMode.HALF_DOWN)).toString();
			break;
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			value = cell.getCellFormula();
			break;
		case Cell.CELL_TYPE_ERROR:
			value = Byte.toString(cell.getErrorCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			value = null;
			break;
		}
		return value;
	}

}
