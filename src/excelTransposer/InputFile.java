package excelTransposer;

import org.apache.poi.ss.usermodel.Cell;
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
		
		
		
		
}
