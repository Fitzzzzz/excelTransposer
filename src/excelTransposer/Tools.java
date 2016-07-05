package excelTransposer;

import org.apache.poi.ss.usermodel.Cell;
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
					
					Cell iCell = iRow.getCell(j, iRow.CREATE_NULL_AS_BLANK);
					Cell oCell = oRow.createCell(j);
					
					switch (iCell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						oCell.setCellValue(iCell.getNumericCellValue());
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
		System.out.println("header de taille " + lastColumn); // TODO : TBR
		
		
		for (int i = 0; i <= lastColumn; i++) {

			System.out.println("On s'occupe de la case " + i);
			
			line[i] = row.getCell(i, row.CREATE_NULL_AS_BLANK);
		}
		return line;
	}
	
	public Cell[] extractLine(int rowId, int start, int end) { 
		
		XSSFRow row = input.getRow(rowId);
		Cell[] line = new Cell[end - start + 1];
		
		
		for (int i = start; i <= end; i++) {
			
			System.out.println("On s'occupe de la case " + i);
			Cell c = row.getCell(i, row.CREATE_NULL_AS_BLANK);
			line[i - start] = row.getCell(i, row.CREATE_NULL_AS_BLANK);
			printCell(c);
		}
		return line;
	}
	
	public void writeLine(int rowId, Cell[] beginning, String[] end) {
		
		XSSFRow row = output.createRow(rowId);
		for (int i = 0; i < beginning.length; i++) {
			Cell cell = row.createCell(i);
			switch (beginning[i].getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				cell.setCellValue(beginning[i].getNumericCellValue());
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
				cell.setCellValue(beginning[i].getNumericCellValue());
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
				cell.setCellValue(end[i].getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				cell.setCellValue(end[i].getStringCellValue());
				break;
			}
		}
		
	}
	
	public void writeLine(int rowId, Cell[] line) {
		
		XSSFRow row = output.createRow(rowId);
		for (int i = 0; i < line.length; i++) {
			System.out.println("writeLine de la case " + i);
			Cell cell = row.createCell(i);
			switch (line[i].getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				cell.setCellValue(line[i].getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				cell.setCellValue(line[i].getStringCellValue());
				break;
			}
			
		}
		
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
		
		System.out.println("a de taille " + a.length + " et b de taille " + b.length + " d�but � " + start);
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
				System.out.println(c.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				System.out.println(c.getStringCellValue());
				break;
			}
		}
	}
	
	
	
	
	
}
