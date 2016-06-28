package excelTransposer;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;


public class InputFile extends InOutFile{


		/**
		 * Constructor
		 * @param name
		 * 		Name of the file to be transposed
		 * @param linesToCopy
		 * 		Number of lines in the file that should be copy/pasted 
		 */
		public InputFile(String name, XSSFSheet sheet, int linesToCopy) {
			super(name, sheet);
			allDone = false;
			this.linesToCopy = linesToCopy;
		}
		
		public void extractHeader() {
		
			extractLine(linesToCopy);
			
		}

		public void extractLine(int rowId) { // Probleme cases vides 
			
			XSSFRow row = getSheet().getRow(rowId);
			Iterator < Cell > cellIterator = row.cellIterator();
			header = new String[row.getLastCellNum()];
			System.out.println("header de taille " + row.getLastCellNum()); // TODO : TBR
			int i = 0;
			
			while (cellIterator.hasNext()) {

				System.out.println("On s'occupe de la case " + i);
				
				Cell cell = cellIterator.next();
				
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					header[i] = String.valueOf(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					header[i] = cell.getStringCellValue();
					break;
				}
				
				i++;
				
			}
		}
		
		/**
		 * 	Number of lines in the file that should be copy/pasted in the output file.
		 */
		private int linesToCopy;

		/**
		 * A boolean that is true when the whole file has been read. Means the CSVReader reached EOF.
		 */
		private boolean allDone;
		

		public boolean isAllDone() {
			return allDone;
		}
		
		private String[] header; // Mieux tableau de Cells?


		public String[] getHeader() {
			return header;
		}
		
		
}
