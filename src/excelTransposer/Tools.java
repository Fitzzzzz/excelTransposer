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
	
	public void copy(int inputStart, int outputStart, int length) {

		XSSFRow iRow;
		XSSFRow oRow;
		for (int i = 0; i < length; i++) {
			
			iRow = input.getRow(inputStart + i);
			oRow = output.createRow(outputStart + i);
			
			if (iRow != null) {

				int lastColumn = iRow.getLastCellNum();

				for (int j = 0; j < lastColumn; j++) {
					
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
		int lastColumn = row.getLastCellNum();
		Cell[] line = new Cell[lastColumn];
		System.out.println("header de taille " + lastColumn); // TODO : TBR
		
		
		for (int i = 0; i < lastColumn; i++) {

			System.out.println("On s'occupe de la case " + i);
			
			line[i] = row.getCell(i, row.CREATE_NULL_AS_BLANK);
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
	
	public static void regroup(Cell[] a, Cell[] b, Cell[] c) {
		
		int bLength = b.length;
		for (int i = 0; i < bLength; i++) {
			a[i] = b[i];
		}
		for (int i = 0; i < c.length; i++) {
			a[i + bLength] = c[i];
		}
	}
	
	
}
