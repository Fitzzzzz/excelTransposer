package excelTransposer;

import java.util.Iterator;

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
		
		int rowId = inputStart;
		int cellId = 0;
		
		for (int i = 0; i < length; i++) {
			
			iRow = input.getRow(inputStart + i);
			oRow = output.createRow(outputStart + i);
			
			if (iRow != null) {
				
				iRow.toString();
				Iterator < Cell > cellIterator = iRow.cellIterator();
				
				while ( cellIterator.hasNext()) {
					
					Cell iCell = cellIterator.next();
					Cell oCell = oRow.createCell(cellId);
					
					switch (iCell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						oCell.setCellValue(iCell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						oCell.setCellValue(iCell.getStringCellValue());
						break;
					}
					cellId++;
				}
				cellId = 0;	
			}

		}
		
	}
	
}
