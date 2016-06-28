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
			int lastColumn = row.getLastCellNum();
			header = new Cell[lastColumn];
			System.out.println("header de taille " + lastColumn); // TODO : TBR
			
			
			for (int i = 0; i < lastColumn; i++) {

				System.out.println("On s'occupe de la case " + i);
				
				Cell cell = row.getCell(i, row.CREATE_NULL_AS_BLANK);
				
				header[i] = cell;
				
				
				
				
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
		
		private Cell[] header; // Mieux tableau de Cells?


		public Cell[] getHeader() {
			return header;
		}
		
		
}
