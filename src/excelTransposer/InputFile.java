package excelTransposer;

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
		}

		/**
		 * A boolean that is true when the whole file has been read. Means the CSVReader reached EOF.
		 */
		private boolean allDone;
		

		public boolean isAllDone() {
			return allDone;
		}
		
		private Cell[] header;


		public void setHeader(Cell[] header) {
			this.header = header;
		}

		public Cell[] getHeader() {
			return header;
		}
		
		private int lastColumn;
		
		public int getColumnLimit() {
			return lastColumn;
		}

		public void setColumnLimit(int columnLimit) {
			this.lastColumn = columnLimit;
		}

		public int lastPeriod;
		
		public int getLastPeriod() {
			return lastPeriod;
		}

		public void setLastPeriod(int lastPeriod) {
			this.lastPeriod = lastPeriod;
		}

		public void findFirstBlankColumn(int rowId, int serieNb) {

			System.out.println("findFirstBlank ligne " + rowId);
			XSSFRow row = getSheet().getRow(rowId);
			int eof = row.getLastCellNum();
			boolean stringFound = false;
			for (int i = serieNb; i < eof; i++) {

				System.out.println("On cherche si c'est null en case " + i);
				
				Cell cell = row.getCell(i, row.RETURN_BLANK_AS_NULL);
				
//				Tools.printCell(cell);
//				System.out.println("");
				if (cell == null) {
					lastColumn = i - 1;
					System.out.println("Premiere colonne vide à " + i);
					return;
				}
				else if ( !(stringFound) && (cell.getCellType() == cell.CELL_TYPE_STRING)) {
					lastPeriod = i - 1;
					System.out.println("Lastperiod = " + lastPeriod);
					stringFound = true;
				}
			}
			lastColumn = eof - 1;
			
		}
		
		
		
		
}
