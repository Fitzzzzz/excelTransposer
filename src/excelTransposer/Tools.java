package excelTransposer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class Tools {

	public Tools(XSSFSheet input, XSSFSheet output) {
		this.input = input;
		this.output = output;
	}
	
	private XSSFSheet input;
	private XSSFSheet output;
	
	private int lastColumn;
	
	public int getLastColumn() {
		return lastColumn;
	}
	public void setLastColumn(int columnLimit) {
		this.lastColumn = columnLimit;
	}
	
	
	public void copy(int inputStart, int outputStart, int length) {

		XSSFRow iRow;
		XSSFRow oRow;
		for (int i = 0; i < length; i++) {
			
			iRow = input.getRow(inputStart + i);
			oRow = output.createRow(outputStart + i);
			
			if (iRow != null) {

				int eol = iRow.getLastCellNum();

				for (int j = 0; j < eol; j++) {
					
					Cell iCell = iRow.getCell(j, Row.CREATE_NULL_AS_BLANK);
					Cell oCell = oRow.createCell(j);
					
					switch (iCell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
					 	if (DateUtil.isCellDateFormatted(iCell)) {
					 		oCell.setCellValue(iCell.getDateCellValue());
//							System.out.println(" FOUND A DATE " + iCell.getRowIndex() + ":" + iCell.getColumnIndex()); // TODO : TBR
					 	}
					 	else {
					 		oCell.setCellValue(iCell.getNumericCellValue());
					 	}	
						break;
					case Cell.CELL_TYPE_STRING:
						oCell.setCellValue(iCell.getStringCellValue());
						break;
					}

				}

			}

		}
		
	}
	public Cell[] extractLine(int rowId) { 
		
		XSSFRow row = input.getRow(rowId);
		Cell[] line = new Cell[lastColumn + 1];
//		System.out.println("header de taille " + lastColumn); // TODO : TBR
		
		
		for (int i = 0; i <= lastColumn; i++) {

//			System.out.println("On s'occupe de la case " + i);
			
			line[i] = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
		}
		return line;
	}
	
	public Cell[] extractLine(int rowId, int start, int end) { 
		
		XSSFRow row = input.getRow(rowId);
		Cell[] line = new Cell[end - start + 1];
		
		
		for (int i = start; i <= end; i++) {
			
//			System.out.println("On s'occupe de la case " + i);
//			Cell c = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
			line[i - start] = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
//			printCell(c);
		}
		return line;
	}
	
	public void writeLine(int rowId, Cell[] beginning, String[] end) {
		
		XSSFRow row = output.createRow(rowId);
		for (int i = 0; i < beginning.length; i++) {
			Cell cell = row.createCell(i);
			switch (beginning[i].getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(beginning[i])) {
					cell.setCellValue(beginning[i].getDateCellValue());
//					System.out.println(" FOUND A DATE " + beginning[i].getRowIndex() + ":" + beginning[i].getColumnIndex()); // TODO : TBR
				}
				else {
					cell.setCellValue(beginning[i].getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cell.setCellValue(beginning[i].getStringCellValue());
				break;
			}
			
		}
		for (int i = 0; i < end.length; i++) {
			Cell cell = row.createCell(beginning.length + i);
			cell.setCellValue(end[i]);
		}
		
	}
	
	public void writeLine(int rowId, Cell[] beginning, String[] middle, Cell[] end) {
		
		XSSFRow row = output.createRow(rowId);
		for (int i = 0; i < beginning.length; i++) {
			Cell cell = row.createCell(i);
			switch (beginning[i].getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(beginning[i])) {
					cell.setCellValue(beginning[i].getDateCellValue());
//					System.out.println(" FOUND A DATE " + beginning[i].getRowIndex() + ":" + beginning[i].getColumnIndex()); // TODO : TBR
				}
				else {
					cell.setCellValue(beginning[i].getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cell.setCellValue(beginning[i].getStringCellValue());
				break;
			}
			
		}
		for (int i = 0; i < middle.length; i++) {
			Cell cell = row.createCell(beginning.length + i);
			cell.setCellValue(middle[i]);
		}
		for (int i = 0; i < end.length; i++) {
			Cell cell = row.createCell(beginning.length + middle.length+ i);
			switch (end[i].getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(end[i])) {
					cell.setCellValue(end[i].getDateCellValue());
//					System.out.println(" FOUND A DATE " + end[i].getRowIndex() + ":" + end[i].getColumnIndex()); // TODO : TBR
				}
				else {
					cell.setCellValue(end[i].getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cell.setCellValue(end[i].getStringCellValue());
				break;
			}
		}
		
	}
	
	public void writeLine(int rowId, Cell[] beginning, String[] second, Cell[] third, String[] end) {
		
		XSSFRow row = output.createRow(rowId);
		for (int i = 0; i < beginning.length; i++) {
			Cell cell = row.createCell(i);
			switch (beginning[i].getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(beginning[i])) {
					cell.setCellValue(beginning[i].getDateCellValue());
//					System.out.println(" FOUND A DATE " + beginning[i].getRowIndex() + ":" + beginning[i].getColumnIndex()); // TODO : TBR
				}
				else {
					cell.setCellValue(beginning[i].getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cell.setCellValue(beginning[i].getStringCellValue());
				break;
			}
			
		}
		for (int i = 0; i < second.length; i++) {
			Cell cell = row.createCell(beginning.length + i);
			cell.setCellValue(second[i]);
		}
		for (int i = 0; i < third.length; i++) {
			Cell cell = row.createCell(beginning.length + second.length+ i);
			switch (third[i].getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(third[i])) {
//					System.out.println(" FOUND A DATE " + third[i].getRowIndex() + ":" + third[i].getColumnIndex()); // TODO : TBR
					cell.setCellValue(third[i].getDateCellValue());
				}
				else {
					cell.setCellValue(third[i].getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cell.setCellValue(third[i].getStringCellValue());
				break;
			}
		}
		for (int i = 0; i < end.length; i++) {
			Cell cell = row.createCell(beginning.length + second.length + third.length + i);
			cell.setCellValue(end[i]);
		}
		
	}
	
	public void writeLine(int rowId, Cell[] line) {
		
		XSSFRow row = output.createRow(rowId);
		for (int i = 0; i < line.length; i++) {
//			System.out.println("writeLine de la case " + i);
			Cell cell = row.createCell(i);
			switch (line[i].getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(line[i])) {
//					System.out.println(" FOUND A DATE " + line[i].getRowIndex() + ":" + line[i].getColumnIndex()); // TODO : TBR
					
					cell.setCellValue(line[i].getDateCellValue());
				}
				else {
					cell.setCellValue(line[i].getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cell.setCellValue(line[i].getStringCellValue());
				break;
			}
			
		}
		
	}
	 
	public void writeline(int rowId, Cell[] first, int second, int third, Cell forth, Cell[] last) {

		
		XSSFRow row = output.createRow(rowId);
		for (int i = 0; i < first.length; i++) {
			Cell cell = row.createCell(i);
			switch (first[i].getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(first[i])) {
					cell.setCellValue(first[i].getDateCellValue());
//					System.out.println(" FOUND A DATE " + beginning[i].getRowIndex() + ":" + beginning[i].getColumnIndex()); // TODO : TBR
				}
				else {
					cell.setCellValue(first[i].getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cell.setCellValue(first[i].getStringCellValue());
				break;
			}
			
		}

		Cell cell = row.createCell(first.length);
		cell.setCellValue(second);
		
		cell = row.createCell(first.length + 1);
		cell.setCellValue(third);
		
		cell = row.createCell(first.length + 2);
		switch (forth.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(forth)) {
//				System.out.println(" FOUND A DATE " + third[i].getRowIndex() + ":" + third[i].getColumnIndex()); // TODO : TBR
				cell.setCellValue(forth.getDateCellValue());
			}
			else {
				cell.setCellValue(forth.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_STRING:
			cell.setCellValue(forth.getStringCellValue());
			break;
		}
		
		
		for (int i = 0; i < last.length; i++) {
			cell = row.createCell(first.length + 3 + i);
			switch (last[i].getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(last[i])) {
//					System.out.println(" FOUND A DATE " + third[i].getRowIndex() + ":" + third[i].getColumnIndex()); // TODO : TBR
					cell.setCellValue(last[i].getDateCellValue());
				}
				else {
					cell.setCellValue(last[i].getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cell.setCellValue(last[i].getStringCellValue());
				break;
			}
		}
		
	}
	
	// Needs row to be created (Should always been)
	// Needs cell to be created? Is it?
	public void writeCell(int rowId, int columnId, String msg) {
		
		XSSFRow row = output.getRow(rowId);
		Cell cell = row.createCell(columnId);
//		System.out.println("dans writeCell de " + rowId + ":" + columnId + " : " + msg);
		cell.setCellValue(msg);
		
	}
	
	public boolean isItEOF(int j) {
		XSSFRow row = input.getRow(j);
		if (row == null) {
			return true;
		}
		if (isRowEmpty(row)) {
//			System.out.println("Row empty at " + j);
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isRowEmpty(XSSFRow row) {
	    for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);
	        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
	            return false;
	    }
	    return true;
	}
	
	public boolean isColumnEmpty(int column, int firstRow) {
		

		XSSFRow row = input.getRow(firstRow);
		
		while (row != null) {
			Cell c = row.getCell(column, Row.RETURN_BLANK_AS_NULL);
			if (c != null) {
				return false;
			}
			row = input.getRow(firstRow++);
		}
		return true;
		
	}
	

	public static boolean isColumnEmpty(int column, int firstRow, XSSFSheet sheet) {
		

		XSSFRow row = sheet.getRow(firstRow);
		
		while (row != null) {
			Cell c = row.getCell(column, Row.RETURN_BLANK_AS_NULL);
			if (c != null) {
				return false;
			}
			row = sheet.getRow(firstRow++);
		}
		return true;
		
	}
	 
	// TODO : Check b + c = a ?
	public static void divide(Cell[] a, Cell[] b, Cell[] c) {
		
		int bLength = b.length;
		for (int i = 0; i < bLength; i++) {
			b[i] = a[i];
		}
		for (int i = 0; i < c.length; i++) {
			c[i] = a[i + bLength];
		}
	}
	// TODO : Check b + c = a ?
	public static void regroup(Cell[] a, Cell[] b, Cell[] c) {
		
		int bLength = b.length;
		for (int i = 0; i < bLength; i++) {
			a[i] = b[i];
		}
		for (int i = 0; i < c.length; i++) {
			a[i + bLength] = c[i];
		}
	}
	
	public static void fill(Cell[] a, Cell[] b, int start) {
		
//		System.out.println("a de taille " + a.length + " et b de taille " + b.length + " début à " + start);
		for (int i = 0; i < b.length; i++) {
			a[start + i] = b[i];
			
		}
	}
	public static void printCell(Cell c) {
		
		if (c == null) {
			System.out.print("NULL #");
		}
		else {
			switch (c.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(c)) {
					System.out.print(c.getDateCellValue());
				}
				else {
					System.out.print(c.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_STRING:
				System.out.print(c.getStringCellValue());
				break;
			}
		}
	}
	
	
	
	
	
	
	
}
