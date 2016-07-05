package excelTransposer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import testTools.CellArray;


public class Main {

	public static void main(String[] args) throws IOException {

		String inputName = ""; // TODO : REWORK ugly af
		int sheetNb = 0;
		
		switch (args.length) {
		case 1:
			inputName = args[0];
			break;
		case 2:
			inputName = args[0];
			sheetNb = Integer.parseInt(args[1]);
			break;
		}
		
		String[] parts = inputName.split("\\.");
		String outputName = parts[0] + "Transposed.xlsx";
		final int linesToCopy = 2;
		final int serieNb = 3;
		
		FileInputStream fis = new FileInputStream(new File(inputName));
		XSSFWorkbook iWorkbook = new XSSFWorkbook(fis);
	    XSSFSheet iSheet = iWorkbook.getSheetAt(sheetNb);
	    
	    InputFile input = new InputFile(inputName, iSheet, linesToCopy);
	    
	    XSSFWorkbook oWorkbook = new XSSFWorkbook();
	    XSSFSheet oSheet = oWorkbook.createSheet("default");
	    
	    OutputFile output = new OutputFile(outputName, oSheet);
	    
	    Tools T = new Tools(iSheet, oSheet);
	    
	    T.copy(0, 0, linesToCopy);
	    
	    input.setCurrentLine(linesToCopy); // Useless ?
	    output.setCurrentLine(linesToCopy); // Useless ?
	    
	    input.findFirstBlankColumn(linesToCopy, serieNb);
	    T.setLastColumn(input.getColumnLimit());
	    input.setHeader(T.extractLine(linesToCopy));
	    
	    System.out.println("serieNb = " + serieNb + " firstRightHeader = " + (input.getLastPeriod() + 1));
	    output.divideHeader(input.getHeader(), serieNb, input.getLastPeriod() + 1);
	    input.incrLine(); // Useless?
	    
	    
	    System.out.println("leftheader = " + output.getLeftHeader().length);
	    System.out.println("serieEnd = " + OutputFile.serieEnd.length);
	    System.out.println("rightheader = " + output.getRightHeader().length);
	    
	    // Writing the header
	    
	    T.writeLine(linesToCopy, output.getLeftHeader(), OutputFile.serieEnd, output.getRightHeader());

	    CellArray rightHeader = new CellArray(output.getRightHeader()); 
	    rightHeader.print();
//	    System.out.println(serieNb + " " + (T.getLastColumn() - input.getLastPeriod()) + " " + (input.getLastPeriod() - serieNb));
	    
	    /* 
	     * Début du traitement ligne par ligne
	     * 
	     */
	    boolean done = false;
	    int j = serieNb;
	    while (!done) {
	    	System.out.println("j =" + j);
		    output.setLeftHeader(T.extractLine(j, 0, serieNb - 1));
		    System.out.println("lastperiod = " + input.getLastPeriod());
		    output.setValues(T.extractLine(j, serieNb, input.getLastPeriod()));
//		    System.out.println(input.getColumnLimit());
		    output.setRightHeader(T.extractLine(j, input.getLastPeriod() + 1, input.getColumnLimit()));

		    
		    Cell[] line = new Cell[serieNb + 2 + output.getRightHeader().length];
//		    System.out.println("line de longueur " + line.length);
		    
		    Tools.fill(line, output.getLeftHeader(), 0);
		    Tools.fill(line, output.getRightHeader(), serieNb + 2);
		    
		    System.out.println("Values length = " + output.getValues().length);
		    for (int i = 0; i < output.getValues().length; i++) {
		    	
		    	line[serieNb] = output.getYears()[i];
		    	line[serieNb + 1] = output.getValues()[i];
		    	CellArray lineCA = new CellArray(line);
		    	System.out.print("line inc : ");
		    	lineCA.print();
		    	T.writeLine(serieNb + i + (j - serieNb)*output.getValues().length, line);

		    }
		    j++;
//		    System.out.println("j = " + j);
		    done = T.isItEOF(j);
	    }
	    
	    	
	    	
	    	

	    
	    
	  
	    
	    FileOutputStream out = new FileOutputStream( new File(outputName));
		oWorkbook.write(out);
		out.close();
//				System.out.println( 
//				outputName + " written successfully" );
				
		
	    
	    
	}

}
