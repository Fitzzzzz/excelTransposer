package excelTransposer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class OutputFile extends InOutFile {
	

	/**
	 * Constructor
	 * @param name Name of the file in which the transposition will be written. Everything is written into this file.
	 */
	public OutputFile(String name, XSSFSheet sheet) {
		super(name, sheet);
	}
	
	private Cell[] years;
	
	
	public Cell[] getYears() {
		return years;
	}

	public void setYears(Cell[] years) {
		this.years = years;
	}



	private Cell[] serieHeader;


	public Cell[] getSerieHeader() {
		return serieHeader;
	}

	public void setSerieHeader(Cell[] serieHeader) {
		this.serieHeader = serieHeader;
	}
	
	
	public void divideHeader(Cell[] header, int serieNb) {
		this.serieHeader = new Cell[serieNb];
		this.years = new Cell[header.length - serieNb];
		Tools.divide(header, serieHeader, years);
	}
	
	
		
	public static final String[] serieEnd = {"period", "value"};
	
	
}

