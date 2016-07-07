package excelTransposer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import commentsHandler.CommentReader;
import testTools.CellArray;


public class Main {

	public static void main(String[] args) throws IOException {

		String inputName = ""; // TODO : REWORK because ugly af
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
	    XSSFSheet oSheet = oWorkbook.createSheet(iSheet.getSheetName());
	    
	    OutputFile output = new OutputFile(outputName, oSheet);
	    
	    Tools T = new Tools(iSheet, oSheet);
	    
	    T.copy(0, 0, linesToCopy);
	    
	    input.setCurrentLine(linesToCopy); // Useless ?
	    output.setCurrentLine(linesToCopy); // Useless ?
	    
	    input.findFirstBlankColumn(linesToCopy, serieNb);
	    T.setLastColumn(input.getColumnLimit());
	    input.setHeader(T.extractLine(linesToCopy));
	    
//	    System.out.println("serieNb = " + serieNb + " firstRightHeader = " + (input.getLastPeriod() + 1));
	    output.divideHeader(input.getHeader(), serieNb, input.getLastPeriod() + 1);
	    input.incrLine(); // Useless?
	    
//	    
//	    System.out.println("leftheader = " + output.getLeftHeader().length);
//	    System.out.println("serieEnd = " + OutputFile.periodValue.length);
//	    System.out.println("rightheader = " + output.getRightHeader().length);
	    
	    // Writing the header
	    
	    T.writeLine(linesToCopy, output.getLeftHeader(), OutputFile.periodValue, output.getRightHeader(), OutputFile.commentColumns);
	    int commentIndex = output.getLeftHeader().length + OutputFile.periodValue.length + output.getRightHeader().length;
	    

//	    CellArray rightHeader = new CellArray(output.getRightHeader()); 
//	    rightHeader.print();
//	    System.out.println(serieNb + " " + (T.getLastColumn() - input.getLastPeriod()) + " " + (input.getLastPeriod() - serieNb));
	    
	    /* 
	     * Début du traitement ligne par ligne
	     * 
	     */
	    int valuesNumber;
	    
	    boolean done = false;
	    int j = serieNb;
	    while (!done) {
//	    	System.out.println("j =" + j);
		    output.setLeftHeader(T.extractLine(j, 0, serieNb - 1));
//		    System.out.println("lastperiod = " + input.getLastPeriod());
		    output.setValues(T.extractLine(j, serieNb, input.getLastPeriod()));
//		    System.out.println(input.getColumnLimit());
		    output.setRightHeader(T.extractLine(j, input.getLastPeriod() + 1, input.getColumnLimit()));

		    
		    Cell[] line = new Cell[serieNb + 2 + output.getRightHeader().length];
//		    System.out.println("line de longueur " + line.length);
		    
		    Tools.fill(line, output.getLeftHeader(), 0);
		    Tools.fill(line, output.getRightHeader(), serieNb + 2);
		    
//		    System.out.println("Values length = " + output.getValues().length);
		    for (int i = 0; i < output.getValues().length; i++) {
		    	
		    	line[serieNb] = output.getYears()[i];
		    	line[serieNb + 1] = output.getValues()[i];
//		    	CellArray lineCA = new CellArray(line);
//		    	System.out.print("line inc : ");
//		    	lineCA.print();
		    	T.writeLine(serieNb + i + (j - serieNb)*output.getValues().length, line);

		    }
		    j++;
//		    System.out.println("j = " + j);
		    done = T.isItEOF(j);
		    
	    }
	    
	    valuesNumber = output.getValues().length;
	    
	    
	    Map<CellAddress, XSSFComment> comments = iSheet.getCellComments();
	    
	    for (java.util.Map.Entry<CellAddress, XSSFComment> e : comments.entrySet()) {
	      CellAddress loc = e.getKey();
	      Comment comment = e.getValue();
	      
//	      System.out.println("Bulle trouvée à " + loc.getRow() + ":" + loc.getColumn());
	      
	      CommentReader commentR = new CommentReader(comment.getString().getString());
//	      System.out.println(comment.getString().getString());
	      int outputRowId = (loc.getRow() - linesToCopy)*valuesNumber + linesToCopy + loc.getColumn() - serieNb;
	     
	      if (commentR.getSourcePosition() != -1) {
//	    	  System.out.println("Source trouvé à " + loc.getRow() + ":" + loc.getColumn());
		      T.writeCell(outputRowId, commentIndex, commentR.getSource());
	      }
	      if (commentR.getCommentPosition() != -1) {
//	    	  System.out.println("Comment trouvé à " + loc.getRow() + ":" + loc.getColumn());
		      T.writeCell(outputRowId, commentIndex + 1, commentR.getComment());
	      }
	      if (commentR.getStatutPosition() != -1) {
//	    	  System.out.println("Statut trouvé à " + loc.getRow() + ":" + loc.getColumn());
		      T.writeCell(outputRowId, commentIndex + 2, commentR.getStatut());
	      }
	      
	    }
	  
	    
	    XSSFSheet iSuppSheet;
	    	
	    if ((iSuppSheet = iWorkbook.getSheet("suppression")) != null) {
//	    	System.out.println("suppression trouvé");
	    	
	    	InputFile inputSupp = new InputFile("suppression", iSuppSheet, linesToCopy);
		    
	    	XSSFSheet oSuppSheet = oWorkbook.createSheet("suppression");
		    
		    OutputFile outputSupp = new OutputFile("suppression", oSuppSheet);
		    
		    Tools tSupp = new Tools(iSuppSheet, oSuppSheet);
		    
		    tSupp.copy(0, 0, linesToCopy);
		    
		    inputSupp.setCurrentLine(linesToCopy); // Useless ?
		    outputSupp.setCurrentLine(linesToCopy); // Useless ?
		    
		    inputSupp.findFirstBlankColumn(linesToCopy, serieNb);
		    tSupp.setLastColumn(inputSupp.getColumnLimit());
		    inputSupp.setHeader(tSupp.extractLine(linesToCopy));
		    
//		    System.out.println("serieNb = " + serieNb + " firstRightHeader = " + (inputSupp.getLastPeriod() + 1));
		    outputSupp.divideHeader(inputSupp.getHeader(), serieNb, inputSupp.getLastPeriod() + 1);
		    inputSupp.incrLine(); // Useless?
		    
		    
//		    System.out.println("leftheader = " + outputSupp.getLeftHeader().length);
//		    System.out.println("serieEnd = " + OutputFile.periodValue.length);
//		    System.out.println("rightheader = " + outputSupp.getRightHeader().length);
//		    
		    // Writing the header
		    
		    tSupp.writeLine(linesToCopy, outputSupp.getLeftHeader(), OutputFile.periodValue, outputSupp.getRightHeader(), OutputFile.commentColumns);

//		    CellArray rightHeaderSupp = new CellArray(outputSupp.getRightHeader()); 
//		    rightHeaderSupp.print();
//		    System.out.println(serieNb + " " + (T.getLastColumn() - input.getLastPeriod()) + " " + (input.getLastPeriod() - serieNb));
		    
		    /* 
		     * Début du traitement ligne par ligne
		     * 
		     */
		    done = false;
		    j = serieNb;
		    while (!done) {
//		    	System.out.println("j =" + j);
			    outputSupp.setLeftHeader(tSupp.extractLine(j, 0, serieNb - 1));
//			    System.out.println("lastperiod = " + inputSupp.getLastPeriod());
			    outputSupp.setValues(tSupp.extractLine(j, serieNb, inputSupp.getLastPeriod()));
//			    System.out.println(input.getColumnLimit());
			    outputSupp.setRightHeader(tSupp.extractLine(j, inputSupp.getLastPeriod() + 1, inputSupp.getColumnLimit()));

			    
			    Cell[] line = new Cell[serieNb + 2 + outputSupp.getRightHeader().length];
			    
//			    System.out.println("line de longueur " + line.length);
			    
			    Tools.fill(line, outputSupp.getLeftHeader(), 0);
			    Tools.fill(line, outputSupp.getRightHeader(), serieNb + 2);
			    
//			    System.out.println("Values length = " + outputSupp.getValues().length);
			    for (int i = 0; i < outputSupp.getValues().length; i++) {
			    	
			    	line[serieNb] = outputSupp.getYears()[i];
			    	line[serieNb + 1] = outputSupp.getValues()[i];
//			    	CellArray lineCA = new CellArray(line);
//			    	System.out.print("line inc : ");
//			    	lineCA.print();
			    	tSupp.writeLine(serieNb + i + (j - serieNb)*outputSupp.getValues().length, line);

			    }
			    j++;
//			    System.out.println("j = " + j);
			    done = tSupp.isItEOF(j);
		    }
		    
		    comments = iSuppSheet.getCellComments();
		    
		    for (java.util.Map.Entry<CellAddress, XSSFComment> e : comments.entrySet()) {
		      CellAddress loc = e.getKey();
		      Comment comment = e.getValue();
		      
//		      System.out.println("Bulle trouvée à " + loc.getRow() + ":" + loc.getColumn());
		      
		      CommentReader commentR = new CommentReader(comment.getString().getString());
//		      System.out.println(comment.getString().getString());
		      int outputRowId = (loc.getRow() - linesToCopy)*valuesNumber + linesToCopy + loc.getColumn() - serieNb;
		     
		      if (commentR.getSourcePosition() != -1) {
//		    	  System.out.println("Source trouvé à " + loc.getRow() + ":" + loc.getColumn());
			      tSupp.writeCell(outputRowId, commentIndex, commentR.getSource());
		      }
		      if (commentR.getCommentPosition() != -1) {
//		    	  System.out.println("Comment trouvé à " + loc.getRow() + ":" + loc.getColumn());
		    	  tSupp.writeCell(outputRowId, commentIndex + 1, commentR.getComment());
		      }
		      if (commentR.getStatutPosition() != -1) {
//		    	  System.out.println("Statut trouvé à " + loc.getRow() + ":" + loc.getColumn());
		    	  tSupp.writeCell(outputRowId, commentIndex + 2, commentR.getStatut());
		      }
		      
		    }
	    }

	    
	    
	  
	    
	    FileOutputStream out = new FileOutputStream(new File(outputName));
		oWorkbook.write(out);
		out.close();
//				System.out.println( 
//				outputName + " written successfully" );
				
		
	    
	    
	}

}
