package excelTransposer;

import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * A class describing a file. Can be a file in which it will be written (OutputFile) OR read (Inputfile) but not both.
 * 
 * @author hamme
 *
 */

public class InOutFile {


	/**
	 * The line in which it will be written or read next. Starts at 0.
	 */
	private int currentLine; 
	
	private XSSFSheet sheet;
	
	public XSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(XSSFSheet sheet) {
		this.sheet = sheet;
	}

	public int getCurrentLine() {
		return currentLine;
	}
	
	public void setCurrentLine(int l) {
		this.currentLine = l;
	}


	
	/**
	 * Sets the name of the file and the current line to 0.
	 * @param name Name of the file.
	 * @see InOutFile#currentLine
	 */
	public InOutFile(XSSFSheet sheet) {
		this.currentLine = 0;
		this.sheet = sheet;
	}
	
	/**
	 * Increments the current line. Called after each readNext() or writeNext().
	 * @see InOutFile#currentLine
	 */
	public void incrLine() {

		currentLine++;
	}
}

