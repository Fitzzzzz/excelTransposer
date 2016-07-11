package excelTransposer;

import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import commentsHandler.CommentReader;

public class SheetCouple {

	public SheetCouple(XSSFSheet iSheet, XSSFSheet oSheet) {
		this.inputSheet = iSheet;
		this.outputSheet = oSheet;
		t = new Tools(inputSheet, outputSheet);
		this.inputCurrentLine = 0;
		this.outputCurrentLine = 0;
		this.inputFile = new InputFile(iSheet);
		this.outputFile = new OutputFile(oSheet);
	}
	
	public SheetCouple(XSSFSheet iSheet, XSSFSheet oSheet, int linesToCopy) {
		this.inputSheet = iSheet;
		this.outputSheet = oSheet;
		t = new Tools(inputSheet, outputSheet);
		this.inputCurrentLine = 0;
		this.outputCurrentLine = 0;
		this.inputFile = new InputFile(iSheet, linesToCopy);
		this.outputFile = new OutputFile(oSheet);
	}
	
	
	private static final String monthlyRegex = "[0-9]{4}_[0-9]{1,2}";
	
	private InputFile inputFile;
	
	public InputFile getInputFile() {
		return inputFile;
	}


	private OutputFile outputFile;
	
	public OutputFile getOutputFile() {
		return outputFile;
	}


	private XSSFSheet inputSheet;
	
	private XSSFSheet outputSheet;
	
	private Tools t;
	
	
	private int inputCurrentLine;
	
	private int outputCurrentLine;
	
	public void incrInputLine() {
		this.inputCurrentLine++;
	}
	
	public void incOutputLine() {
		this.outputCurrentLine++;
	}
	
	public void copy(int inputStart, int outputStart, int length) {
		
		t.copy(inputStart, outputStart, length);
		this.outputCurrentLine = this.outputCurrentLine + length;
		this.inputCurrentLine = this.inputCurrentLine + length;
	
	}
	
	public void findFirstBlankColumn(int rowId, int columnStart) {
		inputFile.findFirstBlankColumn(rowId, columnStart);
		t.setLastColumn(inputFile.getColumnLimit());
	}
	
	// HeaderRow has to be set for inputFile
	public void extractHeader(int headerRowId) {
		inputFile.setHeader(t.extractLine(headerRowId)); 
	}
	
	public void divideHeader() {
		outputFile.divideHeader(inputFile.getHeader(), inputFile.getSerieNb(), inputFile.getLastPeriod() + 1);
	}
	
	public void writeHeader(int headerRowId) {
		
		findFirstBlankColumn(headerRowId, inputFile.getSerieNb());
		extractHeader(headerRowId);
		divideHeader();
		if (!isMonthly()) {
			t.writeLine(outputCurrentLine, outputFile.getLeftHeader(), OutputFile.periodValueYearly, outputFile.getRightHeader(), OutputFile.commentColumns);
			this.outputFile.setCommentColumnId(outputFile.getLeftHeader().length + OutputFile.periodValueYearly.length + outputFile.getRightHeader().length);
		}
		else {
			int[] years = new int[outputFile.getYears().length];
			int[] months = new int[outputFile.getYears().length];
			for (int i = 0; i < years.length; i++) {
				String[] yearAndMonth = this.separeYearMonth(outputFile.getYears()[i]);
				years[i] = Integer.parseInt(yearAndMonth[0]);
				months[i] = Integer.parseInt(yearAndMonth[1]);
			}
			outputFile.setYearsInt(years);
			outputFile.setMonths(months);
			t.writeLine(outputCurrentLine, outputFile.getLeftHeader(), OutputFile.periodValueMonthly, outputFile.getRightHeader(), OutputFile.commentColumns);
			this.outputFile.setCommentColumnId(outputFile.getLeftHeader().length + OutputFile.periodValueMonthly.length + outputFile.getRightHeader().length);

		
		}
		
	}
	
