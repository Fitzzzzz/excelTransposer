package excelTransposer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class OutputFile extends InOutFile {
	

	/**
	 * Constructor
	 * @param name Name of the file in which the transposition will be written. Everything is written into this file.
	 */
	public OutputFile(XSSFSheet sheet) {
		super(sheet);
	}
	
	private Cell[] years;
	
	
	public Cell[] getYears() {
		return years;
	}

	public void setYears(Cell[] years) {
		this.years = years;
	}

	private int[] yearsInt;
	
	public int[] getYearsInt() {
		return yearsInt;
	}

	public void setYearsInt(int[] yearsString) {
		this.yearsInt = yearsString;
	}

	public int[] months;
	
	public int[] getMonths() {
		return months;
	}

	public void setMonths(int[] months) {
		this.months = months;
	}

	private Cell[] rightHeader;
 
	public Cell[] getRightHeader() {
		return rightHeader;
	}

	public void setRightHeader(Cell[] rightHeader) {
		this.rightHeader = rightHeader;
	}

	private Cell[] leftHeader;


	public Cell[] getLeftHeader() {
		return leftHeader;
	}

	public void setLeftHeader(Cell[] leftHeader) {
		this.leftHeader = leftHeader;
	}

	private int commentColumnId;
	

	public int getCommentColumnId() {
		return commentColumnId;
	}

	public void setCommentColumnId(int commentColumnId) {
		this.commentColumnId = commentColumnId;
	}
	
	private Cell[] values;
	
 	public Cell[] getValues() {
		return values;
	}
	public void setValues(Cell[] values) {
		this.values = values;
	}
	
	public void divideHeader(Cell[] header, int serieNb, int firstRightHeader) {
		this.leftHeader = new Cell[serieNb];
		Cell[] left = new Cell[header.length - serieNb];
		Tools.divide(header, leftHeader, left);
		years = new Cell[firstRightHeader - serieNb];
		rightHeader = new Cell[left.length - years.length];
		Tools.divide(left, years, rightHeader);
	}
	
	
		
	public static final String[] periodValueYearly = {"period", "value"};
	
	public static final String[] periodValueMonthly = {"year", "month", "value"};
	public static final String[] commentColumns = {"source", "comment", "statut"};
	
	public void setArrays(int leftSerieNb, int rightSerieNb, int valuesNb) {
		this.leftHeader = new Cell[leftSerieNb];
		this.rightHeader = new Cell[rightSerieNb];
		this.values = new Cell[valuesNb];
	}

}

