package excelTransposer;

import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * A class describing a sheet. Can be a sheet in which it will be written (OutputFile) OR read (Inputfile) but not both.
 * 
 * @author hamme
 *
 */

public class InOutFile {


	/**
	 * The line in which it will be written or read next. Starts at 0.
	 */
	private int currentLine; 
	
	public int getCurrentLine() {
		return currentLine;
	}
	
	public void setCurrentLine(int l) {
		this.currentLine = l;
	}
	
	/**
	 * The sheet of the file.
	 */
	private XSSFSheet sheet;
	
	public XSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(XSSFSheet sheet) {
		this.sheet = sheet;
	}
	
	


	
	/**
	 * Sets the name of the file and the current line to 0.
	 * @param {@link InOutFile#sheet} 
	 * @see InOutFile#currentLine
	 */
	public InOutFile(XSSFSheet sheet) {
		this.currentLine = 0;
		this.sheet = sheet;
	}
	
	/**
	 * Increments the current line. 
	 * @see InOutFile#currentLine
	 */
	public void incrLine() {

		currentLine++;
	}
}