	public void writeBody() {
		
		int serieNb = inputFile.getSerieNb();
		int j = serieNb;
		boolean done = false;
		
		if (!isMonthly()) {
			while (!done) {
//		    	System.out.println("j =" + j);
			    outputFile.setLeftHeader(t.extractLine(j, 0, serieNb - 1));
//			    System.out.println("lastperiod = " + input.getLastPeriod());
			    outputFile.setValues(t.extractLine(j, serieNb, inputFile.getLastPeriod()));
//			    System.out.println(input.getColumnLimit());
			    outputFile.setRightHeader(t.extractLine(j, inputFile.getLastPeriod() + 1, inputFile.getColumnLimit()));

			    
			    Cell[] line = new Cell[serieNb + 2 + outputFile.getRightHeader().length];
//			    System.out.println("line de longueur " + line.length);
			    
			    Tools.fill(line, outputFile.getLeftHeader(), 0);
			    Tools.fill(line, outputFile.getRightHeader(), serieNb + 2);
			    
//			    System.out.println("Values length = " + output.getValues().length);
			    for (int i = 0; i < outputFile.getValues().length; i++) {
			    	
			    	line[serieNb] = outputFile.getYears()[i];
			    	line[serieNb + 1] = outputFile.getValues()[i];
//			    	CellArray lineCA = new CellArray(line);
//			    	System.out.print("line inc : ");
//			    	lineCA.print();
			    	t.writeLine(serieNb + i + (j - serieNb)*outputFile.getValues().length, line);

			    }
			    j++;
//			    System.out.println("j = " + j);
			    done = t.isItEOF(j);
			    
		    }
		}
		else {
			while (!done) {
//		    	System.out.println("j =" + j);
			    outputFile.setLeftHeader(t.extractLine(j, 0, serieNb - 1));
//			    System.out.println("lastperiod = " + input.getLastPeriod());
			    outputFile.setValues(t.extractLine(j, serieNb, inputFile.getLastPeriod()));
//			    System.out.println(input.getColumnLimit());
			    outputFile.setRightHeader(t.extractLine(j, inputFile.getLastPeriod() + 1, inputFile.getColumnLimit()));

			    
			    
			    
//			    System.out.println("Values length = " + output.getValues().length);
			    for (int i = 0; i < outputFile.getValues().length; i++) {
			    	

			    	t.writeline(serieNb + i + (j - serieNb)*outputFile.getValues().length, outputFile.getLeftHeader(), outputFile.getYearsInt()[i], outputFile.getMonths()[i], outputFile.getValues()[i] ,outputFile.getRightHeader());

			    }
			    j++;
//			    System.out.println("j = " + j);
			    done = t.isItEOF(j);
			    
		    }
		}
		
	}
	
	public void insertComments() {
		
		int valuesNumber = outputFile.getValues().length;
		int commentIndex = outputFile.getLeftHeader().length + OutputFile.periodValueYearly.length + outputFile.getRightHeader().length;

		Map<CellAddress, XSSFComment> comments = inputSheet.getCellComments();
		    
		for (java.util.Map.Entry<CellAddress, XSSFComment> e : comments.entrySet()) {
		      CellAddress loc = e.getKey();
		      Comment comment = e.getValue();
		      
//		      System.out.println("Bulle trouvée à " + loc.getRow() + ":" + loc.getColumn());
		      
		      CommentReader commentR = new CommentReader(comment.getString().getString());
//		      System.out.println(comment.getString().getString());
		      int outputRowId = (loc.getRow() - inputFile.getLinesToCopy())*valuesNumber + inputFile.getLinesToCopy() + loc.getColumn() - inputFile.getSerieNb();
		     
		      if (commentR.getSourcePosition() != -1) {
//		    	  System.out.println("Source trouvé à " + loc.getRow() + ":" + loc.getColumn());
			      t.writeCell(outputRowId, commentIndex, commentR.getSource());
		      }
		      if (commentR.getCommentPosition() != -1) {
//		    	  System.out.println("Comment trouvé à " + loc.getRow() + ":" + loc.getColumn());
			      t.writeCell(outputRowId, commentIndex + 1, commentR.getComment());
		      }
		      if (commentR.getStatutPosition() != -1) {
//		    	  System.out.println("Statut trouvé à " + loc.getRow() + ":" + loc.getColumn());
			      t.writeCell(outputRowId, commentIndex + 2, commentR.getStatut());
		      }
		      
		    }
	}
	
	public boolean isMonthly() {
		
		return outputFile.getYears()[0].getStringCellValue().matches(monthlyRegex);
		
	}
	
	private String[] separeYearMonth(Cell yearWithMonthCell) {
		String yearWithMonthString = yearWithMonthCell.getStringCellValue();
		String[] yearAndMonth = yearWithMonthString.split("_");
		if (yearAndMonth[1].matches("0[0-9]")) {
			yearAndMonth[1] = yearAndMonth[1].substring(0, 1);
		}
		return yearAndMonth;
	}
	
		
	
}
