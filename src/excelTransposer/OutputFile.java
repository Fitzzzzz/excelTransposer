package excelTransposer;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public class OutputFile extends InOutFile {
	

	/**
	 * Constructor
	 * @param name Name of the file in which the transposition will be written. Everything is written into this file.
	 */
	public OutputFile(String name, XSSFSheet sheet) {
		super(name, sheet);
	}
	
}

